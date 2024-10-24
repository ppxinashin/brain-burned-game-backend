package com.jeholppx.bbg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeholppx.bbg.model.dto.userAnswer.AppAnswerCountDTO;
import com.jeholppx.bbg.model.dto.userAnswer.AppAnswerResultCountDTO;
import com.jeholppx.bbg.model.entity.UserAnswer;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author billz
* @description 针对表【user_answer(用户答题记录)】的数据库操作Mapper
* @createDate 2024-10-14 10:58:22
* @Entity com.jeholppx.bbg.model.entity.UserAnswer
*/
public interface UserAnswerMapper extends BaseMapper<UserAnswer> {

    @Select("select appId, count(userId) as answerCount from user_answer " +
            "group by appId order by answerCount desc")
    List<AppAnswerCountDTO> doAppAnswerCount();

    @Select("select resultName, count(resultName) as resultCount from user_answer " +
            "where appId = #{appId} group by resultName order by resultCount desc")
    List<AppAnswerResultCountDTO> doAppAnswerResultCount(Long appId);
}





