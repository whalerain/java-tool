package com.github.whalerain.javatool.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author ZhangXi
 */
public class PinyinTool {


    public static String toHanYuPinyin(String chinese) throws BadPinyinException {
        try {
            return PinyinHelper.toHanYuPinyinString(chinese, new HanyuPinyinOutputFormat(), " ", true);
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            throw new BadPinyinException();
        }
    }



    public static class BadPinyinException extends Exception {

    }


}
