package com.jeholppx.bbg.ai;

import com.jeholppx.bbg.manager.AiManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class QwenAiTest {

    @Resource
    private AiManager aiManager;

    @Test
    public void test() {
        System.out.println("通义千问测试");
        String unstableRequest = aiManager.doSyncUnstableRequest("You are a helpful assistant.", "你是谁？");
        System.out.println(unstableRequest);
        String stableRequest = aiManager.doSyncStableRequest("You are a helpful assistant.", "你是谁？");
        System.out.println(stableRequest);
    }
}
