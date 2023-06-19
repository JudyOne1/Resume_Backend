package com.hc.resume_backend.service;

import com.hc.resume_backend.model.entity.Baseinfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
* @author Judy
* @description 针对表【baseinfo】的数据库操作Service
* @createDate 2023-06-17 21:54:25
*/
public interface BaseinfoService extends IService<Baseinfo> {

    /**
     * 获取所有单独字段的集合
     * @return
     */
    HashMap<String,ArrayList<String>> getSELFBaseInfo();
}
