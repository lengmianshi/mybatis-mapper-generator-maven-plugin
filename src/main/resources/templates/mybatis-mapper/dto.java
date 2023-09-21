package [(${packageName})];

import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

[# th:each="pkg : ${p.pkgList}"]
import [(${pkg})];
[/]


@Data
public class [(${p.className})]DTO implements Serializable {
[# th:each="field : ${p.fieldList}"]
    @ApiModelProperty("[(${field.remark})]")
    private [(${field.fieldType})] [(${field.javaName})];

[/]
}
