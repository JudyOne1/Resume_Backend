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
    private int Building_RealEstate;
    private int Finance;
    private int Internet;
    private int Education;
    private int Engineer;
    private int Sports;
    private int Art;
    private int Agriculture;
    private int Law;
    private int Medical_Pharmaceutical_Nursing;
    private int Personnel_Administration_Management;

}
