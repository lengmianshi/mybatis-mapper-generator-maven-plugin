package [(${packageName})];

import io.mybatis.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import [(${servicePackageName})].[(${p.className})]Service;
import [(${mapperPackageName})].[(${p.className})]Mapper;
import [(${pojoPackageName})].[(${p.className})];


@Service
public class [(${p.className})]ServiceImpl extends AbstractService<[(${p.className})], [(${p.primaryKeyType})], [(${p.className})]Mapper> implements [(${p.className})]Service {
    @Autowired
    private [(${p.className})]Mapper mapper;


}
