package com.hc.resume_backend.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hc.resume_backend.common.BaseResponse;

import com.hc.resume_backend.common.ResultUtils;
import com.hc.resume_backend.model.entity.*;
import com.hc.resume_backend.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private EduinfoService eduinfoService;

    @Autowired
    private WorkinfoService workinfoService;

    @Autowired
    private TaginfoService taginfoService;

    @Autowired
    private CapacityinfoService capacityinfoService;


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

    @ApiOperation(value = "获取所有简历基本信息")
    @GetMapping("/getAllBaseInfo")
    public BaseResponse<ArrayList<Baseinfo>> getAllBaseInfo(){
        ArrayList<Baseinfo> baseinfos = (ArrayList<Baseinfo>) baseinfoService.list(null);
        return ResultUtils.success(baseinfos);
    }

    @ApiOperation(value = "根据指定pid的获取简历的所有信息")
    @GetMapping("/getDetailInfoByPid")
    public BaseResponse<AllInfo> getDetailInfo(@RequestBody Long pid){
        QueryWrapper<Baseinfo> baseinfoQueryWrapper = new QueryWrapper<Baseinfo>();
        baseinfoQueryWrapper.eq("pid",pid);
        Baseinfo baseinfo = baseinfoService.getOne(baseinfoQueryWrapper);

        LambdaQueryWrapper<Detailinfo> detailinfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        detailinfoLambdaQueryWrapper.eq(Detailinfo::getPid,pid);
        Detailinfo detailinfo = detailinfoService.getOne(detailinfoLambdaQueryWrapper);

        //通过baseinfo的tag获取标签信息
        //通过detailinfo的skill获取技能信息
        //其余可以通过pid获取，工作简历and教育经历可能不止一个，需要注意
        String allTag = baseinfo.getAlltag();
        String[] tags = allTag.split("/");
        QueryWrapper<Taginfo> taginfoQueryWrapper = new QueryWrapper<>();
        for (int i = 0; i < tags.length; i++) {
            taginfoQueryWrapper.eq("id",tags[i]);
            if (i<(tags.length-1)){
                taginfoQueryWrapper.or();
            }
        }
        List<Taginfo> taginfoList = taginfoService.list(taginfoQueryWrapper);

        QueryWrapper<Capacityinfo> capacityinfoQueryWrapper = new QueryWrapper<>();
        String allSkill = detailinfo.getSkill();
        String[] skills = allSkill.split("/");
        for (int i = 0; i < skills.length; i++) {
            capacityinfoQueryWrapper.eq("id",skills[i]);
            if (i<(skills.length-1)){
                capacityinfoQueryWrapper.or();
            }
        }
        List<Capacityinfo> capacityinfoList = capacityinfoService.list(capacityinfoQueryWrapper);

        LambdaQueryWrapper<Eduinfo> eduinfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        eduinfoLambdaQueryWrapper.eq(Eduinfo::getPid,pid);
        List<Eduinfo> eduinfoList = eduinfoService.list(eduinfoLambdaQueryWrapper);

        LambdaQueryWrapper<Workinfo> workinfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        workinfoLambdaQueryWrapper.eq(Workinfo::getPid,pid);
        List<Workinfo> workinfoList = workinfoService.list(workinfoLambdaQueryWrapper);


        AllInfo allInfo = new AllInfo(baseinfo,detailinfo,eduinfoList,workinfoList,taginfoList,capacityinfoList);

        return ResultUtils.success(allInfo);
    }
}
