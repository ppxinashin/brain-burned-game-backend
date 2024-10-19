package com.jeholppx.bbg.model.dto.question;

import lombok.Data;

/**
 * 题目答案DTO
 *
 * @author <a href="https://www.jehol-ppx.com">热河fen青</a>
 * @date 2024/10/19 11:21
 */
@Data
public class QuestionAnswerDTO {

    /**
     * 题目
     */
    private String title;

    /**
     * 用户答案
     */
    private String userAnswer;

    /**
     * 正确答案
     */
    private String correctAnswer;

    /**
     * 正确得分
     */
    private Integer score;
}
