package com.hc.resume_backend.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hc.resume_backend.common.BaseResponse;
import com.hc.resume_backend.common.ErrorCode;
import com.hc.resume_backend.common.ResultUtils;
import com.hc.resume_backend.model.entity.*;
import com.hc.resume_backend.model.vo.AllInfoVO;
import com.hc.resume_backend.model.vo.BaseinfoVO;
import com.hc.resume_backend.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Judy
 * @create 2023-06-19-15:41
 */
@RestController
@RequestMapping("/resume")
@ResponseBody
public class resumeController {
    //todo 可能要修改数据库and实体类和业务逻辑
    //todo 图片格式转pdf格式
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
        if(baseInfo == null){
            return ResultUtils.error(ErrorCode.FILEMISS_ERROR,"系统出现未知错误，请稍后再试");
        }
        HashMap<String, ArrayList<String>> detailInfo = detailinfoService.getSELFDetailInfo();
        if(detailInfo == null){
            return ResultUtils.error(ErrorCode.FILEMISS_ERROR,"系统出现未知错误，请稍后再试");
        }
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
    public BaseResponse<ArrayList<BaseinfoVO>> getAllBaseInfo(){
        ArrayList<Baseinfo> baseinfos = (ArrayList<Baseinfo>) baseinfoService.list(null);
        if (baseinfos == null){
            return ResultUtils.error(ErrorCode.FILEMISS_ERROR,"系统出现未知错误，请稍后再试");
        }
        ArrayList<BaseinfoVO> result = new ArrayList<>();
        for (Baseinfo info : baseinfos) {
            BaseinfoVO baseinfoVO = new BaseinfoVO();
            BeanUtils.copyProperties(info,baseinfoVO);
            result.add(baseinfoVO);
        }

        return ResultUtils.success(result);
    }

    @ApiOperation(value = "根据指定pid的获取简历的所有信息")
    @GetMapping("/getDetailInfoByPid")
    public BaseResponse<AllInfo> getDetailInfo(Long pid){

        QueryWrapper<Baseinfo> baseinfoQueryWrapper = new QueryWrapper<Baseinfo>();
        baseinfoQueryWrapper.eq("pid",pid);
        Baseinfo baseinfo = baseinfoService.getOne(baseinfoQueryWrapper);
        if (baseinfo == null){
            return ResultUtils.error(ErrorCode.FILEMISS_ERROR,"请检查参数");
        }
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

        //todo skill
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
