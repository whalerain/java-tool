package com.github.whalerain.javatool;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件工具类
 *
 * @author ZhangXi
 */
@Slf4j
public class FileTool extends FileUtil {

    public static final String PATH_SEPARATOR_RIGHT = "\\";
    public static final String PATH_SEPARATOR_LEFT = "/";

    private static final String URL_SEPARATOR = "/";

    private static final String FILE_TYPE_JAR = "jar";

    private static final String PROP_OS_NAME = "os.name";
    private static final String OS_NAME_WINDOWS = "windows";

    /**
     * 获取运行时Jar包所在目录路径
     * @return 目录绝对路径
     */
    public static String getRunningJarDirPath() {
        URL location = getThisClass().getProtectionDomain().getCodeSource().getLocation();
        String path;
        try {
            path = URLDecoder.decode(location.getPath(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.warn("解码路径：{} 失败", location.getPath());
            path = location.getPath();
        }
        if (path.endsWith(FILE_TYPE_JAR)) {
            path = path.substring(0, path.lastIndexOf(URL_SEPARATOR));
        }
        if (System.getProperty(PROP_OS_NAME).toLowerCase().contains(OS_NAME_WINDOWS)) {
            // 剔除windows下路径前缀：/
            if (path.startsWith(URL_SEPARATOR)) {
                path = path.substring(1);
            }
        }
        return path.replace(URL_SEPARATOR, File.separator);
    }

    /**
     * 加载resources下资源文件
     * @return {@link InputStream}
     */
    public static InputStream loadProjectResource(String resourceName) {
        return getThisClass().getClassLoader().getResourceAsStream(resourceName);
    }


    private static Class getThisClass() {
        try {
            return Class.forName(Thread.currentThread().getStackTrace()[1].getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return FileTool.class;
        }
    }

    /**
     * 构建文件路径
     * @param paths 文件路径字符串组
     * @return 拼接后的文件路径
     */
    public static String appendPath(String... paths) {
        // 首位可能是绝对路径起点，所以不做操作
        StringBuffer sb = new StringBuffer(paths[0]);
        for (int i=1; i<paths.length; i++) {
            String path = paths[i];
            if (path.startsWith(PATH_SEPARATOR_LEFT) || path.startsWith(PATH_SEPARATOR_RIGHT)) {
                path = path.substring(1);
            }
            if (path.endsWith(PATH_SEPARATOR_LEFT) || path.endsWith(PATH_SEPARATOR_RIGHT)) {
                path = path.substring(0, path.length()-1);
            }
            sb.append(File.separator).append(path);
        }
        return sb.toString();
    }

    /**
     * 构建并检查文件路径
     * @param paths 文件路径字符串组
     * @return 拼接后的文件路径
     */
    public static String appendPathAndEnsureDir(String... paths) throws IOException {
        String filePath = appendPath(paths);
        Path thePath = Paths.get(filePath);
        if (Files.isDirectory(thePath)) {
            if (! Files.exists(thePath)) {
                Files.createDirectories(thePath);
            }
        } else {
            Path subPath = Paths.get(filePath.substring(0, filePath.lastIndexOf(File.separator)));
            if (!Files.exists(subPath)) {
                Files.createDirectories(subPath);
            }
        }
        return filePath;
    }



    public static String getDevProjectRootPath() {
        //todo 获取项目根路径
        return null;
    }


    public static String getMavenTargetPath() {
        //todo 获取maven的target路径
        return null;
    }




}
