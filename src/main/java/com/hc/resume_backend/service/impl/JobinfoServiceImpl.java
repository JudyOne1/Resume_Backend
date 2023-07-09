package com.hc.resume_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.resume_backend.common.BaseResponse;
import com.hc.resume_backend.common.ResultUtils;
import com.hc.resume_backend.constant.ResumeConstant;
import com.hc.resume_backend.model.entity.Baseinfo;
import com.hc.resume_backend.model.entity.Jobinfo;
import com.hc.resume_backend.service.BaseinfoService;
import com.hc.resume_backend.service.JobinfoService;
import com.hc.resume_backend.mapper.JobinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
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
    public ArrayList<Baseinfo> getBaseInfosByJobID(Long jobId) {
        //根据jobid查询pid，根据pid查询baseinfo
        JobinfoMapper jobinfoMapper = this.getBaseMapper();
        ArrayList<Long> pidByJobId = jobinfoMapper.getPidByJobId(jobId);
        if(pidByJobId.isEmpty()){
            return new ArrayList<>();
        }
        QueryWrapper<Baseinfo> queryWrapper = new QueryWrapper<>();
        for (Long pid : pidByJobId) {
            queryWrapper.eq("pid",pid).or();
        }

        ArrayList<Baseinfo> baseinfos = (ArrayList<Baseinfo>) baseinfoService.list(queryWrapper);

        return baseinfos;
    }

    @Override
    public ArrayList<Baseinfo> getBaseInfosByJobIDSORT(Long jobId, Integer sortId) {
        ArrayList<Baseinfo> infos = this.getBaseInfosByJobID(jobId);
        if (infos.isEmpty()){
            return new ArrayList<>();
        }
        ArrayList<Baseinfo> baseinfos = null;
        switch (sortId) {
            case 1:
                //根据工龄排序
                baseinfos = (ArrayList<Baseinfo>) infos.stream()
                        .sorted(Comparator.comparingDouble(Baseinfo::getWorkyears))
                        .collect(Collectors.toList());
                break;
            case 2:
                //根据年龄排序
                baseinfos = (ArrayList<Baseinfo>) infos.stream()
                        .sorted(Comparator.comparingDouble(Baseinfo::getAge))
                        .collect(Collectors.toList());
                break;
            case 3:
                //根据学历排序
                //todo 学历的修改判断
                baseinfos = (ArrayList<Baseinfo>) infos.stream()
                    .sorted(Comparator.comparing(Baseinfo::getLevel, (o1, o2) -> Integer.compare(getLevelCount(o1),getLevelCount(o2))))
                    .collect(Collectors.toList());
            case 4:
                //todo 根据相关度排序
                break;
        }

        return baseinfos;
    }

    public Integer getLevelCount(String level){
        switch (level){
            case "小学":
                return ResumeConstant.PRIMARY_SCHOOL;
            case "初中":
                return ResumeConstant.JUNIOR_HIGH_SCHOOL;
            case "高中":
                return ResumeConstant.SENIOR_HIGH_SCHOOL;
            case "中专":
                return ResumeConstant.POLYTECHNIC_SCHOOL;
            case "大专":
                return ResumeConstant.JUNIOR_COLLEGE;
            case "本科":
                return ResumeConstant.BACHELOR_DEGREE;
            case "硕士":
                return ResumeConstant.MASTER_DEGREE;
            case "博士":
                return ResumeConstant.DOCTORAL_DEGREE;
            case "博士后":
                return ResumeConstant.POSTDOCTORAL_DEGREE;
            default:
                return ResumeConstant.OTHER;
        }
    }
}




