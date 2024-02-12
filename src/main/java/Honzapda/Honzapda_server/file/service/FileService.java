package Honzapda.Honzapda_server.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface FileService {

    List<String> uploadObjects(List<MultipartFile> multipartFiles) ;

    String uploadObject(MultipartFile multipartFile);

    String deleteObject(String objectName);

    String subStringUrl(String url);
}
