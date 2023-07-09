package com.hc.resume_backend.model.dto.deepin;

import com.hc.resume_backend.model.dto.deepin.result.ALL_Edu_exp;
import com.hc.resume_backend.model.dto.deepin.result.ALL_Work_exp;
import com.hc.resume_backend.model.dto.deepin.result.IndustryBackground;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultMessage {
    private Long PID;
    private String name;
    private String gender;
    private String mail;
    private String phone_num;
    private String address;
    private String birthday;
    //民族
    private String race;
    //政治面貌
    private String police_face;
    private String age;
    //level
    private String max_degree;

    private ALL_Edu_exp edu_exp;
    private ALL_Work_exp work_exp;

    private String[] edu_label;
    private IndustryBackground industry_background;
    private String[] matchJobID;


}
