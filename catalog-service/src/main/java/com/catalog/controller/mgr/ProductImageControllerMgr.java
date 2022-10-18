
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.controller.mgr;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import static com.catalog.constants.CatalogConstant.IMG_BUCKET;
import com.catalog.dao.ProductImageDao;
import com.catalog.exception.OperationError;
import com.catalog.exception.ResourceNotFoundException;
import com.catalog.model.Product;
import com.catalog.model.ProductImage;
import com.catalog.model.ServiceResponse;
import com.catalog.repository.ProductImageRepository;
import com.catalog.service.AWSService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Priyank Agrawal
 */
@Service
public class ProductImageControllerMgr {

    @Autowired
    private ProductImageDao imageDao;

    @Autowired
    private ProductImageRepository imageRepo;

    @Qualifier("supportedImageType")
    @Autowired
    private Map<String, String> supportedImage;

    @Autowired
    private AWSService awsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductImageControllerMgr.class);

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse uploadImageInS3(String productId, String productName, List<MultipartFile> files) {
        List<ProductImage> productImages = new ArrayList<>();
        if (!validateImageInput(files)) {
            LOGGER.info("Validating failed while uploading product image." + "productId: {}", productId);
            Map<String, Object> invalidInputResp = new HashMap<>();
            invalidInputResp.put("message", "Invalid file type");
            invalidInputResp.put("allowedFileTypes", supportedImage);
            throw new OperationError(HttpStatus.UNPROCESSABLE_ENTITY, "File format is not supported", invalidInputResp,
                    "UploadProductImage");
        }

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            Path filepath = Paths.get(file.getOriginalFilename());
            try {
                file.transferTo(filepath);
                File originalFile = new File(file.getOriginalFilename());
                String uploadedImgUrl = awsService.uploadFile(originalFile, productName, IMG_BUCKET,
                        CannedAccessControlList.PublicRead);
                productImages.add(new ProductImage(uploadedImgUrl, fileName, productName, new Product(productId)));
                originalFile.delete();
            } catch (IOException | IllegalStateException ex) {

            }
        }
        imageDao.saveAll(productImages);
        return new ServiceResponse(HttpStatus.CREATED.value(), "Images are updated in S3.");
    }

    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse deleteProductImage(Long imageId) {
        LOGGER.info("Deleting product image by imageId: {}", imageId);
        Optional<ProductImage> productImageOptionalObj = imageDao.findById(imageId);
        if (!productImageOptionalObj.isPresent()) {
            LOGGER.info("Deleting product image, imageId: {} is not exist", imageId);
            throw new ResourceNotFoundException("productImage", imageId, "Product image is not found");
        }
        ProductImage productImage = productImageOptionalObj.get();
        String imageLocation = productImage.getImgDirectory().concat("/").concat(productImage.getImageName());
        List<String> imgFiles = new ArrayList<>();
        imgFiles.add(imageLocation);
        imageDao.deleteById(imageId);
        boolean isFileDeleted = awsService.deleteFilesFromBucket(imgFiles, IMG_BUCKET);
        if (isFileDeleted) {
            return new ServiceResponse(HttpStatus.OK.value(), isFileDeleted);
        } else {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something wrong with operation.");
        }
    }

    public ServiceResponse listProductImagesByProduct(String productId) {
        List<ProductImage> images = imageRepo.listProductImagesByProduct(productId);
        if (images.isEmpty()) {
            return new ServiceResponse(HttpStatus.NO_CONTENT.value());
        } else {
            return new ServiceResponse(HttpStatus.OK.value(), images);
        }
    }

    private boolean validateImageInput(List<MultipartFile> files) {
        boolean isInputValid = true;
        for (MultipartFile file : files) {
            System.out.println(file.getContentType());
            if (!supportedImage.containsKey(file.getContentType())) {
                isInputValid = false;
                break;
            }
        }
        return isInputValid;
    }
}
