package com.lixiang.hitravel.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * 文件上传
 */
@Controller
@Api(value = "文件操作类", tags = "文件操作类")
@RestController
@RequestMapping(value = "/file")
@Slf4j
public class FileController {

    @GetMapping(value = "/test")
    public String file() {
        System.out.println("111111");
        return "file";
    }

    @PostMapping(value = "/fileUpload")
    public String fileUpload(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
//        String filePath = "D://temp-rainy//"; // 上传后的路径 /Users/zhang/Desktop
        String filePath = "/images/"; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        System.out.println(filePath + fileName);
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename = "/img/" + fileName;
        return filename;
    }
}

