/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author Priyank Agrawal
 */
@Component
@PropertySource("classpath:config/aws/aws_config.properties")
public class ObjectConfig {

    private AmazonS3 amazonS3;

    private AWSCredentials aWSCredentials;

    @Value("${accessKey}")
    private String accessKey;

    @Value("${secretKey}")
    private String secretKey;

    @Bean
    public AmazonS3 getS3Client() {
        aWSCredentials = getAWSCredentials();
        amazonS3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(aWSCredentials))
                .withRegion(Regions.AP_SOUTH_1).build();
        return amazonS3;
    }

    @Bean
    public AWSCredentials getAWSCredentials() {
        aWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return aWSCredentials;
    }

    @Bean(name = "supportedImageType")
    public Map<String, String> getSupportedImageType() {
        Map<String, String> supportedImageType = new HashMap<>();
        supportedImageType.put("image/bmp", "Bitmap");
        supportedImageType.put("image/cis-cod", "compiled source code");
        supportedImageType.put("image/gif", "graphic interchange format");
        supportedImageType.put("image/ief", "image file");
        supportedImageType.put("image/jpeg", "JPEG image");
        supportedImageType.put("image/pipeg", "JPEG file interchange format");
        supportedImageType.put("image/svg+xml", "scalable vector graphic");
        supportedImageType.put("image/tiff", "TIF image");
        supportedImageType.put("image/x-cmu-raster", "Sun raster graphic");
        supportedImageType.put("image/x-cmx", "Corel metafile exchange image file");
        supportedImageType.put("image/x-icon", "icon");
        supportedImageType.put("image/x-portable-anymap", "portable any map image");
        supportedImageType.put("image/x-portable-bitmap", "portable bitmap image");
        supportedImageType.put("image/x-portable-graymap", "portable graymap image");
        supportedImageType.put("image/x-portable-pixmap", "portable pixmap image");
        supportedImageType.put("image/x-rgb", "RGB bitmap");
        supportedImageType.put("image/x-xbitmap", "X11 bitmap");
        supportedImageType.put("image/x-xpixmap", "X11 pixmap");
        supportedImageType.put("image/x-xwindowdump", "X-Windows dump image");
        supportedImageType.put("image/png", "png image");
        return supportedImageType;
    }
}
