package com.hc.resume_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.resume_backend.model.entity.Workinfo;
import com.hc.resume_backend.service.WorkinfoService;
import com.hc.resume_backend.mapper.WorkinfoMapper;
import org.springframework.stereotype.Service;

/**
* @author Judy
* @description 针对表【workinfo】的数据库操作Service实现
* @createDate 2023-06-17 21:54:25
*/
@Service
public class WorkinfoServiceImpl extends ServiceImpl<WorkinfoMapper, Workinfo>
    implements WorkinfoService{

}




