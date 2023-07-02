package com.hc.resume_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hc.resume_backend.common.BaseResponse;
import com.hc.resume_backend.common.ErrorCode;
import com.hc.resume_backend.common.ResultUtils;
import com.hc.resume_backend.model.dto.job.jobInfoInsertRequest;
import com.hc.resume_backend.model.dto.job.jobInfoUpdateRequest;
import com.hc.resume_backend.model.dto.job.jobSortRequest;
import com.hc.resume_backend.model.entity.Baseinfo;
import com.hc.resume_backend.model.entity.Jobinfo;
import com.hc.resume_backend.model.vo.BaseinfoVO;
import com.hc.resume_backend.model.vo.JobinfoVO;
import com.hc.resume_backend.service.JobinfoService;
import com.hc.resume_backend.utils.UuidUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public BaseResponse<List<JobinfoVO>> getAllJob(){
        List<Jobinfo> list = jobinfoService.list();
        if (list.isEmpty() || list==null){
            return ResultUtils.error(ErrorCode.FILEMISS_ERROR,"无法查询数据，请联系管理员");
        }
        ArrayList<JobinfoVO> result = new ArrayList<>();
        for (Jobinfo jobinfo : list) {
            JobinfoVO jobinfoVO = new JobinfoVO();
            BeanUtils.copyProperties(jobinfo,jobinfoVO);
            result.add(jobinfoVO);
        }

        return ResultUtils.success(result);
    }

    @ApiOperation("获取指定岗位下的简历基本信息")
    @GetMapping("/getBaseInfosByJobID")
    public BaseResponse<ArrayList<BaseinfoVO>> getBaseInfosByJobID(Long jobId){
        ArrayList<Baseinfo> infos = jobinfoService.getBaseInfosByJobID(jobId);
        if (infos.isEmpty()){
            return ResultUtils.error(ErrorCode.FILEMISS_ERROR,"暂时无匹配简历");
        }
        ArrayList<BaseinfoVO> result = new ArrayList<>();
        for (Baseinfo info : infos) {
            BaseinfoVO baseinfoVO = new BaseinfoVO();
            BeanUtils.copyProperties(info,baseinfoVO);
            result.add(baseinfoVO);
        }

        return ResultUtils.success(result);
    }

    @ApiOperation("获取指定岗位下的简历基本信息(排序)")
    @PostMapping("/getBaseInfosByJobIDSORT")
    public BaseResponse<ArrayList<BaseinfoVO>> getBaseInfosByJobIDSORT(@RequestBody jobSortRequest jobSortRequest){
        Long jobId = jobSortRequest.getJobId();
        Integer sortId = jobSortRequest.getSortId();
        int[] numbers = new int[]{1,2,3,4};
        boolean SortIdFlag = false;
        for (int number : numbers) {
            if (number == sortId){
                SortIdFlag = true;
            }
        }
        if (!SortIdFlag){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"请求参数(sortId)出现错误");
        }
        ArrayList<Baseinfo> infos = jobinfoService.getBaseInfosByJobIDSORT(jobId, sortId);
        if (infos.isEmpty()){
            return ResultUtils.error(ErrorCode.FILEMISS_ERROR,"暂时无数据");
        }
        ArrayList<BaseinfoVO> result = new ArrayList<>();
        for (Baseinfo info : infos) {
            BaseinfoVO baseinfoVO = new BaseinfoVO();
            BeanUtils.copyProperties(info,baseinfoVO);
            result.add(baseinfoVO);
        }

        return ResultUtils.success(result);
    }


    @ApiOperation("新增岗位信息")
    @PostMapping("/insertJobInfo")
    public BaseResponse<String> insertJobInfo(@RequestBody jobInfoInsertRequest jobInfoInsertRequest){
        Jobinfo jobinfo = new Jobinfo();
        jobinfo.setJobid(UuidUtils.getId());
        jobinfo.setJobname(jobInfoInsertRequest.getJobname());
        jobinfo.setRequirement(jobInfoInsertRequest.getRequirement());
        jobinfo.setResponsibility(jobInfoInsertRequest.getResponsibility());
        boolean save = jobinfoService.save(jobinfo);
        if (!save){
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"新增失败");
        }
        return ResultUtils.success("新增成功");
    }

    @ApiOperation("删除岗位信息")
    @GetMapping("/deleteJobInfo")
    public BaseResponse<String> deleteJobInfo(Long jobId){
        LambdaQueryWrapper<Jobinfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Jobinfo::getJobid,jobId);
        List<Jobinfo> list = jobinfoService.list(queryWrapper);
        if (list.isEmpty()){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"无此jobId");
        }
        jobinfoService.remove(queryWrapper);
        return ResultUtils.success("删除成功");
    }

    @ApiOperation("修改岗位信息")
    @PostMapping("/updateJobInfo")
    public BaseResponse<String> updateJobInfo(@RequestBody jobInfoUpdateRequest jobInfoUpdateRequest){
        Long jobid = jobInfoUpdateRequest.getJobid();
        Jobinfo jobinfo = new Jobinfo();
        jobinfo.setJobid(jobid);
        jobinfo.setJobname(jobInfoUpdateRequest.getJobname());
        jobinfo.setRequirement(jobInfoUpdateRequest.getRequirement());
        jobinfo.setResponsibility(jobInfoUpdateRequest.getResponsibility());

        LambdaQueryWrapper<Jobinfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Jobinfo::getJobid,jobid);
        List<Jobinfo> list = jobinfoService.list(queryWrapper);
        if (list.isEmpty()){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"无此jobId");
        }
        jobinfoService.update(jobinfo,queryWrapper);
        return ResultUtils.success("修改成功");
    }
}
