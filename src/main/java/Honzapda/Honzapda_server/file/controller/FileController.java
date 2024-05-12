package Honzapda.Honzapda_server.file.controller;


import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.apiPayload.code.status.SuccessStatus;
import Honzapda.Honzapda_server.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<List<String>> uploadObject(@RequestPart List<MultipartFile> images) {
        return ApiResult.onSuccess(SuccessStatus._CREATED,fileService.uploadObjects(images));
    }

    @DeleteMapping("/delete/{uuid}")
    public ApiResult<String> deleteObject(@PathVariable String uuid) {
        return ApiResult.onSuccess(fileService.deleteObject(uuid));
    }

}
