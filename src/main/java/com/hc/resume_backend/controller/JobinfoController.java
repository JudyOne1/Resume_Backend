package com.hc.resume_backend.controller;

import com.hc.resume_backend.common.BaseResponse;
import com.hc.resume_backend.common.ResultUtils;
import com.hc.resume_backend.model.entity.Jobinfo;
import com.hc.resume_backend.service.JobinfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Judy
 * @create 2023-06-18-17:47
 */
@RestController
@RequestMapping("/JobInfo")
@ResponseBody
public class JobinfoController {

    @Autowired
    private JobinfoService jobinfoService;

    @ApiOperation(value = "获取所有岗位信息")
    @GetMapping("/getAllJob")
    public BaseResponse<List<Jobinfo>> getAllJob(){
        List<Jobinfo> list = jobinfoService.list();
        return ResultUtils.success(list);
    }

    // todo 获取指定岗位下的简历信息

}
