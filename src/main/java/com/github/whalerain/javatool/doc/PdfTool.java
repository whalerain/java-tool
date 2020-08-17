package com.github.whalerain.javatool.doc;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.IOException;
import java.io.InputStream;

/**
 * PDF工具类
 *
 * @author ZhangXi
 */
public class PdfTool {

    /**
     * 加载自定义字体
     * 支持的字体文件包括：TTF
     *
     * @param document {@link PDDocument}
     * @param input {@link InputStream} 字体文件输入流
     * @param embedSubset 字体是否支持完全嵌入 full-embedded，使用前确保自定义的字体许可允许完全嵌入
     * @return {@link PDFont}
     * @throws IOException 输入流中加载字体IO异常
     */
    public static PDFont loadCustomFont(PDDocument document, InputStream input, boolean embedSubset) throws IOException {
        return PDType0Font.load(document, input, embedSubset);
    }

}
