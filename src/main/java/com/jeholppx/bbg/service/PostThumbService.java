package com.jeholppx.bbg.service;

import com.jeholppx.bbg.model.entity.PostThumb;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeholppx.bbg.model.entity.User;

/**
 * 帖子点赞服务
 *
 * @author <a href="https://www.jehol-ppx.com">热河fen青</a>
 * @date 2024/10/14 19:00
 */
public interface PostThumbService extends IService<PostThumb> {

    /**
     * 点赞
     *
     * @param postId
     * @param loginUser
     * @return
     */
    int doPostThumb(long postId, User loginUser);

    /**
     * 帖子点赞（内部服务）
     *
     * @param userId
     * @param postId
     * @return
     */
    int doPostThumbInner(long userId, long postId);
}
