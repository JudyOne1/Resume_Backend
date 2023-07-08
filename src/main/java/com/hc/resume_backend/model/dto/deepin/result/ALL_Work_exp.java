package com.hc.resume_backend.model.dto.deepin.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @author Judy
 * @create 2023-07-08-16:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ALL_Work_exp {
    private String work_time;
    private String work_num;
    private ArrayList<Work_exp> work_exp;
}
