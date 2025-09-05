package com.juan.cakeshop.api.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    public String uploadedImg(MultipartFile img, String folder);

    public boolean deleteImg(String folder, String url);
}
