package com.LandSV.landSV.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage (MultipartFile file) throws IOException {
        try {
            List<String> validTypes = new ArrayList<>(Arrays.asList("image/png", "image/jpg", "image/jpeg", "image/webp"));

            if(!validTypes.contains(file.getContentType())) throw new IllegalArgumentException("Image type is not allowed");

            long sizeInBytes = file.getSize();
            long size = sizeInBytes / (1024 * 1024);

            if(size > 10) throw new IllegalArgumentException("The Image is to heavy");

            Map upload = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "folder", "land_sv",
                    "resource_type", "auto"
            ));

            String url = (String) upload.get("secure_url");

            return url;
        } catch (IllegalArgumentException e) {
            throw e;

        } catch (IOException e) {
            throw e;

        } catch (Exception e) {
            throw e;
        }
    }
}
