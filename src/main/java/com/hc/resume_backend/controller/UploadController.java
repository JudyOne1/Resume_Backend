package com.hc.resume_backend.controller;

import com.hc.resume_backend.common.BaseResponse;
import com.hc.resume_backend.common.ErrorCode;
import com.hc.resume_backend.common.ResultUtils;
import com.hc.resume_backend.model.dto.file.FileDownloadRequest;
import com.hc.resume_backend.model.dto.file.FileUploadRequest;
import com.hc.resume_backend.service.ObsService;
import com.hc.resume_backend.utils.Base64ToMultipartFile;
import com.hc.resume_backend.utils.UuidUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public BaseResponse uploadFile(@RequestBody FileUploadRequest File) throws IOException {
        //base64File格式类似于： data:image/gif;base64,R0lGODlhHA
        //data:application/pdf;base64,
        //data:text/plain;base64,
        //data:application/vnd.openxmlformats-officedocument.wordprocessingml.document;base64,

        String base64File = File.getBase64File();

        final String[] base64Array = base64File.split(",");
        // base64转为流
        String dataUir = base64Array[0];
        String data = base64Array[1];
        Base64ToMultipartFile multipartFile = new Base64ToMultipartFile(data, dataUir);
        //获取格式类型
        String extension = multipartFile.getExtension();

        if (extension.equals("plain")){
            //如果是文本
            extension="txt";
        }
        if (extension.equals("vnd.openxmlformats-officedocument.wordprocessingml.document")){
            //如果是docx
            extension="docx";
        }
        if (ObjectUtils.isEmpty(multipartFile) || multipartFile.getSize() <= 0) {
            System.out.println("文件为空");
            return ResultUtils.error(ErrorCode.FILEMISS_ERROR,"文件为空");
        }
        //上传到obs对象云存储中
        Long uuid = UuidUtils.getId();
//        UUID uuid = UUID.randomUUID();
        obsService.saveData(uuid+"."+extension,multipartFile.getBytes());
        //todo redis

        return ResultUtils.success("success");
    }


    @ApiOperation(value = "下载文件")
    @PostMapping("/downloadFile")
    @ResponseBody
    public BaseResponse downloadFile(@RequestBody FileDownloadRequest fileDownloadRequest) throws IOException {
        String url = obsService.getData(fileDownloadRequest.getPid());
        return ResultUtils.success(url);
    }


}
