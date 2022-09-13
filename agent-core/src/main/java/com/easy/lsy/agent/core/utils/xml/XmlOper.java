package com.easy.lsy.agent.core.utils.xml;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;

public class XmlOper extends XmlBuilder {

    public XmlOper() {
        super();
    }

    public XmlOper(String path) {
        super(path);
    }

    // 根据路径查询指定XML元素
    private List<?> queryXMLByPath(String xpath) {
        List<?> list = this.getDoc().selectNodes(xpath);
        return list;
    }

    // 根据名称查询Root下子元素，返回第一个匹配子元素
    private Element queryXMLByName(String name) {
        Element elem = null;
        List<?> list = queryXMLByPath("/" + this.getRootName() + "/*");
        int len = list.size();
        for (int i = 0; i < len; i++) {
            Element sub = (Element) list.get(i);
            if (name.equals(sub.getName())) {
                elem = sub;
                break;
            }
        }
        return elem;
    }

    // 通过属性查询指定名称元素
    public Element queryXML(String name, String attName, String attVal) {
        List<?> list = queryXMLByPath("/" + this.getRootName() + "/" + name);
        int len = list.size();
        Element elem = null;
        for (int i = 0; i < len; i++) {
            Element sub = (Element) list.get(i);
            Attribute att = hasAttribute(sub, attName);
            if (att != null && attVal.equals(att.getValue())) {
                elem = sub;
                break;
            }
        }
        return elem;
    }

    // 查询XML中指定名称的第一个匹配节点
    public Element queryXML(String name) {
        if (name == this.getRootName()) {
            return this.getRoot();
        }
        return queryXMLByName(name);
    }

    // 在指定元素下添加子元素
    public Element addElement(Element elem, String name) {
        if (elem != null) {
            elem = elem.addElement(name);
        }
        return elem;
    }

    // 判断XML父元素中是否包含指定名称的子元素
    private Element hasElement(Element parent, String name) {
        Element has = null;
        List<?> list = parent.elements();
        for (int i = 0; i < list.size(); i++) {
            Element sub = (Element) list.get(i);
            if (name.equals(sub.getName())) {
                has = sub;
                break;
            }
        }
        return has;
    }

    // 判断XML元素中是否包含指定名称的属性
    private Attribute hasAttribute(Element elem, String attName) {
        Attribute has = null;
        List<?> list = elem.attributes();
        for (int i = 0; i < list.size(); i++) {
            Attribute att = (Attribute) list.get(i);
            if (attName.equals(att.getName())) {
                has = att;
                break;
            }
        }
        return has;
    }

    // XML指定元素中删除子元素
    public boolean removeElement(Element parent, String name) {
        boolean succ = false;
        if (parent != null && !parent.isTextOnly()) {
            Element sub = hasElement(parent, name);
            if (sub != null) {
                succ = parent.remove(sub);
            }
        }
        return succ;
    }

    // XML指定元素下添加属性
    public Element addAttribute(Element elem, String attName, String attVal) {
        if (elem != null) {
            elem.addAttribute(attName, attVal);
        }
        return elem;
    }

    // XML指定元素中删除指定属性
    public boolean removeAttribute(Element elem, String attName) {
        boolean succ = false;
        if (elem != null) {
            Attribute has = hasAttribute(elem, attName);
            if (has != null) {
                succ = elem.remove(has);
            }
        }
        return succ;
    }

    // XML指定元素下添加值
    public Element addText(Element elem, String text) throws Exception {
        if (elem != null) {
            if (!elem.isTextOnly()) {
                throw new Exception("包含其他子元素，禁止添加文本!");
            }
            elem.addText(text);
        }
        return elem;
    }

    // XML指定元素下删除值
    public boolean removeText(Element elem) {
        boolean succ = false;
        if (elem != null && elem.isTextOnly()) {
            elem.setText("");
            succ = true;
        }
        return succ;
    }

}
