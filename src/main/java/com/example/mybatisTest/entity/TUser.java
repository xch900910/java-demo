package com.example.mybatisTest.entity;

import lombok.Data;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/7/4 12:06
 */

@Data
public class TUser {
    private Integer id;

    /**
     * 名称
     */
    private String name;
}