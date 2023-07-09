package com.hc.resume_backend.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hc.resume_backend.common.BaseResponse;
import com.hc.resume_backend.common.ErrorCode;
import com.hc.resume_backend.common.ResultUtils;
import com.hc.resume_backend.model.entity.*;
import com.hc.resume_backend.model.vo.*;
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

        ArrayList<String> Address = detailInfo.get("Address");
        finalMap.put("Address",Address);

//        ArrayList<String> Birthday = detailInfo.get("Birthday");
//        finalMap.put("Birthday",Birthday);

        ArrayList<String> nationality = detailInfo.get("nationality");
        finalMap.put("nationality",nationality);

        ArrayList<String> Police_face = detailInfo.get("Police_face");
        finalMap.put("Police_face",Police_face);

        return ResultUtils.success(finalMap);
    }

    @ApiOperation(value = "获取所有简历基本信息")
    @GetMapping("/getAllBaseInfo")
    public BaseResponse<ArrayList<BaseinfoVO>> getAllBaseInfo(){
        ArrayList<Baseinfo> baseinfos = (ArrayList<Baseinfo>) baseinfoService.list(null);
        if (baseinfos == null){
            return ResultUtils.error(ErrorCode.FILEMISS_ERROR,"暂时没有简历消息数据");
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
    public BaseResponse<AllInfoVO> getDetailInfo(Long pid){

        QueryWrapper<Baseinfo> baseinfoQueryWrapper = new QueryWrapper<Baseinfo>();
        baseinfoQueryWrapper.eq("pid",pid);
        Baseinfo baseinfo = baseinfoService.getOne(baseinfoQueryWrapper);
        if (baseinfo == null){
            return ResultUtils.error(ErrorCode.FILEMISS_ERROR,"请检查参数");
        }

//        LambdaQueryWrapper<Detailinfo> detailinfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        detailinfoLambdaQueryWrapper.eq(Detailinfo::getPid,pid);
        QueryWrapper<Detailinfo> detailinfoQueryWrapper = new QueryWrapper<>();
        detailinfoQueryWrapper.eq("pid",pid);
        Detailinfo detailinfo = detailinfoService.getOne(detailinfoQueryWrapper);

        //通过baseinfo的tag获取标签信息
        //通过detailinfo的skill获取技能信息
        //其余可以通过pid获取，工作简历and教育经历可能不止一个，需要注意
        /*String allTag = baseinfo.getAlltag();
        String[] tags = allTag.split("/");
        QueryWrapper<Taginfo> taginfoQueryWrapper = new QueryWrapper<>();
        for (int i = 0; i < tags.length; i++) {
            taginfoQueryWrapper.eq("id",tags[i]);
            if (i<(tags.length-1)){
                taginfoQueryWrapper.or();
            }
        }
        List<Taginfo> taginfoList = taginfoService.list(taginfoQueryWrapper);*/

        // capacityinfo 雷达图
        QueryWrapper<Capacityinfo> capacityinfoQueryWrapper = new QueryWrapper<>();
        capacityinfoQueryWrapper.eq("pid",pid);
        Capacityinfo capacityinfo = capacityinfoService.getOne(capacityinfoQueryWrapper);

//        LambdaQueryWrapper<Eduinfo> eduinfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        eduinfoLambdaQueryWrapper.eq(Eduinfo::getPid,pid);
        QueryWrapper<Eduinfo> eduinfoQueryWrapper = new QueryWrapper<>();
        eduinfoQueryWrapper.eq("pid",pid);
        List<Eduinfo> eduinfoList = eduinfoService.list(eduinfoQueryWrapper);

        ArrayList<EduinfoVO> eduinfoVOS = new ArrayList<>();
        for (Eduinfo eduinfo : eduinfoList) {
            EduinfoVO eduinfoVO = new EduinfoVO(eduinfo.getPid(), eduinfo.getEdubegin(), eduinfo.getEduend(), eduinfo.getCollage(), eduinfo.getMajor());
            eduinfoVOS.add(eduinfoVO);
        }

//        LambdaQueryWrapper<Workinfo> workinfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        workinfoLambdaQueryWrapper.eq(Workinfo::getPid,pid);
        QueryWrapper<Workinfo> workinfoQueryWrapper = new QueryWrapper<>();
        workinfoQueryWrapper.eq("pid",pid);
        List<Workinfo> workinfoList = workinfoService.list(workinfoQueryWrapper);

        ArrayList<WorkinfoVO> WorkinfoVOS = new ArrayList<>();
        for (Workinfo workinfo : workinfoList) {
            WorkinfoVO workinfoVO = new WorkinfoVO(workinfo.getPid(), workinfo.getWorkbegin(), workinfo.getWorkend(), workinfo.getWorktime(), workinfo.getCompanyinfo(), workinfo.getPosition());
            WorkinfoVOS.add(workinfoVO);
        }

        BaseinfoVO baseinfoVO = new BaseinfoVO(baseinfo.getPid(), baseinfo.getName(), baseinfo.getAge(), baseinfo.getLevel(), baseinfo.getCollage(), baseinfo.getWorkyears(), baseinfo.getAlltag());
        DetailinfoVO detailinfoVO = new DetailinfoVO(detailinfo.getPid(),detailinfo.getGender(), detailinfo.getAddress(), detailinfo.getBirthday(), detailinfo.getNationality(), detailinfo.getPoliceface(), detailinfo.getMail(),detailinfo.getPhone());
//        AllInfo allInfo = new AllInfo(baseinfo,detailinfo,eduinfoList,workinfoList,capacityinfo);
        AllInfoVO allInfoVO = new AllInfoVO(baseinfoVO, detailinfoVO,eduinfoVOS,WorkinfoVOS,capacityinfo);

        return ResultUtils.success(allInfoVO);
    }
}
