package com.hc.resume_backend.service;

import com.hc.resume_backend.common.BaseResponse;
import com.hc.resume_backend.common.ResultUtils;
import com.hc.resume_backend.model.entity.Baseinfo;
import com.hc.resume_backend.model.entity.Jobinfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;

/**
* @author Judy
* @description 针对表【jobinfo】的数据库操作Service
* @createDate 2023-06-17 21:54:25
*/
public interface JobinfoService extends IService<Jobinfo> {

    ArrayList<Baseinfo> getBaseInfosByJobID(Long jobId);

    String getJobNameByJobID(ArrayList<Long> jobId);

    ArrayList<Baseinfo> getBaseInfosByJobIDSORT(Long jobId, Integer sortId);
}
