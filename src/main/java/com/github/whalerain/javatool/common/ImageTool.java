package com.github.whalerain.javatool.common;

import cn.hutool.core.img.ImgUtil;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

/**
 * 图片工具类
 *
 * @author ZhangXi
 */
@Slf4j
public class ImageTool extends ImgUtil {

    /**
     * 将图片转为Base64字符串
     *
     * @param image {@link BufferedImage}
     * @param type 图片类型字符串，例如：jpg,png...
     * @return 图片Base64字符串
     */
    public static String toBase64(BufferedImage image, String type) {
        String base64 = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ImageIO.write(image, type, bos);
            byte[] bytes = bos.toByteArray();
            base64 = Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            log.error("图片转Base64失败", e);
        }
        return base64;
    }

    /**
     * 从Base64字符串中读取图片
     *
     * @param base64 图片Base64字符串
     * @return {@link BufferedImage}
     */
    public static BufferedImage fromBase64(String base64) {
        if (null == base64) {
            return null;
        }
        BufferedImage image = null;
        byte[] bytes = Base64.getDecoder().decode(base64);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
            image = ImageIO.read(bis);
        } catch (IOException e) {
            log.error("Base64转图片出错，base64={}", base64, e);
        }
        return image;
    }

    /**
     * 将Base64图片写入文件
     *
     * @param base64 图片Base64字符串
     * @param file {@link File}
     */
    public static void writeBase64(String base64, File file) throws IOException {
        byte[] bytes = Base64.getDecoder().decode(base64);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes);
            fos.flush();
        } catch (IOException e) {
            log.error("Base64图片写入文件：{} 出错，base64={}", file.getAbsolutePath(), base64, e);
            throw e;
        }
    }

}
