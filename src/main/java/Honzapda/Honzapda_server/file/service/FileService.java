package Honzapda.Honzapda_server.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    List<String> uploadObjects(List<MultipartFile> multipartFiles) throws IOException, Exception;

    String uploadObject(MultipartFile multipartFile) throws IOException, Exception;

    String deleteObject(String objectName);
}
