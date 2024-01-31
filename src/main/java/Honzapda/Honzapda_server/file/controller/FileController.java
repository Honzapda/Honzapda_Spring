package Honzapda.Honzapda_server.file.controller;


import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/upload")
    public ApiResult<List<String>> uploadObject(@RequestPart List<MultipartFile> images) throws Exception {
        return ApiResult.onSuccess(fileService.uploadObjects(images));
    }

    @DeleteMapping("/delete/{uuid}")
    public ApiResult<String> deleteObject(@PathVariable String uuid) {
        return ApiResult.onSuccess(fileService.deleteObject(uuid));
    }

}
