package com.fengling.ggkt.vod.controller;

import com.fengling.ggkt.result.Result;
import com.fengling.ggkt.vod.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/vod/file")
public class FileUploadController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        String url =  fileService.upload(file);
        return Result.ok(url).message("成功");
    }

}
