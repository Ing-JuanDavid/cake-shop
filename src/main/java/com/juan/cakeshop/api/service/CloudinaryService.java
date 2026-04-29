package com.juan.cakeshop.api.service;

import com.juan.cakeshop.api.model.CloudinaryResponse;
import com.juan.cakeshop.api.model.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CloudinaryService {
    CloudinaryResponse uploadImg(MultipartFile img, String folder);

    List<CloudinaryResponse> uploadImageList(List<MultipartFile> images, String folderName);

    void deleteImg(String publicId);

    void deleteImageList(List<ProductImage> savedImages);

    boolean deleteFolder(String folderName);

    String getFolderName(String publicId);
}
