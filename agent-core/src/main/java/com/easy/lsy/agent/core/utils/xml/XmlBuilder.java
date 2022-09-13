package com.easy.lsy.agent.core.utils.xml;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlBuilder {

    private String path; // XML保存和读取文件路径
    private Document doc; // XML文档对象
    private Element root; // XML文档根元素

    public XmlBuilder(String path) {
        this.path = path;
        doc = DocumentHelper.createDocument();
        root = null;
    }

    // 构造函数重载
    public XmlBuilder() {
        this.path = "";
        doc = DocumentHelper.createDocument();
        root = null;
    }

    // 返回XML文档对象
    public Document getDoc() {
        return doc;
    }

    // 创建XML文件的根元素
    public Element createRoot(String name) {
        root = doc.addElement(name);
        return root;
    }

    // 返回XML文件根元素的名称
    public String getRootName() {
        if (root != null) {
            return root.getName();
        }
        return null;
    }

    // 返回XML根元素
    public Element getRoot() {
        return root;
    }

    // 设置XML文件保存或者访问的文件位置
    public void setPath(String pathname) {
        this.path = pathname;
    }

    // 读取XML文件的private方法
    private Document readXMLCore(String pathname) throws Exception {
        if (pathname != "") {
            SAXReader reader = new SAXReader();
            try {
                this.setPath(pathname);
                doc = reader.read(new File(pathname));
                root = doc.getRootElement(); // 读取XML文件，同时初始化root
            } catch (DocumentException e) {
                throw new Exception("读取" + pathname + "XML文件出错！");
            }
            return doc;
        } else {
            throw new Exception("读取XML路径为空！");
        }
    }

    // 读取指定路径XML文件，返回Document对象
    public Document readXML(String xmlpath) throws Exception {
        this.path = xmlpath;
        return readXMLCore(path);
    }

    // 读取已初始化路径的XML文件
    public Document readXML() throws Exception {
        return readXMLCore(path);
    }

    // 保存XML文件的private方法
    private void saveXMLCore(String savepath) throws Exception {
        if (savepath == "" || savepath == null) {
            throw new Exception("保存XML文件路径为空！");
        }

        if (doc != null) {
            try {
                OutputFormat format = OutputFormat.createPrettyPrint();
                XMLWriter wrt = new XMLWriter(new FileOutputStream(new File(
                        savepath)), format);
                wrt.write(doc);
                wrt.close();
            } catch (IOException e) {
                throw new Exception("保存" + savepath + "XML文件出错！");
            }
        } else {
            throw new Exception("Document 对象为空");
        }
    }

    // 使用自定义路径，保存XML文件
    public void saveXML(String savepath) throws Exception {
        saveXMLCore(savepath);
    }

    // 使用已初始化路径保存XML文件
    public void saveXML() throws Exception {
        saveXMLCore(path);
    }

    // XML文件输出到屏幕
    public void saveConsole() throws Exception {
        if (doc != null) {
            try {
                OutputFormat format = OutputFormat.createPrettyPrint();
                XMLWriter wrt = new XMLWriter(System.out, format);
                wrt.write(doc);
                wrt.close();
            } catch (IOException e) {
                throw new Exception("XML输出至屏幕出错！");
            }
        } else {
            throw new Exception("Document 对象为空！");
        }
    }
}
