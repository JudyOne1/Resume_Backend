package com.hc.resume_backend.model.vo;

import com.hc.resume_backend.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Judy
 * @create 2023-06-19-21:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllInfoVO {
    BaseinfoVO baseinfo;
    DetailinfoVO detailinfo;
    List<EduinfoVO> eduinfo;
    List<WorkinfoVO> workinfo;
    Capacityinfo capacityinfos;
    String jobNames;
}
