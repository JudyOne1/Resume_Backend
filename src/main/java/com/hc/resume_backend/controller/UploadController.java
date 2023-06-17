package com.hc.resume_backend.controller;

import com.hc.resume_backend.common.BaseResponse;
import com.hc.resume_backend.common.ErrorCode;
import com.hc.resume_backend.common.ResultUtils;
import com.hc.resume_backend.service.ObsService;
import com.hc.resume_backend.utils.Base64ToMultipartFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.ServerResponse;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

/**
 * @author Judy
 * @create 2023-06-15-21:04
 */
@RestController
@RequestMapping("/upload")
@Slf4j
@Api("上传相关接口")
public class UploadController {

    @Autowired
    private ObsService obsService;


    @ApiOperation(value = "上传文件")
    @PostMapping("/uploadFile")
    @ResponseBody
    @ApiParam(name = "base64File", value = "base64格式的文件", required = true)
    public BaseResponse uploadFile(String base64File) throws IOException {
        //base64File格式类似于： data:image/gif;base64,R0lGODlhHA
        final String[] base64Array = base64File.split(",");
        // base64转为流
        String dataUir = base64Array[0];
        String data = base64Array[1];
        Base64ToMultipartFile multipartFile = new Base64ToMultipartFile(data, dataUir);
        //获取格式类型
        String extension = multipartFile.getExtension();
        if (ObjectUtils.isEmpty(multipartFile) || multipartFile.getSize() <= 0) {
            System.out.println("文件为空");
            return ResultUtils.error(ErrorCode.FILEMISS_ERROR,"文件为空");
        }
        //上传到obs对象云存储中
        UUID uuid = UUID.randomUUID();
        obsService.saveData(uuid.toString()+"."+extension,multipartFile.getBytes());
        return ResultUtils.success("success");
    }


//    @ApiOperation(value = "下载文件")
//    @PostMapping("/downloadFile")
//    @ResponseBody
//    public BaseResponse downloadFile(String Key) throws IOException {
//        obsService.getData(Key);
//        return ResultUtils.success("success");
//    }


}
