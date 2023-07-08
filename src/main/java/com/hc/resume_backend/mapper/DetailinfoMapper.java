package com.hc.resume_backend.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hc.resume_backend.model.entity.Detailinfo;

import java.util.ArrayList;

/**
* @author Judy
* @description 针对表【detailinfo】的数据库操作Mapper
* @createDate 2023-06-17 21:54:25
* @Entity com.hc.resume_backend.model.entity.Detailinfo
*/
public interface DetailinfoMapper extends BaseMapper<Detailinfo> {
    public void insertBase(Detailinfo detailinfo);
}




