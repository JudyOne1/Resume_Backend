package com.hc.resume_backend.service;

import com.hc.resume_backend.model.entity.Detailinfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.HashMap;

/**
* @author Judy
* @description 针对表【detailinfo】的数据库操作Service
* @createDate 2023-06-17 21:54:25
*/
public interface DetailinfoService extends IService<Detailinfo> {

    /**
     * 获取所有单独字段的集合
     * @return
     */
    HashMap<String, ArrayList<String>> getSELFDetailInfo();
}
