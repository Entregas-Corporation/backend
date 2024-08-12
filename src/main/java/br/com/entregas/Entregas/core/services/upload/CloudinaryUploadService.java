package br.com.entregas.Entregas.core.services.upload;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import br.com.entregas.Entregas.core.constants.ExceptionMessageConstant;
import br.com.entregas.Entregas.core.exceptions.DomainException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CloudinaryUploadService {

    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile file, String publicId) {
        try {
            var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "public_id", publicId,
                    "overwrite", true));
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new DomainException(ExceptionMessageConstant.fileError);
        }
    }

    public void deleteFile(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new DomainException(ExceptionMessageConstant.fileError);
        }
    }
}
