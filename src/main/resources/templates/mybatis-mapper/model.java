package [(${packageName})];

import java.io.Serializable;
import io.mybatis.provider.Entity;
import lombok.Data;

[# th:each="pkg : ${p.pkgList}"]
import [(${pkg})];
[/]


@Data
@Entity.Table(value="[(${p.tableName})]", remark = "[(${p.remark})]", autoResultMap = true)
public class [(${p.className})] implements Serializable {
[# th:each="field : ${p.fieldList}"]
    [# th:if="${field.primaryKey}"]
    @Entity.Column(value = "[(${field.fieldName})]", id = true, remark = "[(${field.remark})]", updatable = false)
    [/]
    [# th:unless="${field.primaryKey}"]
    @Entity.Column(value = "[(${field.fieldName})]", remark = "[(${field.remark})]")
    [/]
    private [(${field.fieldType})] [(${field.javaName})];
[/]
}
