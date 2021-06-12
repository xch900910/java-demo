package com.example.designPattern.chain;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/12 11:14
 */
public class CHandlerImpl extends AbstractHandler {
    public CHandlerImpl(String name) {
        super(name);
    }

    @Override
    public void operator(LeaveRequest leaveRequest) {
        System.out.println("流程轮到" + name + "处理");
        if (leaveRequest.getDay() > 20 && leaveRequest.getDay() < 30) {
            System.out.println(name + " handler");
        } else {
            if (this.handler != null) {
                this.handler.operator(leaveRequest);
            }
        }
    }
}
