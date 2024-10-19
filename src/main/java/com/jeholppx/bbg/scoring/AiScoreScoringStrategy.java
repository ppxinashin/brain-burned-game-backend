package com.jeholppx.bbg.scoring;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jeholppx.bbg.manager.AiManager;
import com.jeholppx.bbg.model.dto.question.QuestionAnswerDTO;
import com.jeholppx.bbg.model.dto.question.QuestionContentDTO;
import com.jeholppx.bbg.model.entity.App;
import com.jeholppx.bbg.model.entity.Question;
import com.jeholppx.bbg.model.entity.UserAnswer;
import com.jeholppx.bbg.model.vo.QuestionVO;
import com.jeholppx.bbg.service.QuestionService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * AI 测评类应用评分策略
 *
 * @author <a href="https://www.jehol-ppx.com">热河fen青</a>
 * @date 2024/10/19 11:18
 */
@ScoringStrategyConfig(appType = 0, scoringStrategy = 1)
public class AiScoreScoringStrategy implements ScoringStrategy {

    @Resource
    private QuestionService questionService;

    @Resource
    private AiManager aiManager;

    private static final String AI_SCORE_SCORING_SYSTEM_MESSAGE = "你是一位严谨的阅卷专家，我会给你如下信息：\n" +
            "```\n" +
            "应用名称，\n" +
            "【【【应用描述】】】，\n" +
            "题目和用户回答的列表：格式为 [{\"title\": \"题目\", \"userAnswer\": \"用户回答\", \"correctAnswer\": \"正确答案\", \"score\": \"本题分值\"}]\n" +
            "```\n" +
            "\n" +
            "请你根据上述信息，按照以下步骤来对用户进行评价：\n" +
            "1. 要求：需要给出一个明确的评分，若题目中用户回答与正确答案不一致，则本题记为0分，正确时按本题分值计分，求出得分总和，并给予对应称号（尽量简短）和改进意见（尽量详细，大于 200 字）\n" +
            "2. 严格按照下面的 json 格式输出评价名称和评价描述\n" +
            "```\n" +
            "{\"resultScore\": \"最终得分\", \"resultName\": \"称号\", \"resultDesc\": \"改进意见\"}\n" +
            "```\n" +
            "3. 返回格式必须为 JSON 对象";

    private String getAiScoreScoringUserMessage(App app, List<QuestionContentDTO> questionContentDTOList, List<String> choices) {
        StringBuilder userMessage = new StringBuilder();
        userMessage.append(app.getAppName()).append("\n");
        userMessage.append(app.getAppDesc()).append("\n");
        List<QuestionAnswerDTO> questionAnswerDTOList = new ArrayList<>();
        for (int i = 0; i < questionContentDTOList.size(); i++) {
            QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO();
            questionAnswerDTO.setTitle(questionContentDTOList.get(i).getTitle());
            questionAnswerDTO.setUserAnswer(choices.get(i));
            List<QuestionContentDTO.Option> options = questionContentDTOList.get(i).getOptions();
            for (QuestionContentDTO.Option option : options) {
                int score = option.getScore();
                if (score != 0) {
                    questionAnswerDTO.setCorrectAnswer(option.getKey());
                    questionAnswerDTO.setScore(score);
                    break;
                }
            }
            questionAnswerDTOList.add(questionAnswerDTO);
        }
        userMessage.append(JSONUtil.toJsonStr(questionAnswerDTOList));
        return userMessage.toString();
    }

    /**
     * 执行评分
     *
     * @param choices
     * @param app
     * @return
     * @throws Exception
     */
    @Override
    public UserAnswer doScore(List<String> choices, App app) throws Exception {
        Long appId = app.getId();
        // 1. 根据 id 查询到题目
        Question question = questionService.getOne(
                Wrappers.lambdaQuery(Question.class).eq(Question::getAppId, appId)
        );
        QuestionVO questionVO = QuestionVO.objToVo(question);
        List<QuestionContentDTO> questionContent = questionVO.getQuestionContent();
        // 2. 调用 AI 获取结果
        // 封装 Prompt
        String userMessage = getAiScoreScoringUserMessage(app, questionContent, choices);
        // AI 生成
        String result = aiManager.doSyncStableRequest(AI_SCORE_SCORING_SYSTEM_MESSAGE, userMessage);
        // 结果处理
        int start = result.indexOf("{");
        int end = result.lastIndexOf("}");
        String json = result.substring(start, end + 1);
        // 3. 构造返回值，填充答案对象的属性
        UserAnswer userAnswer = JSONUtil.toBean(json, UserAnswer.class);
        userAnswer.setAppId(appId);
        userAnswer.setAppType(app.getAppType());
        userAnswer.setResultScore(userAnswer.getResultScore());
        userAnswer.setScoringStrategy(app.getScoringStrategy());
        userAnswer.setChoices(JSONUtil.toJsonStr(choices));
        return userAnswer;
    }

}
