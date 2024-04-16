package com.food.ordering.app.restaurant.service.storage;

public interface BucketStorageStrategy {

  String saveFile(String fileName, String contentType, byte[] file);
}
