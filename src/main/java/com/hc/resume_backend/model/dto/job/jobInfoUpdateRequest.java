package com.hc.resume_backend.model.dto.job;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Judy
 * @create 2023-06-27-19:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class jobInfoUpdateRequest {

    /**
     * 职位id
     */
    @ApiModelProperty("职位id(即jobid)")
    private Long jobid;

    /**
     * 职位名称
     */
    @ApiModelProperty("职位名称")
    private String jobname;

    /**
     * 岗位职责
     */
    @ApiModelProperty("岗位职责")
    private String responsibility;

    /**
     * 岗位要求
     */
    @ApiModelProperty("岗位要求")
    private String requirement;
}
