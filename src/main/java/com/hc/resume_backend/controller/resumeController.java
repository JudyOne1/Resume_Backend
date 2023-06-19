package com.hc.resume_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hc.resume_backend.common.BaseResponse;

import com.hc.resume_backend.common.ResultUtils;
import com.hc.resume_backend.model.entity.Baseinfo;
import com.hc.resume_backend.model.entity.Detailinfo;
import com.hc.resume_backend.service.BaseinfoService;
import com.hc.resume_backend.service.DetailinfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author Judy
 * @create 2023-06-19-15:41
 */
@RestController
@RequestMapping("/resume")
@ResponseBody
public class resumeController {
    @Autowired
    private BaseinfoService baseinfoService;

    @Autowired
    private DetailinfoService detailinfoService;

    @ApiOperation(value = "获取简历统计信息")
    @GetMapping("/getStatisticsInfo")
    public BaseResponse<HashMap<String, ArrayList<String>>> getStatisticsInfo(){

        HashMap<String, ArrayList<String>> baseInfo = baseinfoService.getSELFBaseInfo();

        HashMap<String, ArrayList<String>> detailInfo = detailinfoService.getSELFDetailInfo();
        HashMap<String, ArrayList<String>> finalMap = new HashMap<>();
        ArrayList<String> age = baseInfo.get("age");
        finalMap.put("age",age);
        ArrayList<String> level = baseInfo.get("level");
        finalMap.put("level",level);
        ArrayList<String> workYears = baseInfo.get("workYears");
        finalMap.put("workYears",workYears);

        ArrayList<String> gender = detailInfo.get("gender");
        finalMap.put("gender",gender);
        ArrayList<String> height = detailInfo.get("height");
        finalMap.put("height",height);
        ArrayList<String> Birthday = detailInfo.get("Birthday");
        finalMap.put("Birthday",Birthday);
        ArrayList<String> Birthplane = detailInfo.get("Birthplane");
        finalMap.put("Birthplane",Birthplane);
        ArrayList<String> Resident = detailInfo.get("Resident");
        finalMap.put("Resident",Resident);

        return ResultUtils.success(finalMap);
    }

//    @ApiOperation(value = "获取简历基本信息")
//    @GetMapping("/getAllBaseInfo")
//    public BaseResponse<ArrayList<Baseinfo>> getAllBaseInfo(){
//
//
//    }
//
//    @ApiOperation(value = "获取指定id的简历详细信息")
//    @GetMapping("/getDetailInfo")
//    public BaseResponse<ArrayList<Baseinfo>> getDetailInfo(){
//
//
//    }
}
