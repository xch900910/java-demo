package com.example.deferredResultTest.service;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-07-07 14:27
 **/

import com.example.deferredResultTest.model.DeferredResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Service
@Slf4j
public class DeferredResultService {

    private Map<String, Consumer<DeferredResultResponse>> taskMap;

    public DeferredResultService() {
        taskMap = new ConcurrentHashMap<>();
    }

    /**
     * 将请求id与setResult映射
     *
     * @param requestId
     * @param deferredResult
     */
    public void process(String requestId, DeferredResult<DeferredResultResponse> deferredResult) {
        // 请求超时的回调函数
        deferredResult.onTimeout(() -> {
            log.info("time out.......");
            taskMap.remove(requestId);
            DeferredResultResponse deferredResultResponse = new DeferredResultResponse();
            deferredResultResponse.setCode(HttpStatus.REQUEST_TIMEOUT.value());
            deferredResultResponse.setMsg(DeferredResultResponse.Msg.TIMEOUT.getDesc());
            deferredResult.setResult(deferredResultResponse);
        });
        log.info("not block..........");
        Optional.ofNullable(taskMap)
                .filter(t -> !t.containsKey(requestId))
                .orElseThrow(() -> new IllegalArgumentException(String.format("requestId=%s is existing", requestId)));

        // 这里的Consumer，就相当于是传入的DeferredResult对象的地址
        // 所以下面settingResult方法中"taskMap.get(requestId)"就是Controller层创建的对象
        taskMap.putIfAbsent(requestId, deferredResult::setResult);
    }

    /**
     * 这里相当于异步的操作方法
     * 设置DeferredResult对象的setResult方法
     *
     * @param requestId
     * @param deferredResultResponse
     */
    public void settingResult(String requestId, DeferredResultResponse deferredResultResponse) {
        if (taskMap.containsKey(requestId)) {
            Consumer<DeferredResultResponse> deferredResultResponseConsumer = taskMap.get(requestId);
            // 这里相当于DeferredResult对象的setResult方法
            deferredResultResponseConsumer.accept(deferredResultResponse);
            taskMap.remove(requestId);
        }
    }

}