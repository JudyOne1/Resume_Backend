package com.hc.resume_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.resume_backend.common.BaseResponse;
import com.hc.resume_backend.common.ResultUtils;
import com.hc.resume_backend.model.entity.Baseinfo;
import com.hc.resume_backend.model.entity.Jobinfo;
import com.hc.resume_backend.service.BaseinfoService;
import com.hc.resume_backend.service.JobinfoService;
import com.hc.resume_backend.mapper.JobinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
* @author Judy
* @description 针对表【jobinfo】的数据库操作Service实现
* @createDate 2023-06-17 21:54:25
*/
@Service
public class JobinfoServiceImpl extends ServiceImpl<JobinfoMapper, Jobinfo>
    implements JobinfoService{

    @Autowired
    private BaseinfoService baseinfoService;

    @Override
    public BaseResponse<ArrayList<Baseinfo>> getBaseInfosByJobID(Long jobId) {
        //根据jobid查询pid，根据pid查询baseinfo
        JobinfoMapper jobinfoMapper = this.getBaseMapper();
        ArrayList<Long> pidByJobId = jobinfoMapper.getPidByJobId(jobId);

        QueryWrapper<Baseinfo> queryWrapper = new QueryWrapper<>();
        for (Long pid : pidByJobId) {
            queryWrapper.eq("pid",pid).or();
        }

        ArrayList<Baseinfo> baseinfos = (ArrayList<Baseinfo>) baseinfoService.list(queryWrapper);

        return ResultUtils.success(baseinfos);
    }
}




