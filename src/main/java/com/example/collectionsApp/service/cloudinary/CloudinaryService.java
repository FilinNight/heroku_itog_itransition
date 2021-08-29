package com.example.collectionsApp.service.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Random;


@Service
public class CloudinaryService {

    @Value("${cloud_name}")
    private String cloud_name;
    @Value("${api_key}")
    private String api_key;
    @Value("${api_secret}")
    private String api_secret;

    private static final Logger log = Logger.getLogger(CloudinaryService.class);

    private Cloudinary cloudinary ;


    public String upload(MultipartFile file, String nameUser, String nameCollection, String nameItem) {

        if (file == null || file.getOriginalFilename().equals("")){
            log.debug("add file: (file null)");
            return null;
        }

        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloud_name,
                "api_key", api_key,
                "api_secret", api_secret));

        Random random = new Random();
        String pathName = nameUser + "/" + nameCollection + "/" + nameItem + "_" + random.nextInt(10000) + "_" + file.getOriginalFilename();

        try {
            File tempFile = convertMultiPartToFile(file);
            Map uploadResult = cloudinary.uploader().upload(tempFile , ObjectUtils.asMap(  "public_id", pathName));
            tempFile.delete();
            log.info("add file: " + pathName);
            return uploadResult.get("url").toString();
        } catch (Exception ex) {
            log.error("add file:" + pathName);
            return null;
        }

    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {

        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());

        fos.close();
        return convFile;
    }

}
