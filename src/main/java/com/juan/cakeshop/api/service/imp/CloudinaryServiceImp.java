package com.juan.cakeshop.api.service.imp;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.juan.cakeshop.api.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImp implements CloudinaryService {

    final Cloudinary cloudinary;

    @Override
    public String uploadedImg(MultipartFile file, String folderName) {
        try{
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            return cloudinary.url().secure(true).generate(publicId);

        }catch (IOException e){
           throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload product's img");
        }
    }

    @Override
    public boolean deleteImg(String folder, String url) {
        String resource = url.substring(url.lastIndexOf("/") + 1);

        //Remove extension
        String resourceName = resource.contains(".")
                ? resource.substring(0, resource.lastIndexOf("."))
                : resource;

        String publicId = folder + "/" + resourceName;

        try {
            Map result =cloudinary.uploader().destroy(
                            publicId,
                            ObjectUtils.asMap("invalidate", true, "resource_type", "image")
                    );

            if("ok".equals(result.get("result"))) return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
