package com.jeholppx.bbg.model.dto.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 题目内容JSON
 *
 * @author <a href="https://www.jehol-ppx.com">热河fen青</a>
 * @date 2024/10/14 15:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionContentDTO implements Serializable {
    /**
     * 题目标题
     */
    private String title;

    /**
     * 题目选项列表
     */
    private List<Option> options;

    /**
     * 题目选项
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Option implements Serializable {
        private String result;
        private int score;
        private String value;
        private String key;
    }
}
