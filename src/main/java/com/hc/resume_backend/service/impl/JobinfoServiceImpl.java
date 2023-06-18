package com.hc.resume_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.resume_backend.model.entity.Jobinfo;
import com.hc.resume_backend.service.JobinfoService;
import com.hc.resume_backend.mapper.JobinfoMapper;
import org.springframework.stereotype.Service;

/**
* @author Judy
* @description 针对表【jobinfo】的数据库操作Service实现
* @createDate 2023-06-17 21:54:25
*/
@Service
public class JobinfoServiceImpl extends ServiceImpl<JobinfoMapper, Jobinfo>
    implements JobinfoService{

}




