package com.food.ordering.app.restaurant.service.service;

import com.food.ordering.app.restaurant.service.exception.InvalidImageTypeException;
import com.food.ordering.app.restaurant.service.storage.BucketStorageStrategy;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageStorageService implements StorageService {

  public static final List<String> ACCEPTED_IMAGE_TYPES = Arrays.asList(
      MimeTypeUtils.IMAGE_JPEG_VALUE,
      MimeTypeUtils.IMAGE_PNG_VALUE
  );

  private final BucketStorageStrategy bucketStorageStrategy;

  @Override
  public String uploadFile(MultipartFile image, UUID relatedEntityId) throws IOException {
    if (image.getContentType() == null || !ACCEPTED_IMAGE_TYPES.contains(image.getContentType())) {
      throw new InvalidImageTypeException(String.join(", ", ACCEPTED_IMAGE_TYPES));
    }

    return bucketStorageStrategy.saveFile(relatedEntityId.toString(), image.getContentType(),
        image.getBytes());
  }

}
