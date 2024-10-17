package com.jeholppx.bbg.scoring;

import com.jeholppx.bbg.model.entity.App;
import com.jeholppx.bbg.model.entity.UserAnswer;

import java.util.List;

/**
 * 评分策略接口
 *
 * @author <a href="https://www.jehol-ppx.com">热河fen青</a>
 * @date 2024/10/15 20:11
 */
public interface ScoringStrategy {

    /**
     * 执行评分
     *
     * @param choices
     * @param app
     * @return
     * @throws Exception
     */
    UserAnswer doScore(List<String> choices, App app) throws Exception;
}
