package com.jeholppx.bbg.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 审核请求
 *
 * @author <a href="https://www.jehol-ppx.com">热河fen青</a>
 * @date 2024/10/15 16:33
 */
@Data
public class ReviewRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 状态：0-待审核, 1-通过, 2-拒绝
     */
    private Integer reviewStatus;

    /**
     * 审核信息
     */
    private String reviewMessage;


    private static final long serialVersionUID = 1L;
}
