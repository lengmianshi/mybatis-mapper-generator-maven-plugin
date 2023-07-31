package [(${packageName})];

import io.mybatis.mapper.Mapper;
import [(${pojoPackageName})].[(${p.className})];


@org.apache.ibatis.annotations.Mapper
public interface [(${p.className})]Mapper extends Mapper<[(${p.className})], [(${p.primaryKeyType})]> {

}
