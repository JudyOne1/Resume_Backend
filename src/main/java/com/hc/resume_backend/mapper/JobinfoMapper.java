package com.hc.resume_backend.mapper;

import com.hc.resume_backend.model.entity.Jobinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;

/**
* @author Judy
* @description 针对表【jobinfo】的数据库操作Mapper
* @createDate 2023-06-17 21:54:25
* @Entity com.hc.resume_backend.model.entity.Jobinfo
*/
public interface JobinfoMapper extends BaseMapper<Jobinfo> {

    public ArrayList<Long> getPidByJobId(Long jobId);

    public ArrayList<Long> getJobIdByPid(Long pid);

    public void insertMatchJobId(Long jobId,Long pid);

}




