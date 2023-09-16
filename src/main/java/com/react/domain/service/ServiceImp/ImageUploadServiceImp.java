package com.react.domain.service.ServiceImp;

import com.react.domain.service.ImageUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageUploadServiceImp implements ImageUploadService {

    public String uploadImage(MultipartFile file, String filePath) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo vazio.");
        }

        Path directoryPath = Paths.get(filePath).getParent();
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        File destinationFile = new File(filePath);
        file.transferTo(destinationFile);

        return filePath;
    }

}