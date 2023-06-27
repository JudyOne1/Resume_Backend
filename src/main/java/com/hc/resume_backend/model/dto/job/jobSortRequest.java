package com.hc.resume_backend.model.dto.job;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Judy
 * @create 2023-06-27-21:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class jobSortRequest {

    @ApiModelProperty("职位id(即jobid)")
    Long jobId;

    @ApiModelProperty("1工龄，2年龄，3学历，4相关度（没做）")
    Integer sortId;
}
