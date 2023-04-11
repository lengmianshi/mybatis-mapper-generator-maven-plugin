package com;

import com.lengmianshi.plugin.mapper.Config;
import com.lengmianshi.plugin.mapper.util.PathUtil;
import com.lengmianshi.plugin.mapper.util.XMLUtil;
import org.junit.Test;

import java.net.URL;

public class MyTest {
    @Test
    public void test()throws Exception{
        URL url = Thread.currentThread().getContextClassLoader().getResource("generatorConfig.xml");
        Config config = XMLUtil.readXml(url.openStream());
        System.out.println(config);
    }

    @Test
    public void test2(){
        System.out.println(PathUtil.getAbsolutePath("/a/b/c", "../d/e"));
    }
}
