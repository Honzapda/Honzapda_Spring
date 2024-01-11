package Honzapda.Honzapda_server.file.data.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class GCSRequest {

    private String name;
    private MultipartFile file;

}