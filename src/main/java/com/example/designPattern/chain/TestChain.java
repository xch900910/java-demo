package com.example.designPattern.chain;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/12 11:25
 */
public class TestChain {
    public static void main(String[] args) {
        AHandlerImpl aHandler = new AHandlerImpl("张三");
        BHandlerImpl bHandler = new BHandlerImpl("李车");
        CHandlerImpl cHandler = new CHandlerImpl("王五");
        aHandler.setNextHandler(bHandler);
        bHandler.setNextHandler(cHandler);
        LeaveRequest leaveRequest = new LeaveRequest("哈哈", 16, "世界那么大，我想去看看");
        aHandler.operator(leaveRequest);

    }
}
