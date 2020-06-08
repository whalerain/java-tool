package com.github.whalerain.javatool.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author ZhangXi
 */
public class PdfTest {


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
