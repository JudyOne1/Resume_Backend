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
public class ALL_Edu_exp {
    private String edu_num;
    private ArrayList<Edu_exp> edu_exp;
}
