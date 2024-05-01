package com.food.ordering.app.restaurant.service.storage;

import com.food.ordering.app.restaurant.service.config.AppGCPConfigProperties;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class GoogleBucketStorageStrategy implements BucketStorageStrategy {

  private final Storage storage;
  private final AppGCPConfigProperties appGCPConfigProperties;

  @Override
  public String saveFile(String fileName, String contentType, byte[] file) {
    log.info("Uploading file {} to Google Cloud Storage", fileName);
    BlobId blobId = BlobId.of(appGCPConfigProperties.getBucketName(), fileName);

    // short cache time for testing purposes
    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType)
        .setCacheControl("public, max-age=30").build();
    storage.create(blobInfo, file);

    log.info("File {} was uploaded to Google Cloud Storage", fileName);

    return String.format("%s/%s/%s", appGCPConfigProperties.getPublicUrl(),
        appGCPConfigProperties.getBucketName(), fileName);
  }
}
