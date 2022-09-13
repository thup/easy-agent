package com.easy.lsy.agent.core.utils.xml;

import io.restassured.path.xml.XmlPath;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Demo {

    public static void main(String[] args) throws Exception {
        String path = "E:\\cebaSource\\aitest2022\\junit-pass-tests-agent\\sample\\pom.xml";

        XmlOper xmlOper = new XmlOper(path);

        //xml字符串转为Document
        Document doc = xmlOper.readXML();
        //先备份
        xmlOper.saveXML(path+".1bak");

        Element project = xmlOper.getRoot();
        System.out.println("xmlOper.getRoot().getName() == " + xmlOper.getRoot().getName());
        if(Objects.isNull(project) || !"project".equals(project.getName())){
            System.out.println("非pom.xml文件");
            return;
        }

        Element properties = project.element("properties");
        System.out.println("properties== " + properties);
        if(Objects.isNull(properties)){
            properties = project.addElement("properties");
        }
        if(Objects.isNull(properties.element("java.trace.projectpath"))){
            properties.addElement("java.trace.projectpath");
        }

        //排除net.bytebuddy依赖
        Element dependencies = project.element("dependencies");
        System.out.println("dependencies== " + dependencies);
        //如果没有依赖，则不处理
        if(Objects.isNull(dependencies)){
            dependencies = project.addElement("dependencies");
        }else {
            List nodes = dependencies.elements("dependency");
            for (Iterator it = nodes.iterator(); it.hasNext();) {
                Element dependency = (Element) it.next();
                Element groupId = dependency.element("groupId");
                Element artifactId = dependency.element("artifactId");
                System.out.println("dependencies groupId&artifactId== " + groupId+" & " + artifactId);
                //如果依赖中含有appium和selenium的客户端，则添加排除依赖节点
                if((groupId.getText().equals("org.seleniumhq.selenium")&&artifactId.getText().equals("selenium-java"))
                        || (groupId.getText().equals("io.appium")&&artifactId.getText().equals("java-client"))){

                    Element exclusions = dependency.element("exclusions");
                    if(Objects.isNull(exclusions)){
                        exclusions = dependency.addElement("exclusions");
                    }

                    List exclusionNodes = exclusions.elements("exclusion");

                    //是否已经含有bytebuddy排除项
                    boolean bytebuddyFlag = false;
                    //如果已经包含，则直接终止排除项的循环
                    for (Iterator exIt = exclusionNodes.iterator(); exIt.hasNext();) {
                        Element exclusion = (Element) exIt.next();
                        Element exGroupId = exclusion.element("groupId");
                        Element exArtifactId = exclusion.element("artifactId");

                        if(Objects.nonNull(exGroupId)&&Objects.nonNull(exArtifactId)){

                            if((exGroupId.getText().equals("net.bytebuddy")&&exArtifactId.getText().equals("byte-buddy"))){
                                bytebuddyFlag = true;
                                break;
                            }
                        }
                    }

                    //不存在时，新增排除项
                    if(!bytebuddyFlag){
                        Element exclusion = exclusions.addElement("exclusion");
                        Element exGroupId = exclusion.addElement("groupId");
                        exGroupId.setText("net.bytebuddy");
                        Element exArtifactId = exclusion.addElement("artifactId");
                        exArtifactId.setText("byte-buddy");
                    }

                }
            }
        }


        Element build = project.element("build");
        System.out.println("build== " + build);
        if(Objects.isNull(build)){
            build = project.addElement("build");
        }else {
            Element plugins = build.element("plugins");
            List nodes = plugins.elements("plugin");
            for (Iterator it = nodes.iterator(); it.hasNext();) {
                Element plugin = (Element) it.next();
                Element groupId = plugin.element("groupId");
                Element artifactId = plugin.element("artifactId");
                //如果依赖中含有appium和selenium的客户端，则添加排除依赖节点
                System.out.println("plugin groupId&artifactId== " + groupId+" & " + artifactId);
                if((groupId.getText().equals("org.apache.maven.plugins")&&artifactId.getText().equals("maven-surefire-plugin"))){
                    Element configuration = plugin.element("configuration");
                    //如果configuration标签为空，则直接新增argLine的数据
                    System.out.println("configuration== " + configuration);
                    if(Objects.isNull(configuration)){
                        configuration = plugin.addElement("configuration");
                        Element argLine = configuration.addElement("argLine");
                        argLine.setText(" -javaagent:E:/cebaSource/aitest2022/java_trace/CallChain/target/CallChain-1-full.jar=${java.trace.projectpath}");
                    }else {
                        Element argLine = configuration.element("argLine");
                        System.out.println("argLine== " + argLine);
                        //如果argLine标签不存在，则新增argLine标签
                        if(Objects.isNull(argLine)){
                            argLine = configuration.addElement("argLine");
                            argLine.setText(" -javaagent:E:/cebaSource/aitest2022/java_trace/CallChain/target/CallChain-1-full.jar=${java.trace.projectpath}");
                        }else {
                            String oldArgLine = argLine.getText();
                            System.out.println("oldArgLineText== " + oldArgLine);
                            if(Objects.isNull(oldArgLine)){
                                oldArgLine = "";
                            }
                            //不包含CallChain-1-full.jar=${java.trace.projectpath}时才添加
                            if(!oldArgLine.contains("CallChain-1-full.jar=${java.trace.projectpath}")){
                                argLine.setText(oldArgLine + " -javaagent:E:/cebaSource/aitest2022/java_trace/CallChain/target/CallChain-1-full.jar=${java.trace.projectpath}");
                            }
                        }
                    }


                }
            }
        }


       /* //recordsXml代表xml文件数据
        XmlPath xmlPath = new XmlPath(doc.asXML());

        //设置根路径
        xmlPath.setRootPath(doc.getRootElement().getPath());

        String projectProperties = xmlPath.getString("properties");


        System.out.println("projectProperties== " + projectProperties);
*/

        //修改完成后再覆盖原文件
        xmlOper.saveXML();


        xmlOper.saveConsole();
    }
}
