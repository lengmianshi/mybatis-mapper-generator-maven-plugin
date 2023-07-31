package com.lengmianshi.plugin.mapper.service;

import com.lengmianshi.plugin.mapper.Config;
import com.lengmianshi.plugin.mapper.model.Field;
import com.lengmianshi.plugin.mapper.model.JavaType;
import com.lengmianshi.plugin.mapper.model.Pojo;
import com.lengmianshi.plugin.mapper.util.*;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
public class GenerateService {
    private DBUtil dbUtil;
    private Config config;
    //插件所在的外部项目路径
    private String outerProjectDir;


    public GenerateService(Config config, String outerProjectDir) {
        this.dbUtil = new DBUtil(config.getJdbcConnection().getDriverClass(),
                config.getJdbcConnection().getUrl(), config.getJdbcConnection().getUser(),
                config.getJdbcConnection().getPassword(), config.getJdbcConnection().getDatabase());
        this.config = config;
        this.outerProjectDir = outerProjectDir;
    }

    public void doGenerate() throws Exception {
        if (config.getTableList() == null || config.getTableList().size() == 0) {
            log.warn("尚未指定数据库表");
            return;
        }

        TemplateEngine templateEngine = templateEngine(TemplateMode.TEXT);
        for (Config.Table table : config.getTableList()) {
            Pojo pojo = this.selectTableInfoList(table);
            //生成pojo
            Map<String, Object> params = new HashMap() {{
                put("p", pojo);
                put("packageName", config.getPojo().getPackageName());
            }};
            generateCodeFile(params, config.getPojo().getProjectPath(), config.getPojo().getPackageName(), pojo.getClassName() + ".java", table.getTemplate(), "model.java", templateEngine, true);

            //生成mapper
            params = new HashMap() {{
                put("p", pojo);
                put("packageName", config.getMapper().getPackageName());
                put("pojoPackageName", config.getPojo().getPackageName());
            }};
            generateCodeFile(params, config.getMapper().getProjectPath(), config.getMapper().getPackageName(), pojo.getClassName() + "Mapper.java", table.getTemplate(), "mapper.java", templateEngine, false);

            if (table.isGenerateService()) {
                //生成Service
                params = new HashMap() {{
                    put("p", pojo);
                    put("packageName", config.getService().getPackageName());
                    put("pojoPackageName", config.getPojo().getPackageName());
                }};
                generateCodeFile(params, config.getService().getProjectPath(), config.getService().getPackageName(), pojo.getClassName() + "Service.java", table.getTemplate(), "service.java", templateEngine, false);

                //生成ServiceImpl
                params = new HashMap() {{
                    put("p", pojo);
                    put("packageName", config.getServiceImpl().getPackageName());
                    put("pojoPackageName", config.getPojo().getPackageName());
                    put("servicePackageName", config.getService().getPackageName());
                    put("mapperPackageName", config.getMapper().getPackageName());
                }};
                generateCodeFile(params, config.getServiceImpl().getProjectPath(), config.getServiceImpl().getPackageName(), pojo.getClassName() + "ServiceImpl.java", table.getTemplate(), "serviceImpl.java", templateEngine, false);
            }
            //生成xml
            params = new HashMap() {{
                put("p", pojo);
                put("mapperPackageName", config.getMapper().getPackageName());
                put("pojoPackageName", config.getPojo().getPackageName());
            }};
            templateEngine = templateEngine(TemplateMode.XML);
            generateCodeFile(params, config.getXml().getProjectPath(), config.getXml().getPackageName(), pojo.getClassName() + "Mapper.xml", table.getTemplate(), "mapper.xml", templateEngine, true);

        }

    }


    /**
     * 创建模板引擎
     */
    private TemplateEngine templateEngine(TemplateMode templateMode) {
        // 定义、设置模板解析器
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        // 设置模板类型 # https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#what-is-thymeleaf
        // HTML、XML、TEXT、JAVASCRIPT、CSS、RAW
        templateResolver.setTemplateMode(templateMode);
        templateResolver.setPrefix("/templates/");
        //templateResolver.setSuffix(".html");
        // 定义模板引擎
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }


    /**
     * 生成代码文件
     */
    private void generateCodeFile(Map<String, Object> params, String projectPath, String packageName, String fileName, String templateDir, String template, TemplateEngine templateEngine, boolean isOverride) throws Exception {
        boolean isXml = Objects.equals("mapper.xml", template);
        String outFilePath = PathUtil.getAbsolutePath(outerProjectDir, projectPath + File.separator + PathUtil.convertPath(packageName) + File.separator + fileName);
        File file = new File(outFilePath);
        if (file.exists() && !isOverride && !isXml) {
            log.info("文件已存在，不再创建：" + outFilePath);
            return;
        }

        file.getParentFile().mkdirs();

        // 定义数据模型
        Context context = new Context();
        params.forEach((key, value) -> {
            context.setVariable(key, value);
        });

        boolean fileExists = file.exists();
        //如果xml文件已存在，则用新生成的ResultMap去替换旧文件中的ResultMap，这样可以保留之前已经定义好的方法
        if (isXml && fileExists) {
            //渲染模板，并获取resultMap元素
            String xml = templateEngine.process(templateDir + "/" + template, context);
            StringBuilder sb = new StringBuilder();
            InputStream in = new ByteArrayInputStream(xml.getBytes());
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String tmp = null;
            while ((tmp = br.readLine()) != null) {
                if (StringUtil.isEmpty(tmp)) {
                    continue;
                }

                if (StringUtil.contains(tmp, "<result", "<id")) {
                    sb.append(tmp + "\n");
                }
                if (StringUtil.contains(tmp, "</resultMap>")) {
                    sb.append(tmp);
                }
            }

            br.close();
            in.close();

            FileUtil.replaceContent(file, "baseResultMap", "</resultMap>", sb.toString());

        } else {
            Writer writer = new FileWriter(file);
            templateEngine.process(templateDir + "/" + template, context, writer);
        }

        //删除多余的空行
        if (Objects.equals("model.java", template)) {
            FileUtil.removeEmptyRows(file, "{", "}", "private");
        } else if (Objects.equals("mapper.xml", template)) {
            FileUtil.removeEmptyRows(file, "baseResultMap", "</resultMap>", null);
        }

        log.info("文件{}：{}", fileExists ? "已覆盖" : "已创建", outFilePath);
    }

    /**
     * 获取数据库表信息
     *
     * @param table
     * @return
     */
    private Pojo selectTableInfoList(Config.Table table) throws Exception {
        Connection con = this.dbUtil.getConnection();
        try {
            //查询表注释
            String sql = String.format("SELECT " +
                    "table_name," +
                    "table_comment " +
                    "FROM " +
                    "information_schema.TABLES " +
                    "WHERE " +
                    "table_schema = '%s' " +
                    " AND table_name = '%s';", this.dbUtil.getDatabase(), table.getTableName());
            Statement st = null;
            try {
                Pojo pojo = new Pojo();
                pojo.setTableName(table.getTableName());
                if (StringUtil.isNotEmpty(table.getClassName())) {
                    pojo.setClassName(table.getClassName());
                } else {
                    pojo.setClassName(StringUtil.toCamelCase(table.getTableName()));
                }

                Set<String> importPackageSet = new HashSet<>();

                List<Field> fieldList = new ArrayList<>();

                st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    pojo.setRemark(rs.getString("TABLE_COMMENT"));
                    break;
                }

                //查询表的所有列
                sql = String.format("SELECT " +
                        "COLUMN_NAME," +
                        "DATA_TYPE," +
                        "COLUMN_COMMENT," +
                        "COLUMN_KEY " +
                        "FROM " +
                        "INFORMATION_SCHEMA.COLUMNS " +
                        "WHERE table_schema = '%s' AND table_name = '%s'", this.dbUtil.getDatabase(), table.getTableName());

                st = con.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    Field field = new Field();
                    String originalFieldName = rs.getString("COLUMN_NAME");
                    field.setFieldName(KeywordsUtil.convertSafeFieldName(originalFieldName));
                    String javaName = StringUtil.toCamelCase(originalFieldName);
                    if (javaName.length() > 1) {
                        javaName = javaName.substring(0, 1).toLowerCase(Locale.ROOT) + javaName.substring(1);
                    }
                    field.setJavaName(javaName);

                    String dbType = rs.getString("DATA_TYPE").toLowerCase();
                    field.setJdbcType(TypeUtil.getJDBCType(dbType));
                    JavaType javaType = TypeUtil.getJavaType(dbType);
                    field.setFieldType(javaType.getTypeName());

                    if (javaType.isImport()) {
                        importPackageSet.add(javaType.getPkg());
                    }

                    field.setRemark(rs.getString("COLUMN_COMMENT"));
                    field.setPrimaryKey(Objects.equals("PRI", rs.getString("COLUMN_KEY")));
                    if (field.isPrimaryKey()) {
                        pojo.setPrimaryKeyType(field.getFieldType());
                    }
                    fieldList.add(field);
                }
                if (fieldList.isEmpty()) {
                    throw new RuntimeException("表【" + table.getTableName() + "】不存在");
                }

                if (StringUtil.isEmpty(pojo.getPrimaryKeyType())) {
                    throw new RuntimeException("表【" + table.getTableName() + "】没有主键");
                }

                //将主键放在前面
                List<Field> primaryKeyFieldList = fieldList.stream().filter(field -> field.isPrimaryKey()).collect(Collectors.toList());
                List<Field> unPrimaryKeyFieldList = fieldList.stream().filter(field -> !field.isPrimaryKey()).collect(Collectors.toList());

                List<Field> fields = new ArrayList<>();
                fields.addAll(primaryKeyFieldList);
                fields.addAll(unPrimaryKeyFieldList);

                pojo.setFieldList(fields);

                pojo.setPkgList(new ArrayList<>(importPackageSet));

                return pojo;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } finally {
            this.dbUtil.closeConnection(null, null, con);
        }


    }
}
