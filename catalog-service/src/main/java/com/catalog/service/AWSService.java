/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Priyank Agrawal
 */
@Service
public class AWSService {

        @Autowired
        private AmazonS3 amazonS3;
        private static final Logger LOGGER = LoggerFactory.getLogger(AWSService.class);

        public String uploadFile(File file, String directory, String bucketName,
                        CannedAccessControlList accessControl) {
                PutObjectResult objectResult = amazonS3
                                .putObject(new PutObjectRequest(bucketName, directory + "/" + file.getName(), file)
                                                .withCannedAcl(accessControl));
                if (objectResult.getMetadata().getETag() != null) {
                        LOGGER.info("Uploading image in s3 bucket: {}.", bucketName);
                        return amazonS3.getUrl(bucketName, directory + "/" + file.getName()).toExternalForm();
                }
                return "";
        }

        public boolean deleteFilesFromBucket(List<String> fileFullPathList, String bucketName) {
                LOGGER.info("Deleting image from s3 bucket: {}.", bucketName);
                DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName);
                List<DeleteObjectsRequest.KeyVersion> listKeys = new ArrayList<>();
                for (String fileFullPath : fileFullPathList) {
                        DeleteObjectsRequest.KeyVersion key = new DeleteObjectsRequest.KeyVersion(fileFullPath);
                        listKeys.add(key);
                }
                deleteObjectsRequest.setKeys(listKeys);
                return fileFullPathList.size() == amazonS3.deleteObjects(deleteObjectsRequest).getDeletedObjects()
                                .size();
        }
}
