package br.com.entregas.Entregas.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

import br.com.entregas.Entregas.core.constants.ExceptionMessageConstant;
import br.com.entregas.Entregas.core.exceptions.DomainException;

public final class UploadConfig {
    public final static void upload(String uploadDir, String storageFileName, String fileName, MultipartFile image) {
        try {
            Files.deleteIfExists(Paths.get(uploadDir + fileName));
            InputStream inputStream = image.getInputStream();
            Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new DomainException(ExceptionMessageConstant.fileError);
        }
    }
}
