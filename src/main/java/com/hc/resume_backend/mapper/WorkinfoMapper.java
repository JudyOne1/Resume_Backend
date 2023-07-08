package com.hc.resume_backend.mapper;

import com.hc.resume_backend.model.entity.Workinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Judy
* @description 针对表【workinfo】的数据库操作Mapper
* @createDate 2023-06-17 21:54:25
* @Entity com.hc.resume_backend.model.entity.Workinfo
*/
public interface WorkinfoMapper extends BaseMapper<Workinfo> {

    public void insertBase(Workinfo workinfo);
}




