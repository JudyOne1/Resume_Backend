package com.hc.resume_backend.model.entity;

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
public class AllInfo {
    Baseinfo baseinfo;
    Detailinfo detailinfo;
    List<Eduinfo> eduinfo;
    List<Workinfo> workinfo;
//    List<Taginfo> taginfos;
    Capacityinfo capacityinfos;
}
