package com.easy.lsy.agent.core.utils;

import com.easy.lsy.agent.core.config.Config;
import com.easy.lsy.agent.core.span.CallInfo;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class FileUtils {
    public static HashMap<String, Map<String, List<String>>> hashMap = new HashMap<>();
    private static String businessType;

    public static HashMap<String,String> configMap= new HashMap<>();

    public static void getFileList(String path, String type) {
        File file = null;
        String key = null;
        if (type.equals("Client")) {
            businessType = type;
            hashMap.put("Client", new HashMap<String, List<String>>());
            path = path + "\\Client";
        } else if (type.equals("Supplier")) {
            businessType = type;
            hashMap.put("Supplier", new HashMap<String, List<String>>());
            path = path + "\\Supplier";
        } else {
            key = type;
        }
        file = new File(path);
        File[] fileList = file.listFiles();

        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isFile()) {
                if (key != null) {
                    hashMap.get(businessType).get(key).add(fileList[i].getName());
                }

            }

            if (fileList[i].isDirectory()) {
                String directoryName = fileList[i].getName();
                String nextPath = path + "\\" + directoryName + "\\services";
                if (!new File(nextPath).isDirectory()) {
                    nextPath = path + "\\" + directoryName;
                }
                hashMap.get(type).put(directoryName, new ArrayList<String>());
                getFileList(nextPath, directoryName);

            }
        }
    }

    public static String readConfig(String key) throws Exception {
        if(configMap.containsKey(key)){
            return configMap.get(key);
        }
        String result="";
        Properties prop = new Properties();
        InputStream in = new BufferedInputStream(new FileInputStream(Config.Agent.JAR_PATH + "/config/AgentConfig.properties"));
        prop.load(in);     ///加载属性列表
        Iterator<String> it = prop.stringPropertyNames().iterator();
        while (it.hasNext()) {
            String tempKey = it.next();
            if (tempKey.equals(key)) {
                result=prop.get(key).toString();
            }
        }
        in.close();
        configMap.put(key, result);
        return result;
    }

    public static void saveYaml(String path, CallInfo callInfo) {
        final DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        final Yaml yaml = new Yaml(dumperOptions);

        ArrayList<CallInfo> yamlData = new ArrayList<>();
        yamlData.add(callInfo);
        try {
            File targetFile = new File(path);

            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }

            System.out.println("reslut == " + targetFile.getAbsolutePath() );

            final FileWriter fileWriter = new FileWriter(path, true);
            yaml.dump(yamlData, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
