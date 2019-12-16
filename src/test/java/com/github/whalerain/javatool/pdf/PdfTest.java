package com.github.whalerain.javatool.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author ZhangXi
 */
public class PdfTest {

    @Test
    public void testStamper() throws IOException, DocumentException {
        PdfReader reader = new PdfReader("D:\\apidoc_1.1.0.pdf");
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("D:\\apidoc_test.pdf"));
        PdfContentByte over = stamper.getOverContent(2);
        // 设置字体
        BaseFont bf =BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.EMBEDDED);
        // 设置透明度
        PdfGState gs = new PdfGState();
        gs.setFillOpacity(0.5f);
        // 获取总页数
        int total = reader.getNumberOfPages() + 1;//获取PDF的总页数
        System.out.println("total : " + total);
        PdfContentByte content = null;
        for(int i=1; i<total; i++) {
            content = stamper.getOverContent(i);
            content.setGState(gs);
            content.beginText();
            content.setFontAndSize(bf, 30);
            content.setTextMatrix(30, 30);
            content.showTextAligned(Element.ALIGN_LEFT, "版权所有，翻版必究", 230, 500, 45);
            content.endText();
        }
        stamper.close();
        reader.close();
    }

    @Test
    public void testWatermark() throws IOException {
        File tmpPDF;
        PDDocument doc;


        tmpPDF = new File("D:\\apidoc_test.pdf");
        doc = PDDocument.load(new File("D:\\apidoc_1.1.0.pdf"));
        doc.setAllSecurityToBeRemoved(true);
        for(PDPage page:doc.getPages()){
            PDPageContentStream cs = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true);
            String ts = "Some 版权所有 text";
            PDFont font = PDType1Font.HELVETICA_OBLIQUE;
            float fontSize = 50.0f;
            PDResources resources = page.getResources();
            PDExtendedGraphicsState r0 = new PDExtendedGraphicsState();
            // 透明度
            r0.setNonStrokingAlphaConstant(0.2f);
            r0.setAlphaSourceFlag(true);
            cs.setGraphicsStateParameters(r0);
            cs.setNonStrokingColor(200,0,0);//Red
            cs.beginText();
            cs.setFont(font, fontSize);
            // 获取旋转实例
            cs.setTextMatrix(Matrix.getRotateInstance(20,350f,490f));
            cs.showText(ts);
            cs.endText();

            cs.close();
        }
        doc.save(tmpPDF);
    }



}
