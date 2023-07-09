package com.hc.resume_backend.controller;

import cn.hutool.core.lang.Pid;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hc.resume_backend.common.ErrorCode;
import com.hc.resume_backend.exception.BusinessException;
import com.hc.resume_backend.mapper.DetailinfoMapper;
import com.hc.resume_backend.mapper.JobinfoMapper;
import com.hc.resume_backend.mapper.WorkinfoMapper;
import com.hc.resume_backend.model.dto.deepin.ResultMessage;
import com.hc.resume_backend.model.dto.deepin.result.*;
import com.hc.resume_backend.model.entity.*;
import com.hc.resume_backend.server.TransmissionServer;
import com.hc.resume_backend.service.*;
import com.hc.resume_backend.utils.UuidUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Judy
 * @create 2023-06-20-21:34
 */
@RestController
@ResponseBody
@RequestMapping("/doDeepin")
@Slf4j
public class TransmissionController {
//todo delete
    @Autowired
    private TransmissionServer transmissionServer;

    @Autowired
    private DetailinfoService detailinfoService;

    @Resource
    private DetailinfoMapper detailinfoMapper;

    @Autowired
    private BaseinfoService baseinfoService;

    @Autowired
    private EduinfoService eduinfoService;

    @Autowired
    private CapacityinfoService capacityinfoService;

    @Resource
    private WorkinfoMapper workinfoMapper;

    @Resource
    private JobinfoMapper jobinfoMapper;

    @Autowired
    private UploadfileinfoService uploadfileinfoService;

    @PostMapping("/text")
    @ApiOperation("/测试连接用的，不用管")
    public void doDeepin() throws IOException {
        transmissionServer.sendMessage("hello");
    }

    @PostMapping("/message")
    @ApiOperation("/测试消息用的，不用管")
    public void message(String resultMessageJSON) throws IOException, ParseException {
        {
            //接收数据 处理数据并且保存到数据库中 需要修改handle属性 redis不用管等过期
            //有2种类型：1是app导入，2是数据集导入
            log.error("收到消息");

            //转换为Bean
            if (resultMessageJSON.isEmpty() || resultMessageJSON == null) {
                //抛异常
                throw new BusinessException(ErrorCode.FILEMISS_ERROR);
            }
            ResultMessage resultMessage = JSONUtil.toBean(resultMessageJSON, ResultMessage.class);

            //使用Java中的JSONObject类来处理JSON数据
            JSONObject jsonObj = new JSONObject(resultMessageJSON);

            //处理work_exp添加到all_work_exp中
            ALL_Work_exp all_work_exp = resultMessage.getWork_exp();

            ArrayList<Work_exp> workArrays = null;
            if (all_work_exp != null) {
                Object work_exp = jsonObj.get("work_exp");
                JSONObject jsonObj_work_exp = new JSONObject(work_exp.toString());
                workArrays = all_work_exp.getWork_exp();
                workArrays = new ArrayList<>();
                int workNumber = Integer.parseInt(all_work_exp.getWork_num());
                if (workNumber > 0) {
                    for (int i = 1; i <= workNumber; i++) {
                        Object min_work_exp = jsonObj_work_exp.get("work_exp" + i);
                        Work_exp exp = JSONUtil.toBean(min_work_exp.toString(), Work_exp.class);
                        workArrays.add(exp);
                    }
                }
            }


            //处理edu_exp添加到all_edu_exp中
            ALL_Edu_exp all_edu_exp = resultMessage.getEdu_exp();
            ArrayList<Edu_exp> eduArrays = null;
            if (all_work_exp != null) {
                Object edu_exp = jsonObj.get("edu_exp");
                JSONObject jsonObj_edu_exp = new JSONObject(edu_exp.toString());
                eduArrays = all_edu_exp.getEdu_exp();
                eduArrays = new ArrayList<>();
                int eduNumber = Integer.parseInt(all_edu_exp.getEdu_num());
                if (eduNumber > 0) {
                    for (int i = 1; i <= eduNumber; i++) {
                        Object min_edu_exp = jsonObj_edu_exp.get("edu_exp" + i);
                        Edu_exp exp = JSONUtil.toBean(min_edu_exp.toString(), Edu_exp.class);
                        log.warn(exp.toString());
                        eduArrays.add(exp);
                    }
                }
            }
            if (eduArrays != null) {
                all_edu_exp.setEdu_exp(eduArrays);
            }
            if (workArrays != null) {
                all_work_exp.setWork_exp(workArrays);
            }

            resultMessage.setEdu_exp(all_edu_exp);
            resultMessage.setWork_exp(all_work_exp);


            Long pid = UuidUtils.getId();
            //pid的判断 上传的简历拥有pid，数据集的简历没有pid
            if (resultMessage.getPID() != null) {
                pid = resultMessage.getPID();
                //todo 处理handle字段
                LambdaQueryWrapper<Uploadfileinfo> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Uploadfileinfo::getPid, pid);
                Uploadfileinfo one = uploadfileinfoService.getOne(queryWrapper);
                one.setHandle(1);
                uploadfileinfoService.update(one, queryWrapper);
            }

            //封装baseinfo
            String name = resultMessage.getName();
            int age = Integer.parseInt(resultMessage.getAge());
            String level = resultMessage.getMax_degree();
            //todo collage如果有标点符号，需要清除

            String collage = "UNKNOWN";
            if (resultMessage.getEdu_exp() != null) {
                collage = resultMessage.getEdu_exp().getEdu_exp().get(0).getSchool();
            }

            String[] tagsArray = resultMessage.getEdu_label();

            String AllTag = "";
            if (tagsArray.length > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (String str : tagsArray) {
                    stringBuilder.append(str).append("/");
                }
                stringBuilder.deleteCharAt(stringBuilder.toString().length() - 1);
                AllTag = stringBuilder.toString();
            }
            double workyears  = 0;
            if (all_work_exp != null) {
                workyears = Double.parseDouble(all_work_exp.getWork_time());
                //向上取整
                workyears = Math.ceil(workyears);
            }
            Baseinfo baseinfo = new Baseinfo(pid, name, age, level, collage, workyears, AllTag);
            baseinfoService.save(baseinfo);
            //封装detailinfo

            String gender = fillUNKNOWN(resultMessage.getGender());
            //todo 地址and生日的添加
            String mail = fillUNKNOWN(resultMessage.getMail());
            String phone_num = fillUNKNOWN(resultMessage.getPhone_num());
            String police_face = fillUNKNOWN(resultMessage.getPolice_face());
            String nationality = fillUNKNOWN(resultMessage.getRace());

            Detailinfo detailinfo = new Detailinfo(pid, gender, nationality, police_face, mail, phone_num);
            //保存到db中
            detailinfoMapper.insertBase(detailinfo);

            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM");

            //封装eduinfo
            if (all_work_exp != null){
                for (int i = 0; i < all_edu_exp.getEdu_exp().size(); i++) {
                    //是否有"至今"的问题
                    Edu_exp exp = all_edu_exp.getEdu_exp().get(i);
                    String[] split = exp.getTime().split("-");
                    Date startTime = format.parse(split[0]);
//            Date endTime = format.parse(split[1]);
                    Date endTime;
                    if (!split[1].equals("至今")) {
                        endTime = format.parse(split[1]);
                    } else {
                        //注意 "至今" ==？ 2023年4月
                        endTime = format.parse("2023.04");
                    }
                    String major = exp.getMajor();
                    String school = exp.getSchool();
                    Eduinfo eduinfo = new Eduinfo(pid, startTime, endTime, major, school);
                    //保存到db中
                    eduinfoService.save(eduinfo);
                }
            }

            if (all_work_exp != null){
                for (int i = 0; i < all_work_exp.getWork_exp().size(); i++) {
                    Work_exp exp = all_work_exp.getWork_exp().get(i);
                    String[] split = exp.getTime().split("-");
                    Date startTime = format.parse(split[0]);
                    Date endTime;
                    if (!split[1].equals("至今")) {
                        endTime = format.parse(split[1]);
                    } else {
                        //注意 "至今" ==？ 2023年4月
                        endTime = format.parse("2023.04");
                    }

                    //算出有多少年
                    long diffInMillies = Math.abs(endTime.getTime() - startTime.getTime());
                    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    long year = diff / 365;
                    if (diff - 365 * year > 0) {
                        //余数大于0，年数+1
                        year++;
                    }
                    String worktime = String.valueOf(year);
                    String job = exp.getJob();
                    String workplace = exp.getWorkplace();
                    //封装workinfo
                    Workinfo workinfo = new Workinfo(pid, startTime, endTime, worktime, job, workplace);
                    //保存到db中
                    workinfoMapper.insertBase(workinfo);
//            workinfoService.save(workinfo);

                }
            }

            //industry_background的处理  变成json保存到capacityinfo中
            IndustryBackground industry_background = resultMessage.getIndustry_background();
            Capacityinfo capacityinfo = new Capacityinfo();
            capacityinfo.setPid(pid);
            capacityinfo.setSkill(JSONUtil.toJsonStr(industry_background));
            capacityinfoService.save(capacityinfo);

            //matchJobID的处理
            String[] matchJobID = resultMessage.getMatchJobID();
            if (matchJobID.length != 0 && matchJobID != null){
                for (String jobId : matchJobID) {
                    jobinfoMapper.insertMatchJobId(Long.valueOf(jobId), pid);
                }
            }

            //空值填写UNKNOWN √

            //todo 此方法逻辑的bug排查，异常处理 七七八八

            //对之前写的所有逻辑进行排查 √

            //gender的统一 √

            //handle的修改 √

            //返回结果类VO √

            //身高体重修改 √
        }
    }

    public String fillUNKNOWN(String str){
        if (str == null){
            str = "UNKNOWN";
        }
        return str;
    }

//    public void message(String resultMessageJSON) throws IOException, ParseException {
//        //转换为Bean
//        if (resultMessageJSON.isEmpty()||resultMessageJSON==null){
//
//        }
//        ResultMessage resultMessage = JSONUtil.toBean(resultMessageJSON, ResultMessage.class);
//
//        //使用Java中的JSONObject类来处理JSON数据
//        JSONObject jsonObj = new JSONObject(resultMessageJSON);
//
//        //处理work_exp添加到all_work_exp中
//        ALL_Work_exp all_work_exp = resultMessage.getWork_exp();
//        Object work_exp = jsonObj.get("work_exp");
//
//        JSONObject jsonObj_work_exp = new JSONObject(work_exp.toString());
//        ArrayList<Work_exp> workArrays = all_work_exp.getWork_exp();
//        workArrays = new ArrayList<>();
//        int workNumber = Integer.parseInt(all_work_exp.getWork_num());
//        if (workNumber>0) {
//            for (int i = 1; i <= workNumber; i++) {
//                Object min_work_exp = jsonObj_work_exp.get("work_exp" + i);
//                Work_exp exp = JSONUtil.toBean(min_work_exp.toString(), Work_exp.class);
//                workArrays.add(exp);
//            }
//        }
//        //处理edu_exp添加到all_edu_exp中
//        ALL_Edu_exp all_edu_exp = resultMessage.getEdu_exp();
//        Object edu_exp = jsonObj.get("edu_exp");
//        JSONObject jsonObj_edu_exp = new JSONObject(edu_exp.toString());
//        ArrayList<Edu_exp> eduArrays = all_edu_exp.getEdu_exp();
//        eduArrays = new ArrayList<>();
//        int eduNumber = Integer.parseInt(all_edu_exp.getEdu_num());
//        if (eduNumber>0){
//            for (int i = 1; i <= eduNumber; i++) {
//                Object min_edu_exp = jsonObj_edu_exp.get("edu_exp" + i);
//                Edu_exp exp = JSONUtil.toBean(min_edu_exp.toString(), Edu_exp.class);
//                log.warn(exp.toString());
//                eduArrays.add(exp);
//            }
//        }
//        all_work_exp.setWork_exp(workArrays);
//        all_edu_exp.setEdu_exp(eduArrays);
//
//        resultMessage.setEdu_exp(all_edu_exp);
//        resultMessage.setWork_exp(all_work_exp);
//
//
//        Long pid = UuidUtils.getId();
//        //pid的判断 上传的简历拥有pid，数据集的简历没有pid
//        if (resultMessage.getPID()!=null){
//            pid = resultMessage.getPID();
//        }
//
//        //封装baseinfo
//        String name = resultMessage.getName();
//        int age = Integer.parseInt(resultMessage.getAge());
//        String level = resultMessage.getMax_degree();
//        String collage = resultMessage.getEdu_exp().getEdu_exp().get(0).getSchool();
//        String[] tagsArray = resultMessage.getEdu_label();
//
//        String AllTag = "";
//        if (tagsArray.length > 0){
//            StringBuilder stringBuilder = new StringBuilder();
//            for (String str : tagsArray) {
//                stringBuilder.append(str).append("/");
//            }
//            stringBuilder.deleteCharAt(stringBuilder.toString().length()-1);
//            AllTag = stringBuilder.toString();
//        }
//
//        double workyears = Double.parseDouble(all_work_exp.getWork_time());
//        Baseinfo baseinfo = new Baseinfo(pid,name,age,level,collage,workyears,AllTag);
//        baseinfoService.save(baseinfo);
//        //封装detailinfo
//        String gender = resultMessage.getGender();
//        String mail = resultMessage.getMail();
//        String phone_num = resultMessage.getPhone_num();
//        String police_face = resultMessage.getPolice_face();
//        String nationality = resultMessage.getRace();
//        Detailinfo detailinfo = new Detailinfo(pid, gender, nationality, police_face, mail, phone_num);
//        //保存到db中
//        detailinfoMapper.insertBase(detailinfo);
//
//        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM");
//
//        //封装eduinfo
//        for (int i = 0; i < all_edu_exp.getEdu_exp().size(); i++) {
//            Edu_exp exp = all_edu_exp.getEdu_exp().get(i);
//            String[] split = exp.getTime().split("-");
//            Date startTime = format.parse(split[0]);
//            Date endTime = format.parse(split[1]);
//            String major = exp.getMajor();
//            String school = exp.getSchool();
//            Eduinfo eduinfo = new Eduinfo(pid,startTime,endTime,major,school);
//            //保存到db中
//            eduinfoService.save(eduinfo);
//        }
//
//        //封装workinfo
//        for (int i = 0; i < all_work_exp.getWork_exp().size(); i++) {
//            Work_exp exp = all_work_exp.getWork_exp().get(i);
//            String[] split = exp.getTime().split("-");
//            Date startTime = format.parse(split[0]);
//            Date endTime ;
//            if (!split[1].equals("至今")){
//                endTime = format.parse(split[1]);
//            }else {
//                //注意 "至今"
//                endTime = format.parse("2023.01");
//            }
//
//            //算出有多少年
//            long diffInMillies = Math.abs(endTime.getTime() - startTime.getTime());
//            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
//            long year = diff / 365;
//            if (diff-365*year>0){
//                //余数大于0，年数+1
//                year++;
//            }
//            String worktime = String.valueOf(year);
//            String job = exp.getJob();
//            String workplace = exp.getWorkplace();
//            Workinfo workinfo = new Workinfo(pid, startTime, endTime,worktime, job, workplace);
//            //保存到db中
//            workinfoMapper.insertBase(workinfo);
////            workinfoService.save(workinfo);
//
//        }
//
//        //industry_background的处理  变成json保存到capacityinfo中
//        IndustryBackground industry_background = resultMessage.getIndustry_background();
//        Capacityinfo capacityinfo = new Capacityinfo();
//        capacityinfo.setSkill(JSONUtil.toJsonStr(industry_background));
//        capacityinfoService.save(capacityinfo);
//
//        //matchJobID的处理
//        String[] matchJobID = resultMessage.getMatchJobID();
//        for (String jobId : matchJobID) {
//            jobinfoMapper.insertMatchJobId(Long.valueOf(jobId),pid);
//        }
//
//    }


}
