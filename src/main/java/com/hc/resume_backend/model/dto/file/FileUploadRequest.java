package com.hc.resume_backend.model.dto.file;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * @author Judy
 * @create 2023-06-17-19:00
 */
@Data
@ApiModel("文件上传请求体")
public class FileUploadRequest {

    @ApiParam("base64格式数据")
    String base64File;
}
