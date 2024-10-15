package com.jeholppx.bbg.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeholppx.bbg.model.entity.Post;
import javax.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 帖子收藏数据库操作测试
 *
 * @author <a href="https://www.jehol-ppx.com">热河fen青</a>
 * @date 2024/10/14 19:00
 */
@SpringBootTest
class PostFavourMapperTest {

    @Resource
    private PostFavourMapper postFavourMapper;

    @Test
    void listUserFavourPostByPage() {
        IPage<Post> page = new Page<>(2, 1);
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", 1);
        queryWrapper.like("content", "a");
        IPage<Post> result = postFavourMapper.listFavourPostByPage(page, queryWrapper, 1);
        Assertions.assertNotNull(result);
    }
}