package com.gaogzhen.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 *
 * @author gaogzhen
 * @create 2026-01-06
 */
@RestController
public class OllamaController {

    @Resource
    @Qualifier("ollamaChatModel")
    private ChatModel chatModel;

    @GetMapping("/ollama/chat")
    public String chat(@RequestParam(name = "msg")  String msg) {
        return chatModel.call(msg);
    }

    @GetMapping("/ollama/streamChat")
    public Flux<String> streamChat(@RequestParam(name = "msg")  String msg) {
        return chatModel.stream(msg);
    }
}
