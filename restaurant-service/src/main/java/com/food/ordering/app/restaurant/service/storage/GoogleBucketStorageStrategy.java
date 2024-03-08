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

  public static final String GOOGLE_STORAGE_URL = "https://storage.googleapis.com";

  private final Storage storage;
  private final AppGCPConfigProperties appGCPConfigProperties;

  @Override
  public String saveFile(String fileName, String contentType, byte[] file) {
    BlobId blobId = BlobId.of(appGCPConfigProperties.getBucketName(), fileName);

    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();
    storage.create(blobInfo, file);

    return String.format("%s/%s/%s", GOOGLE_STORAGE_URL, appGCPConfigProperties.getBucketName(), fileName);
  }
}
