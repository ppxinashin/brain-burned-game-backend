package com.jeholppx.bbg.manager;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.jeholppx.bbg.common.ErrorCode;
import com.jeholppx.bbg.exception.BusinessException;
import io.reactivex.Flowable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用AI调用能力
 *
 * @author <a href="https://www.jehol-ppx.com">热河fen青</a>
 * @date 2024/10/18 17:46
 */
@Component
public class AiManager {

    @Resource
    private GenerationParam.GenerationParamBuilder<?, ?> generationParamBuilder;

    /**
     * 稳定的随机数
     */
    private static final float STABLE_TEMPERATURE = 0.05f;

    /**
     * 不稳定的随机数
     */
    private static final float UNSTABLE_TEMPERATURE = 0.99f;


    /**
     * 同步请求（答案不稳定）
     *
     * @param systemMessage
     * @param userMessage
     * @return
     */
    public String doSyncUnstableRequest(String systemMessage, String userMessage) {
        return doRequest(systemMessage, userMessage, Boolean.FALSE, UNSTABLE_TEMPERATURE);
    }

    /**
     * 同步请求（答案较稳定）
     *
     * @param systemMessage
     * @param userMessage
     * @return
     */
    public String doSyncStableRequest(String systemMessage, String userMessage) {
        return doRequest(systemMessage, userMessage, Boolean.FALSE, STABLE_TEMPERATURE);
    }

    /**
     * 同步请求
     *
     * @param systemMessage
     * @param userMessage
     * @param temperature
     * @return
     */
    public String doSyncRequest(String systemMessage, String userMessage, Float temperature) {
        return doRequest(systemMessage, userMessage, Boolean.FALSE, temperature);
    }

    /**
     * 通用请求（简化消息传递）
     *
     * @param systemMessage
     * @param userMessage
     * @param stream
     * @param temperature
     * @return
     */
    public String doRequest(String systemMessage, String userMessage, Boolean stream, Float temperature) {
        List<Message> messageList = new ArrayList<>();
        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content(systemMessage)
                .build();
        messageList.add(systemMsg);
        Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content(userMessage)
                .build();
        messageList.add(userMsg);
        return doRequest(messageList, stream, temperature);
    }

    /**
     * 通用请求
     *
     * @param messages
     * @param stream
     * @param temperature
     * @return
     * @date 2024/10/18 19:54
     */
    public String doRequest(List<Message> messages, Boolean stream, Float temperature) {
        Generation gen = new Generation();
        GenerationParam generationParam = generationParamBuilder.messages(messages)
                .incrementalOutput(stream)
                .temperature(temperature)
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build();
        try {
            GenerationResult call = gen.call(generationParam);
            return call.getOutput().getChoices().get(0).getMessage().getContent();
        } catch (NoApiKeyException | InputRequiredException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
    }

    /**
     * 通用流式请求 (简化消息传递)
     *
     * @param systemMessage
     * @param userMessage
     * @param temperature
     * @return
     */
    public Flowable<GenerationResult> doStreamRequest (String systemMessage, String userMessage, Float temperature) {
        List<Message> messageList = new ArrayList<>();
        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content(systemMessage)
                .build();
        messageList.add(systemMsg);
        Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content(userMessage)
                .build();
        messageList.add(userMsg);
        return doStreamRequest(messageList, temperature);
    }

    /**
     * 通用流式请求
     *
     * @param messages
     * @param temperature
     * @return
     */
    public Flowable<GenerationResult> doStreamRequest(List<Message> messages, Float temperature) {
        Generation gen = new Generation();
        GenerationParam generationParam = generationParamBuilder.messages(messages)
                .incrementalOutput(Boolean.TRUE)
                .temperature(temperature)
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build();
        try {
            return gen.streamCall(generationParam);
        } catch (NoApiKeyException | InputRequiredException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
    }
}
