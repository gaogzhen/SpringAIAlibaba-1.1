package com.gaogzhen.study.controller;

import com.gaogzhen.study.records.StudentRecord;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author gaogzhen
 * @create 2026-01-08
 */
@RestController
public class StructuredOutputController {

    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekChatClient;

    @GetMapping("/structuredOutput/chat")
    public StudentRecord chat(@RequestParam String name, @RequestParam String email) {

        String studentTemplate = """
                学号1002，我叫{name},大学专业软件工程，邮箱{email}。
                """;
        return deepseekChatClient.prompt()
                .user(promptUserSpec -> {
                    promptUserSpec.text(studentTemplate)
                            .param("name", name)
                            .param("email", email);
                })
                .call()
                .entity(StudentRecord.class);
    }
}
