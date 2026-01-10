package com.gaogzhen.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author gaogzhen
 * @create 2026-01-09
 */
@RestController
public class PersistentController {

    @Resource
    private ChatClient deepseekChatClient;

    @GetMapping("/persistent/chat")
    public String persistentChat(@RequestParam String userId, @RequestParam String msg){
        return deepseekChatClient.prompt(msg)
                .advisors((advisorSpec -> advisorSpec.param(CONVERSATION_ID, userId)))
                .call()
                .content();
    }
}
