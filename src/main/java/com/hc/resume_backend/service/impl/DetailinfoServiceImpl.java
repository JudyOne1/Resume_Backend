package com.hc.resume_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.resume_backend.model.entity.Baseinfo;
import com.hc.resume_backend.model.entity.Detailinfo;
import com.hc.resume_backend.service.DetailinfoService;
import com.hc.resume_backend.mapper.DetailinfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
* @author Judy
* @description 针对表【detailinfo】的数据库操作Service实现
* @createDate 2023-06-17 21:54:25
*/
@Service
public class DetailinfoServiceImpl extends ServiceImpl<DetailinfoMapper, Detailinfo>
    implements DetailinfoService{

    @Resource
    private DetailinfoMapper detailinfoMapper;

    @Override
    public HashMap<String, ArrayList<String>> getSELFDetailInfo() {
        List<Detailinfo> detailinfos = detailinfoMapper.selectList(null);
        if (detailinfos.isEmpty()){
            return null;
        }
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        ArrayList<String> helper = new ArrayList<>();
        detailinfos.stream().forEach((item)->helper.add(item.getGender()));
        map.put("gender",helper);

        ArrayList<String> helper1 = new ArrayList<>();
        detailinfos.stream().forEach((item)->helper1.add(item.getAddress()));
        map.put("Address",helper1);

//        ArrayList<String> helper2 = new ArrayList<>();
//        detailinfos.stream().forEach((item)->helper2.add(item.getBirthday().toString()));
//        map.put("Birthday",helper2);

        ArrayList<String> helper3 = new ArrayList<>();
        detailinfos.stream().forEach((item)->helper3.add(item.getNationality()));
        map.put("nationality",helper3);

        ArrayList<String> helper4 = new ArrayList<>();
        //有没有空指针问题？
        detailinfos.stream().forEach((item)->helper4.add(item.getPoliceface()));
        map.put("Police_face",helper4);
        return map;
    }
}




