package com.example.designPattern.chain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/12 11:30
 */
@Data
@AllArgsConstructor
public class LeaveRequest {
    private String name;
    private int day;
    private String reason;
}
