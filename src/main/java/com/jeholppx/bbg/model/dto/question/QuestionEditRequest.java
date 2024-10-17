package com.jeholppx.bbg.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 编辑题目请求
 *
 * @author <a href="https://www.jehol-ppx.com">热河fen青</a>
 * @date 2024/10/14 15:33
 */
@Data
public class QuestionEditRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 题目内容（json格式）
     */
    private List<QuestionContentDTO> questionContent;



    private static final long serialVersionUID = 1L;
}