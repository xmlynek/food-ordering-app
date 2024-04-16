package com.food.ordering.app.restaurant.service.service;

import java.io.IOException;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

  String uploadFile(MultipartFile file, UUID relatedEntityId) throws IOException;
}
