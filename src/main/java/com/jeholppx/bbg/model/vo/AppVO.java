package com.jeholppx.bbg.model.vo;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.jeholppx.bbg.model.entity.App;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 应用视图
 *
 * @author <a href="https://www.jehol-ppx.com">热河fen青</a>
 * @date 
 */
@Data
public class AppVO implements Serializable {

    /**
     * id，此处为避免爬虫以及序列重复，使用雪花算法生成，所有ASSIGN_ID同理
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 应用名
     */
    private String appName;

    /**
     * 应用描述
     */
    private String appDesc;

    /**
     * 应用图标
     */
    private String appIcon;

    /**
     * 应用类型（0-得分类，1-测评类）
     */
    private Integer appType;

    /**
     * 评分策略（0-自定义，1-AI）
     */
    private Integer scoringStrategy;

    /**
     * 审核状态：0-待审核, 1-通过, 2-拒绝
     */
    private Integer reviewStatus;

    /**
     * 审核信息
     */
    private String reviewMessage;

    /**
     * 审核人 id
     */
    private Long reviewerId;

    /**
     * 审核时间
     */
    private Date reviewTime;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建用户信息
     */
    private UserVO user;


    /**
     * 封装类转对象
     *
     * @param appVO
     * @return
     */
    public static App voToObj(AppVO appVO) {
        if (appVO == null) {
            return null;
        }
        App app = new App();
        BeanUtils.copyProperties(appVO, app);
        return app;
    }

    /**
     * 对象转封装类
     *
     * @param app
     * @return
     */
    public static AppVO objToVo(App app) {
        if (app == null) {
            return null;
        }
        AppVO appVO = new AppVO();
        return appVO;
    }
}
