package com.hc.resume_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.resume_backend.model.entity.Baseinfo;
import com.hc.resume_backend.model.entity.Detailinfo;
import com.hc.resume_backend.service.BaseinfoService;
import com.hc.resume_backend.mapper.BaseinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author Judy
* @description 针对表【baseinfo】的数据库操作Service实现
* @createDate 2023-06-17 21:54:25
*/
@Service
public class BaseinfoServiceImpl extends ServiceImpl<BaseinfoMapper, Baseinfo>
    implements BaseinfoService{

    @Resource
    private BaseinfoMapper baseinfoMapper;


    @Override
    public HashMap<String,ArrayList<String>> getSELFBaseInfo() {

        ArrayList<Baseinfo> baseinfos = (ArrayList<Baseinfo>) baseinfoMapper.selectList(null);
        if (baseinfos.isEmpty()){
            return null;
        }

        HashMap<String, ArrayList<String>> map = new HashMap<>();
        ArrayList<String> helper = new ArrayList<>();
        baseinfos.forEach((item)->helper.add(item.getAge().toString()));
        baseinfos.stream().forEach((item)->helper.add(item.getAge().toString()));
        map.put("age",helper);

        ArrayList<String> helper1 = new ArrayList<>();
        baseinfos.stream().forEach((item)->helper1.add(item.getLevel()));
        map.put("level",helper1);

        ArrayList<String> helper2 = new ArrayList<>();
        baseinfos.stream().forEach((item)->helper2.add(item.getWorkyears().toString()));
        map.put("workYears",helper2);

        return map;
    }
}




