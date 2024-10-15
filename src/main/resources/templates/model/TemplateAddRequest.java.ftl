package ${packageName}.model.dto.${dataKey};

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建${dataName}请求
 *
 * @author <a href="https://www.jehol-ppx.com">热河fen青</a>
 * @date ${date}
 */
@Data
public class ${upperDataKey}AddRequest implements Serializable {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    private static final long serialVersionUID = 1L;
}