package com.hc.resume_backend.model.dto.deepin.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Judy
 * @create 2023-07-08-16:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Work_exp {
    private String time;
    private String workplace;
    private String job;
}
