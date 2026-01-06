package com.gaogzhen.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 *
 * @author gaogzhen
 * @create 2025-12-30
 */
@RestController
public class ChatHelloController {
    @Resource
    private ChatModel chatModel;

    /**
     * 通用调用
     * @param msg 用户消息
     * @return 模型响应
     */
    @GetMapping("/hello/doChat")
    public String doChat(@RequestParam(name = "msg", defaultValue = "你是谁")String msg) {
        return chatModel.call(msg);
    }

    /**
     * 推理调用
     * @param msg 用户消息
     * @return 模型响应
     */
    @GetMapping("/hello/streamChat")
    public Flux<String> stream(@RequestParam(name = "msg", defaultValue = "你是谁")String msg) {
        return chatModel.stream(msg);
    }
}
