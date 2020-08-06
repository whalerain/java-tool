package com.github.whalerain.javatool.test;

/**
 * @author ZhangXi
 */
public class FileTestTool {


    public static String getTestResourcePath(String path) {
        String absPath = FileTestTool.class.getResource(path).getPath();
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            // 剔除斜杠/首字母
            absPath = absPath.substring(1);
        }
        return absPath;
    }




}
