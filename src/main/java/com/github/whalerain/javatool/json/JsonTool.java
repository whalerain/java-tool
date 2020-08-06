package com.github.whalerain.javatool.json;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * JSON工具类
 *
 * @author ZhangXi
 */
@Slf4j
public class JsonTool {

    /**
     * 从文件中读取JSON数据
     * @param file {@link File} JSON文件
     * @param charset {@link Charset}
     * @return JSON字符串
     */
    public static String readJsonString(File file, Charset charset) {
        JSON json = JSONUtil.readJSON(file, charset);
        return json.toString();
    }




    public static void writeJsonTo(File file, String json, Charset charset) throws IOException {
        String prettyJson = JSONUtil.parseObj(json).toStringPretty();
        Path path = file.toPath();
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        if (Files.isDirectory(path)) {
            // todo 抛出异常？
        }
        try (BufferedWriter writer = Files.newBufferedWriter(path, charset)) {
            writer.write(prettyJson);
        } catch (IOException e) {
            log.warn("JSON字符串写入文件：{} 失败", file.getAbsolutePath());
        }
    }





}
