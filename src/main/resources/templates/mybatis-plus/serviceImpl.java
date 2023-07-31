package [(${packageName})];

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import [(${servicePackageName})].[(${p.className})]Service;
import [(${mapperPackageName})].[(${p.className})]Mapper;


@Service
public class [(${p.className})]ServiceImpl implements [(${p.className})]Service {
    @Autowired
    private [(${p.className})]Mapper mapper;


}

