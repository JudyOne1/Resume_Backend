package com.hc.resume_backend.constant;

/**
 * @author Judy
 * @create 2023-06-27-20:45
 */
/**
 * 简历相关常量
 */
public interface ResumeConstant {
/**
 * 无|小学|初中|高中|中专|大专|本科|硕士|博士|博士后
 */
    //博士后
    Integer POSTDOCTORAL_DEGREE = 9;

    //博士
    Integer DOCTORAL_DEGREE = 8;

    //硕士
    Integer MASTER_DEGREE = 7;

    //本科
    Integer BACHELOR_DEGREE = 6;

    //大专
    Integer JUNIOR_COLLEGE = 5;

    //中专
    Integer POLYTECHNIC_SCHOOL = 4;

    //高中
    Integer SENIOR_HIGH_SCHOOL = 3;

    //初中
    Integer JUNIOR_HIGH_SCHOOL = 2;

    //小学
    Integer PRIMARY_SCHOOL = 1;

    //其他
    Integer OTHER = 0;

}
