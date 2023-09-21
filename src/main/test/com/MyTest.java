package com;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.lengmianshi.plugin.mapper.Config;
import com.lengmianshi.plugin.mapper.util.PathUtil;
import com.lengmianshi.plugin.mapper.util.XMLUtil;
import org.junit.Test;

import java.io.FileInputStream;
import java.net.URL;
import java.util.List;

public class MyTest {
    @Test
    public void test() throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("generatorConfig.xml");
        Config config = XMLUtil.readXml(url.openStream());
        System.out.println(config);
    }

    @Test
    public void test2() {
        System.out.println(PathUtil.getAbsolutePath("/a/b/c", "../d/e"));
    }

    @Test
    public void test3() throws Exception {
        String path = "C:\\Users\\iGreenbank-C\\IdeaProjects\\igreenbank-platform\\tzc-blockchain-saas-web-node\\src\\main\\java\\cn\\igreenbank\\mrv\\tzc\\webnode\\entity\\TaskDocumentaryEvidence.java";
        FileInputStream in = new FileInputStream(path);
        CompilationUnit cu = new JavaParser().parse(in).getResult().get();

//        ClassOrInterfaceDeclaration classOrInterfaceDeclaration = cu.findFirst(ClassOrInterfaceDeclaration.class).get();
        TypeDeclaration<?> typeDeclaration = cu.getTypes().get(0);
        System.out.println(typeDeclaration);

        List<ImportDeclaration> importList = cu.getImports();

        cu.getChildNodes().forEach(node ->
        {
            System.out.println(node.getClass().getSimpleName());
            if (node.toString().startsWith("import")) {
            } else if (node.getClass().getSimpleName().equals("ClassOrInterfaceDeclaration")) {
                System.out.println(node);
                node.getChildNodes().forEach(n -> {
                    System.out.println(n.getClass().getSimpleName());
                    if (n.getClass().getSimpleName().equals("MethodDeclaration")) {
                        System.out.println(n.getClass().getSimpleName());
                        System.out.println(n);
                    }
                });

            }
        });

        System.out.println(importList);
    }

}
