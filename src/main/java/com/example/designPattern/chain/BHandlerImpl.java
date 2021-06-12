package com.example.designPattern.chain;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/12 11:11
 */
public class BHandlerImpl extends AbstractHandler {
    public BHandlerImpl(String name) {
        super(name);
    }

    @Override
    public void operator(LeaveRequest leaveRequest) {
        System.out.println("流程轮到" + name + "处理");
        if (leaveRequest.getDay() > 10 && leaveRequest.getDay() < 20) {
            System.out.println(name + " handler");
        } else {
            if (this.handler != null) {
                this.handler.operator(leaveRequest);
            }
        }
    }
}
