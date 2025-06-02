package com.food.ordering.app.restaurant.service.storage;

import com.food.ordering.app.restaurant.service.config.AppGCPConfigProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Component
@Slf4j
@RequiredArgsConstructor
@Primary
public class S3BucketStorageStrategyImpl implements BucketStorageStrategy {

  private final S3Client s3Client;
  private final AppGCPConfigProperties appGCPConfigProperties;

  @Override
  public String saveFile(String fileName, String contentType, byte[] file) {
    log.info("Uploading file {} to S3", fileName);

    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
        .bucket(appGCPConfigProperties.getBucketName())
        .key(fileName)
        .contentType(contentType)
        .build();

    PutObjectResponse response = s3Client.putObject(
        putObjectRequest,
        RequestBody.fromBytes(file)
    );

    log.info("File {} uploaded successfully. ETag: {}", fileName, response.eTag());

    String publicUrl = appGCPConfigProperties.getPublicUrl();
    return String.format("%s/%s/%s", publicUrl, appGCPConfigProperties.getBucketName(), fileName);
  }
}
