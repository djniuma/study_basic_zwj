package com.basic.controller;

import com.basic.common.HttpResult;
import com.basic.common.OssResult;
import com.basic.util.QiniuPictureServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("oss")
public class OssUploadController {

    @Autowired
    private QiniuPictureServiceUtils qiniuPictureServiceUtils;

    @PostMapping("/upload")
    public HttpResult<OssResult> uploadImg(@RequestParam("file") MultipartFile file){

        try {
            return qiniuPictureServiceUtils.saveImage(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
