package com.gaogzhen.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author gaogzhen
 * @create 2026-01-08
 */
@RestController
public class PromptController {

    @Resource(name = "deepseek")
    private ChatModel deepseekChatModel;

    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekChatClient;


    @GetMapping("/prompt/chat")
    public Flux<String> chat(String question) {
        return deepseekChatClient.prompt()
                .system("你是一个法律助手，只回答法律问题，其他问题回复，我只能回答法律相关问题，其他问题请自行查阅相关文档。")
                .user(question)
                .stream()
                .content();
    }

    @GetMapping("/prompt/chat2")
    public Flux<ChatResponse> chat2(String question) {
        SystemMessage systemMessage = new SystemMessage("你是一个讲故事的助手，每个故事控制在300字以内。");
        UserMessage userMessage = new UserMessage(question);

        Prompt prompt = new Prompt(userMessage, systemMessage);

        return deepseekChatModel.stream(prompt);
    }


    /**
     * http://localhost:8005/prompt/chat3?question=葫芦娃
     * @param question
     * @return
     */
    @GetMapping("/prompt/chat3")
    public Flux<String> chat3(String question)
    {
        // 系统消息
        SystemMessage systemMessage = new SystemMessage("你是一个讲故事的助手," +
                "每个故事控制在600字以内且以HTML格式返回");

        // 用户消息
        UserMessage userMessage = new UserMessage(question);

        Prompt prompt = new Prompt(userMessage, systemMessage);

        return deepseekChatModel.stream(prompt)
                .mapNotNull(response -> response.getResults().getFirst().getOutput().getText());

    }

    /**
     * http://localhost:8005/prompt/chat4?question=葫芦娃
     * @param question
     * @return
     */
    @GetMapping("/prompt/chat4")
    public String chat4(String question)
    {
        AssistantMessage assistantMessage = Objects.requireNonNull(deepseekChatClient.prompt()
                        .user(question)
                        .call()
                        .chatResponse())
                .getResult()
                .getOutput();

        return assistantMessage.getText();

    }

    /**
     * http://localhost:8005/prompt/chat5?city=济南
     * 近似理解Tool后面章节讲解......
     * @param city
     * @return
     */
    @GetMapping("/prompt/chat5")
    public String chat5(String city)
    {

        String answer = Objects.requireNonNull(deepseekChatClient.prompt()
                        .user(city + "未来3天天气情况如何?")
                        .call()
                        .chatResponse())
                .getResult()
                .getOutput()
                .getText();

        ToolResponseMessage toolResponseMessage =
                new ToolResponseMessage(
                        List.of(new ToolResponseMessage.ToolResponse("1","获得天气",city)
                        )
                );

        String toolResponse = toolResponseMessage.getText();

        return answer + toolResponse;
    }

}
