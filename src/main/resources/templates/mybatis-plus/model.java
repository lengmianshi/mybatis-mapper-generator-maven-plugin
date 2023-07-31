package [(${packageName})];

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;

[# th:each="pkg : ${p.pkgList}"]
import [(${pkg})];
[/]


/**
 * [(${p.remark})]
 */
@Data
@TableName("[(${p.tableName})]")
public class [(${p.className})] implements Serializable {
[# th:each="field : ${p.fieldList}"]
    [# th:if="${field.primaryKey}"]
    /**
     * [(${field.remark})]
     */
    @TableId(value = "[(${field.fieldName})]")
    [/]
    [# th:unless="${field.primaryKey}"]
    /**
     * [(${field.remark})]
     */
    @TableField("[(${field.fieldName})]")
    [/]
    private [(${field.fieldType})] [(${field.javaName})];
[/]
}
