package com.lengmianshi.plugin.mapper;

import com.lengmianshi.plugin.mapper.service.GenerateService;
import com.lengmianshi.plugin.mapper.util.XMLUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.FileInputStream;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class MyMojo extends AbstractMojo {
    @Parameter(property = "configFile", required = true, readonly = true)
    private String configFile;
    //    @Parameter(defaultValue = "${session}", readonly = true)
//    private MavenSession mavenSession;
    @Parameter(property = "outerProjectDir", defaultValue = "${session.executionRootDirectory}", readonly = true)
    private String outerProjectDir;



    public void execute() throws MojoExecutionException {
        try {
            Config config = XMLUtil.readXml(new FileInputStream(configFile));
            if (config.getTableList() == null || config.getTableList().size() == 0) {
                getLog().warn("尚未指定数据库表");
                return;
            }

            GenerateService generateService = new GenerateService(config, outerProjectDir);
            generateService.doGenerate();

        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }

    }
}
