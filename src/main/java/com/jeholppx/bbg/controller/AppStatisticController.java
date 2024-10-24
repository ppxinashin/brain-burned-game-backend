package com.jeholppx.bbg.controller;

import com.jeholppx.bbg.common.BaseResponse;
import com.jeholppx.bbg.common.ErrorCode;
import com.jeholppx.bbg.common.ResultUtils;
import com.jeholppx.bbg.exception.ThrowUtils;
import com.jeholppx.bbg.mapper.UserAnswerMapper;
import com.jeholppx.bbg.model.dto.userAnswer.AppAnswerCountDTO;
import com.jeholppx.bbg.model.dto.userAnswer.AppAnswerResultCountDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/app/statistic")
@Slf4j
public class AppStatisticController {

    @Resource
    private UserAnswerMapper userAnswerMapper;

    /**
     * 热门应用排行统计
     *
     * @return
     */
    @GetMapping("/answer_count")
    public BaseResponse<List<AppAnswerCountDTO>> getAppAnswerCount() {
        return ResultUtils.success(userAnswerMapper.doAppAnswerCount());
    }

    /**
     * 应用回答分布统计
     *
     * @param appId
     * @return
     */
    @GetMapping("/answer_result_count")
    public BaseResponse<List<AppAnswerResultCountDTO>> getAppAnswerResultCount(Long appId) {
        ThrowUtils.throwIf(appId == null || appId <= 0, ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(userAnswerMapper.doAppAnswerResultCount(appId));
    }
}
