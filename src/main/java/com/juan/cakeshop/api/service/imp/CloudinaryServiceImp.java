package com.juan.cakeshop.api.service.imp;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.juan.cakeshop.api.model.CloudinaryResponse;
import com.juan.cakeshop.api.model.ProductImage;
import com.juan.cakeshop.api.service.CloudinaryService;
import com.juan.cakeshop.exception.customExceptions.CloudinaryException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImp implements CloudinaryService {

    final Cloudinary cloudinary;

    public String getImgUrl(String publicId) {
        return cloudinary.url().secure(true).generate(publicId);
    }

    @Override
    public CloudinaryResponse uploadImg(MultipartFile file, String folderName) {
        try{
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            Map result = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) result.get("public_id");
            return CloudinaryResponse.builder()
                    .imgUrl(getImgUrl(publicId))
                    .publicId(publicId)
                    .build();

        }catch (IOException e){
           throw  new CloudinaryException("Failed to upload product's img", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<CloudinaryResponse> uploadImageList(List<MultipartFile> images, String folderName) {

        List<CloudinaryResponse> uploadedImages = new ArrayList<>();

        try {
            for(MultipartFile image: images) {
                uploadedImages.add(
                        this.uploadImg(image, folderName)
                );
            }
        }catch (CloudinaryException e) {
            // Manual Roll back
            for(CloudinaryResponse uploadImage: uploadedImages){
                this.deleteImg(uploadImage.getPublicId());
            }
            throw new CloudinaryException(
                    "Fallo al subir, todas las cargas han sido reversadas.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return uploadedImages;
    }

    @Override
    public void deleteImg(String publicId) {
        try {
            Map result =cloudinary.uploader().destroy(
                            publicId,
                            ObjectUtils.asMap("invalidate", true, "resource_type", "image")
                    );

        } catch (IOException e) {
            throw new CloudinaryException("Error al eliminar la imagen", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteImageList(List<ProductImage> savedImages) {
        for(ProductImage image: savedImages) {
            this.deleteImg(image.getPublicId());
        }
    }

    @Override
    public boolean deleteFolder(String folderName) {
        try {
            Map result = cloudinary.api().deleteFolder(folderName, ObjectUtils.emptyMap());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getFolderName(String publicId) {
        return publicId.substring(0, publicId.indexOf("/"));
    }


    public void renameFolder(String oldFolder, String newFolder, List<String> publicIds) {
        try {
            for (String publicId : publicIds) {
                String fileName = publicId.substring(publicId.lastIndexOf("/") + 1);
                System.out.println("OLD: " + publicId);
                System.out.println("NEW: " + newFolder + "/" + fileName);

                Map result = cloudinary.uploader().rename(
                        publicId,
                        newFolder + "/" + fileName,
                        Map.of("overwrite",true)
                );

                System.out.println("Result: "+result.toString());
            }
            // delete old empty folder
            // deleteFolder(oldFolder);
        } catch (Exception e) {
            throw new CloudinaryException("Failed to rename folder", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
