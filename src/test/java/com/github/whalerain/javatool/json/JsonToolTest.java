package com.github.whalerain.javatool.json;

import com.github.whalerain.javatool.test.FileTestTool;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author ZhangXi
 */
class JsonToolTest {


    @Test
    void testReadJsonString() {
        String path = FileTestTool.getTestResourcePath("/json/data.json");
        String json = JsonTool.readJsonString(new File(path), StandardCharsets.UTF_8);
        System.out.println(json);
    }


    @Test
    void testWriteJsonTo() throws IOException {
        String path = FileTestTool.getTestResourcePath("/json/data.json");
        String json = JsonTool.readJsonString(new File(path), StandardCharsets.UTF_8);
        String tempPath = path.replace("data.json", "temp.json");
        JsonTool.writeJsonTo(new File(tempPath), json, StandardCharsets.UTF_8);
    }








}
