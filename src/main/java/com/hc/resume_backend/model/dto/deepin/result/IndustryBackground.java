package com.hc.resume_backend.model.dto.deepin.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Judy
 * @create 2023-07-08-16:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndustryBackground {
    private int building_real_estate;
    private int finance;
    private int internet;
    private int education;
    private int engineer;
    private int sports;
    private int art;
    private int agriculture;
    private int law;
    private int medical_pharmaceutical_nursing;
    private int personnel_administration_management;

}
