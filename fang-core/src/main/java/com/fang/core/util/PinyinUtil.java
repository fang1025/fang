package com.fang.core.util;


import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.LoggerFactory;


/**
 * 汉字辅助类
 */
public final class PinyinUtil {

    protected static final org.slf4j.Logger logger = LoggerFactory.getLogger(PinyinUtil.class);

    /**
     * 将汉字转换为全拼
     *
     * @param str
     * @return String
     */
    public static final String getPinYin(String str) {
        char[] charArr = null;
        charArr = str.toCharArray();

        // 设置汉字拼音输出的格式
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        String pinyin = "";
        String[] tempArr = null;
        int len = charArr.length;
        for (int i = 0; i < len; i++) {
            // 判断是否为汉字字符
            if (Character.toString(charArr[i]).matches("[\\u4E00-\\u9FA5]+")) {
                try {
                    tempArr = PinyinHelper.toHanyuPinyinStringArray(charArr[i], outputFormat);
                    pinyin += tempArr[0];
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    logger.error(e.getMessage());
                }
            } else {
                pinyin += Character.toString(charArr[i]);
            }
        }
        return pinyin;
    }

    /**
     * 将汉字转换为全拼,首字母大写
     *
     * @param str
     * @return String
     */
    public static final String getCamelPinYin(String str) {
        char[] charArr = null;
        charArr = str.toCharArray();

        // 设置汉字拼音输出的格式
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        String pinyin = "",tempStr = "";
        String[] tempArr = null;
        int len = charArr.length;
        for (int i = 0; i < len; i++) {
            // 判断是否为汉字字符
            if (Character.toString(charArr[i]).matches("[\\u4E00-\\u9FA5]+")) {
                try {
                    tempArr = PinyinHelper.toHanyuPinyinStringArray(charArr[i], outputFormat);
                    tempStr = tempArr[0];
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    logger.error(e.getMessage());
                    continue;
                }
            } else {
                tempStr = Character.toString(charArr[i]);
            }
            pinyin += tempStr.substring(0, 1).toUpperCase() + tempStr.substring(1);
        }
        return pinyin;
    }

    /**
     * 提取每个汉字的首字母
     *
     * @param str
     * @return String
     */
    public static final String getPinYinHeadChar(String str) {
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            // 提取汉字的首字母
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }

    /**
     * 提取每个汉字的大写首字母
     *
     * @param str
     * @return String
     */
    public static final String getPinYinHeadUperChar(String str) {
        return getPinYinHeadChar(str).toUpperCase();
    }


}
