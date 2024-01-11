package Honzapda.Honzapda_server.file.service;

import Honzapda.Honzapda_server.file.data.dto.GCSRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    List<String> uploadObject(List<MultipartFile> multipartFiles) throws IOException, Exception;

    void deleteObject(String objectName);
}