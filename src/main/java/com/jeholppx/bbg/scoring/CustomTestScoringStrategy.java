package com.jeholppx.bbg.scoring;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jeholppx.bbg.model.dto.question.QuestionContentDTO;
import com.jeholppx.bbg.model.entity.App;
import com.jeholppx.bbg.model.entity.Question;
import com.jeholppx.bbg.model.entity.ScoringResult;
import com.jeholppx.bbg.model.entity.UserAnswer;
import com.jeholppx.bbg.model.vo.QuestionVO;
import com.jeholppx.bbg.service.QuestionService;
import com.jeholppx.bbg.service.ScoringResultService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义测评类评分策略
 *
 * @author <a href="https://www.jehol-ppx.com">热河fen青</a>
 * @date 2024/10/16 14:42
 */
@ScoringStrategyConfig(appType = 1, scoringStrategy = 0)
public class CustomTestScoringStrategy implements ScoringStrategy {

    @Resource
    private QuestionService questionService;

    @Resource
    private ScoringResultService scoringResultService;

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
        // 1. 根据 id 查询到题目和题目结果的信息
        Long appId = app.getId();
        Question question = questionService.getOne(
                Wrappers.lambdaQuery(Question.class).eq(Question::getAppId, appId)
        );

        List<ScoringResult> scoringResultList = scoringResultService.list(
                Wrappers.lambdaQuery(ScoringResult.class)
                        .eq(ScoringResult::getAppId, appId)
        );

        // 2. 统计用户每个选择对应的属性个数
        // 初始化一个 Map，用于存储每个选项的计数
        Map<String, Integer> optionCount = new HashMap<>();
        // 获取题目结构
        QuestionVO questionVO = QuestionVO.objToVo(question);
        // 获取题目列表
        List<QuestionContentDTO> questionContents = questionVO.getQuestionContent();
        // 遍历答案列表
        for (int i = 0; i < choices.size(); i++) {
            QuestionContentDTO questionContentDTO = questionContents.get(i);
            // 遍历题目中的选项
            List<QuestionContentDTO.Option> options = questionContentDTO.getOptions();
            for (QuestionContentDTO.Option option : options) {
                // 如果答案和选项的 key 匹配
                if (option.getKey().equals(choices.get(i))) {
                    // 获取选项的 result 属性
                    String result = option.getResult();

                    // 如果 result 属性不在 optionCount 中，初始化为 0
                    optionCount.putIfAbsent(result, 0);

                    // 在 optionCount 中增加计数
                    optionCount.put(result, optionCount.get(result) + 1);
                }
            }
        }

        // 3. 遍历每种计算结果，计算哪个结果得分更高
        // 初始化最高分数和最高分数对应的评分结果
        int maxScore = 0;
        ScoringResult maxScoringResult = scoringResultList.get(0);

        // 遍历评分结果列表
        for (ScoringResult scoringResult : scoringResultList) {
            List<String> resultProp = JSONUtil.toList(scoringResult.getResultProp(), String.class);
            // 计算当前评分结果的分数
            int score = resultProp.stream()
                    .mapToInt(prop -> optionCount.getOrDefault(prop, 0))
                    .sum();

            // 如果分数高于当前最高分数，更新最高分数和最高分数对应的评分结果
            if (score > maxScore) {
                maxScore = score;
                maxScoringResult = scoringResult;
            }
        }

        // 4. 构造返回值，填充答案的对象属性
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setAppId(appId);
        userAnswer.setAppType(app.getAppType());
        userAnswer.setScoringStrategy(app.getScoringStrategy());
        userAnswer.setChoices(JSONUtil.toJsonStr(choices));
        userAnswer.setResultId(maxScoringResult.getId());
        userAnswer.setResultName(maxScoringResult.getResultName());
        userAnswer.setResultDesc(maxScoringResult.getResultDesc());
        userAnswer.setResultPicture(maxScoringResult.getResultPicture());

        return userAnswer;
    }
}
