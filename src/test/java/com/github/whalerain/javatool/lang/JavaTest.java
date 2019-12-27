package com.github.whalerain.javatool.lang;

import org.junit.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * @author ZhangXi
 */
public class JavaTest {

    @Test
    public void testNumber() {
        Timestamp time = new Timestamp(Long.MAX_VALUE);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String value = time.toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        System.out.println("max long time :" + value);
    }

}
