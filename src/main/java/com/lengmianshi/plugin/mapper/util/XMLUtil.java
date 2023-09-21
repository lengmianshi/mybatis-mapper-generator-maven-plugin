package com.lengmianshi.plugin.mapper.util;

import com.lengmianshi.plugin.mapper.Config;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class XMLUtil {
    /**
     * 将 xml 转换为 map
     *
     * @param xmlStr xml 格式字符串
     * @return map 对象
     * @throws DocumentException
     */
    public static Map<String, String> xml2Map(String xmlStr) throws DocumentException {
        Map<String, String> map = new HashMap<String, String>();
        Document doc = DocumentHelper.parseText(xmlStr);

        if (doc == null)
            return map;
        Element root = doc.getRootElement();
        for (Iterator<Element> iterator = root.elementIterator(); iterator.hasNext(); ) {
            Element e = iterator.next();
            map.put(e.getName(), e.getText());
        }
        return map;
    }

    public static Config readXml(InputStream in) throws Exception {
        String xmlContent = FileCopyUtil.copyToString(in);

        Config config = new Config();
        Document doc = DocumentHelper.parseText(xmlContent.trim());
        Element root = doc.getRootElement();

        Element context = root.element("context");

        Element jdbcConnection = context.element("jdbcConnection");
        Config.JdbcConnection jc = new Config.JdbcConnection();
        //jc.setDialect(jdbcConnection.attributeValue("dialect"));
        jc.setUrl(jdbcConnection.attributeValue("url"));
        jc.setDriverClass(jdbcConnection.attributeValue("driverClass"));
        jc.setUser(jdbcConnection.attributeValue("user"));
        jc.setPassword(jdbcConnection.attributeValue("password"));

        //解析database
        String[] array = jc.getUrl().split("\\?");
        String[] array2 = array[0].split("/");
        jc.setDatabase(array2[array2.length - 1]);

        config.setJdbcConnection(jc);

        Element pojoGenerator = context.element("pojoGenerator");
        Config.Package pkg = new Config.Package();
        pkg.setPackageName(pojoGenerator.attributeValue("package"));
        pkg.setProjectPath(pojoGenerator.attributeValue("project"));
        config.setPojo(pkg);

        Element dtoGenerator = context.element("dtoGenerator");
        pkg = new Config.Package();
        pkg.setPackageName(dtoGenerator.attributeValue("package"));
        pkg.setProjectPath(dtoGenerator.attributeValue("project"));
        config.setDto(pkg);

        Element xmlGenerator = context.element("xmlGenerator");
        pkg = new Config.Package();
        pkg.setPackageName(xmlGenerator.attributeValue("package"));
        pkg.setProjectPath(xmlGenerator.attributeValue("project"));
        config.setXml(pkg);

        Element mapperGenerator = context.element("mapperGenerator");
        pkg = new Config.Package();
        pkg.setPackageName(mapperGenerator.attributeValue("package"));
        pkg.setProjectPath(mapperGenerator.attributeValue("project"));
        config.setMapper(pkg);

        Element serviceGenerator = context.element("serviceGenerator");
        pkg = new Config.Package();
        pkg.setPackageName(serviceGenerator.attributeValue("package"));
        pkg.setProjectPath(serviceGenerator.attributeValue("project"));
        config.setService(pkg);

        Element serviceImplGenerator = context.element("serviceImplGenerator");
        pkg = new Config.Package();
        pkg.setPackageName(serviceImplGenerator.attributeValue("package"));
        pkg.setProjectPath(serviceImplGenerator.attributeValue("project"));
        config.setServiceImpl(pkg);

        List list = context.elements("table");
        if (list != null) {
            List<Config.Table> tableList = (List<Config.Table>) list.stream()
                    .map(t -> {
                        Element e = (Element) t;
                        Config.Table tab = new Config.Table();
                        tab.setTableName(e.attributeValue("tableName"));
                        tab.setClassName(e.attributeValue("className"));

                        //是否生成service
                        String generateService = e.attributeValue("generateService");
                        if (StringUtil.isNotEmpty(generateService)) {
                            tab.setGenerateService(Boolean.valueOf(generateService));
                        }

                        //是否生成dto
                        String generateDTO = e.attributeValue("generateDTO");
                        if (StringUtil.isNotEmpty(generateDTO)) {
                            tab.setGenerateDTO(Boolean.valueOf(generateDTO));
                        }

//                        String generateController = e.attributeValue("generateController");
//                        if(StringUtil.isNotEmpty(generateController)){
//                            tab.setGenerateController(Boolean.valueOf(generateController));
//                        }

                        //模板
                        String template = e.attributeValue("template");
                        if (StringUtil.isEmpty(template)) {
                            template = "mybatis-mapper";
                        }
                        tab.setTemplate(template);
                        return tab;
                    }).collect(Collectors.toList());
            config.setTableList(tableList);
        }
        return config;
    }

}
