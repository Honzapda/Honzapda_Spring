package Honzapda.Honzapda_server.file.service;

import Honzapda.Honzapda_server.file.data.dto.GCSRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    String uploadObject(MultipartFile image) throws IOException, Exception;

    void deleteObject(String objectName);
}
