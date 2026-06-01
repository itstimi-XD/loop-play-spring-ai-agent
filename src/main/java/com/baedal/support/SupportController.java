package com.baedal.support;

import com.baedal.support.tool.OrderTools;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/support")
public class SupportController {

    private final ChatClient chatClient;

    // 2주차: Structured Output(JSON) 엔드포인트에도 OrderTools를 등록해
    //        Tool Calling과 Structured Output이 함께 동작하는지 직접 확인한다.
    public SupportController(ChatClient.Builder builder,
                             PerformanceLoggingAdvisor performanceAdvisor,
                             OrderTools orderTools) {
        this.chatClient = builder
                .defaultSystem(BaedalPrompt.SYSTEM_PROMPT)
                .defaultAdvisors(performanceAdvisor)
                .defaultTools(orderTools)
                .build();
    }

    @PostMapping
    public SupportResponse triage(@Valid @RequestBody ChatRequest req) {
        try {
            return chatClient
                    .prompt()
                    .user(req.message())
                    .call()
                    .entity(SupportResponse.class);
        } catch (Exception e) {
            log.error("LLM triage call failed", e);
            throw new SupportServiceException("고객 문의 처리 중 오류가 발생했습니다.", e);
        }
    }
}
