package com.zyf.validation.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import net.sf.json.JSONObject;


public class Xml2JsonUtil {
	/** 
     * 转换一个xml格式的字符串到json格式 
     *  
     * @param xml 
     *            xml格式的字符串 
     * @return 成功返回json 格式的字符串;失败反回null 
     */  
    @SuppressWarnings("unchecked")  
    public static  String xml2JSON(String xml) {  
        JSONObject obj = new JSONObject();  
        try {  
            InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));  
            SAXBuilder sb = new SAXBuilder();  
            Document doc = sb.build(is);  
            Element root = doc.getRootElement();  
            obj.put(root.getName(), iterateElement(root));  
            return obj.toString();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
  
    /** 
     * 转换一个xml格式的字符串到json格式 
     *  
     * @param file 
     *            java.io.File实例是一个有效的xml文件 
     * @return 成功反回json 格式的字符串;失败反回null 
     */  
    @SuppressWarnings("unchecked")  
    public static String xml2JSON(File file) {  
        JSONObject obj = new JSONObject();  
        try {  
            SAXBuilder sb = new SAXBuilder();  
            Document doc = sb.build(file);  
            Element root = doc.getRootElement();  
            obj.put(root.getName(), iterateElement(root));  
            return obj.toString();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
  
    /** 
     * 一个迭代方法 
     *  
     * @param element 
     *            : org.jdom.Element 
     * @return java.util.Map 实例 
     */  
    @SuppressWarnings("unchecked")  
    private static Map  iterateElement(Element element) {  
        List jiedian = element.getChildren();  
        Element et = null;  
        Map obj = new HashMap();  
        List list = null;  
        for (int i = 0; i < jiedian.size(); i++) {  
            list = new LinkedList();  
            et = (Element) jiedian.get(i);  
            if (et.getTextTrim().equals("")) {  
                if (et.getChildren().size() == 0)  
                    continue;  
                if (obj.containsKey(et.getName())) {  
                    list = (List) obj.get(et.getName());  
                }  
                list.add(iterateElement(et));  
                obj.put(et.getName(), list);  
            } else {  
                if (obj.containsKey(et.getName())) {  
                    list = (List) obj.get(et.getName());  
                }  
                list.add(et.getTextTrim());  
                obj.put(et.getName(), list);  
            }  
        }  
        return obj;  
    }  
  
    // 测试  
    public static void main(String[] args) {  
        System.out.println(  Xml2JsonUtil.xml2JSON("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n" + 
        		"<product>\r\n" + 
        		"	<id>1</id>\r\n" + 
        		"	<name>A green door</name>\r\n" + 
        		"	<price>12.5</price>\r\n" + 
        		"	<checked>false</checked>\r\n" + 
        		"	<tags>home</tags>\r\n" + 
        		"	<tags>green</tags>\r\n" + 
        		"</product>\r\n" + 
        		"	\r\n" + 
        		""));  
    }  
}	
