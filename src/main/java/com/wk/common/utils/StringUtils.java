package com.wk.common.utils;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class StringUtils extends org.springframework.util.StringUtils {
    private static final String[] sts = {"", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private static final String[] numStr = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};

    private static final Random ran = new Random();
    /**
     * 删除HTML标签的正则匹配
     */
    private static final Pattern HTML_PATTERN = Pattern.compile("</?[^>]*>");
    /**
     * 不包含的img标签的HTML标签正则匹配
     */
    private static final Pattern NOT_IMG_HTML_PATTERN = Pattern.compile("</?[^/?(img)][^><]*>");
    /**
     * 不包含的img和p标签的HTML标签正则匹配
     */
    private static final Pattern NOT_IMG_P_HTML_PATTERN = Pattern.compile("</?[^/?(img|p|br)][^><]*>");
    /**
     * 不包含的img和br标签的HTML标签正则匹配
     */
    private static final Pattern NOT_IMG_BR_HTML_PATTERN = Pattern.compile("</?[^/?(img|br)][^><]*>");

    /**
     * 编码 emoji 时的正则匹配
     *
     * @see #DECODE_EMOJI_PATTERN
     * @see Character#MIN_HIGH_SURROGATE
     * @see Character#MIN_LOW_SURROGATE
     * @see Character#MAX_HIGH_SURROGATE
     * @see Character#MAX_LOW_SURROGATE
     */
    private static final Pattern ENCODE_EMOJI_PATTERN = Pattern.compile("[\uD800\uDC00-\uDBFF\uDFFF]");

    /**
     * 解码 emoji 时的正则匹配
     *
     * @see #ENCODE_EMOJI_PATTERN
     */
    private static final Pattern DECODE_EMOJI_PATTERN = Pattern.compile("\\\\u[0-9A-Za-z]{4}");

    /**
     * 数字转成大写字母
     *
     * @param number
     * @return
     */
    public static String numberToStr(int number) {
        char c1 = (char) (number + 64);
        return String.valueOf(c1);
    }

    /**
     * 通过指定元素，获取char数组中两个元素间的内容，并把内容转成String类型
     *
     * @param str      数据内容
     * @param indexStr 指定的元素
     * @return 值
     */
    public static String subStrByIndex(String str, String indexStr) {
        if (isEmpty(str)) {
            return null;
        }
        if (isEmpty(indexStr)) {
            return str;
        }
        int tagLen = indexStr.length();
        int start = str.indexOf(indexStr);
        if (start == -1) {
            return null;
        }
        String _str = str.substring(start + tagLen);
        int end = _str.indexOf(indexStr);
        if (end == -1) {
            return null;
        }
        str = str.substring(start + tagLen, end + start + tagLen);
        return str;
    }

    /**
     * 把数字转成中文（最多支持两位数）
     *
     * @param index
     * @return 转换好的
     */
    public static String getStrNumIndex(int index) {
        String val = String.valueOf(index);
        String[] arr = val.split("");
        if (arr.length == 1) {
            return numStr[index];
        } else if (arr.length == 2) {
            return numStr[Integer.parseInt(arr[0])] + "十" + numStr[Integer.parseInt(arr[1])];
        }
        return null;
    }

    public static boolean isNotEmpty(Object value) {
        if (!isEmpty(value) && !value.toString().trim().equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 获取随机数据（纯数字）
     *
     * @param n 几位
     * @return 随机字符串
     */
    public static String getRandStr(int n) {
        Random random = new Random();
        String sRand = "";
        for (int i = 0; i < n; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
        }
        return sRand;
    }

    /**
     * 判断当前字符串是否是由数字组成(可判断带小数点的)
     *
     * @param str 要检查的字符串
     * @return 结果
     */
    public static boolean isDigit(String str) {
        //检查是否为空
        if (StringUtils.isEmpty(str.trim())) {
            return false;
        }
        return Pattern.matches("^[0-9]+(.[0-9]{1,2})?$", str);
    }

    /**
     * 判断字符串是否都是数字
     *
     * @param str 验证的字符串
     * @return true是，false否
     */
    public static boolean isNumber(String str) {
        if (isNotEmpty(str)) {
            return Pattern.matches("^\\d+$", str);
        }
        return false;
    }

    public static boolean isMobile(String mobile) {
        if (isNotEmpty(mobile)) {
            return Pattern.matches("^[1][0-9]{10}$", mobile);
        }
        return false;
    }

    public static boolean isEmail(String email) {
        if (isNotEmpty(email)) {
            return Pattern.matches("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$", email);
        }
        return false;
    }

    /**
     * 验证字符串是否是英文数字组合
     *
     * @param val
     * @return true是，false不是
     */
    public static boolean isLetterOrNum(String val) {
        Pattern pattern = Pattern.compile("(?!^\\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]*");
        Matcher matcher = pattern.matcher(val);
        return matcher.matches();
    }

    /**
     * 判断是否包含有中文
     *
     * @param str 内容
     * @return false不包含，true包含
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 生成随机两个字母开头  3位数字结尾
     *
     * @return
     */
    public static String generatingLotNumber() {
        String key = "";
        for (int i = 0; i < 2; i++) {
            key += sts[ran.nextInt(26)];
        }
        String string = StringUtils.getRandStr(3);
        return key + string;
    }

    /**
     * 生成随机两个字母开头  8位数字结尾
     *
     * @return
     */
    public static String generatingLotNumberCoupon() {
        String key = "";
        for (int i = 0; i < 2; i++) {
            key += sts[ran.nextInt(26)];
        }
        String string = StringUtils.getRandStr(8);
        return key + string;
    }

    /**
     * 根据输入的字符串生成一个优惠券编码
     *
     * @param input 输入字符串
     * @return 优惠券编码
     */
    public static String handleStrValue(String input) {
        StringBuilder sb = new StringBuilder(input);
        for (int i = 0; i < 4; i++) {
            String key = sts[ran.nextInt(26)];
            int offset = ran.nextInt(input.length() - 1);
            sb.insert(offset, key);
        }
        return sb.toString();
    }

    /**
     * 把字符串的头尾某字符去掉
     *
     * @param str 原字符串
     * @param tag 去掉的字符
     * @return 处理后的字符
     */
    public static String subHeadTailString(String str, String tag) {
        if (isNotEmpty(str) && isNotEmpty(tag)) {
            str = str.trim();
            tag = tag.trim();
            if (str.startsWith(tag)) {
                str = str.substring(tag.length());
            }
            if (str.endsWith(tag)) {
                str = str.substring(0, str.length() - tag.length());
            }
        }
        return str;
    }

    /**
     * 获取专业条件
     *
     * @param columnName
     * @param subjectIds
     * @param isNull     是否把满足空条件的数据拼上
     * @return
     */
    public static String getSubjectSql(String columnName, String subjectIds, boolean isNull) {
        List<String> sqlList = new ArrayList<>();
        if (isNotEmpty(subjectIds)) {
            String[] arr = subjectIds.split("\\*");
            Arrays.stream(arr).forEach(_subjectIds -> {
                String str = getSubjectSqlContext(columnName, _subjectIds, isNull);
                if (isNotEmpty(str)) {
                    sqlList.add(str);
                }
            });
            if (ObjectUtils.isNotEmpty(sqlList)) {
                return sqlList.stream().collect(Collectors.joining(" or ", " and (", ")"));
            }
        }
        return "";
    }

    /**
     * 获取专业条件
     *
     * @param columnName
     * @param subjectIds
     * @return
     */
    public static String getSubjectSql(String columnName, String subjectIds) {
        return getSubjectSql(columnName, subjectIds, true);
    }

    /**
     * 获取专业SQL内容
     *
     * @param columnName
     * @param subjectIds
     * @return
     */
    private static String getSubjectSqlContext(String columnName, String subjectIds, boolean isNull) {
        String _subjectIds = StringUtils.subHeadTailString(subjectIds, ",");
        String[] arr = _subjectIds.split(",");
        if (arr.length == 1) {
            if (StringUtils.isNotEmpty(columnName)) {
                return " " + columnName + " like '%," + _subjectIds.trim() + ",%'";
            } else {
                return " subjectIds like '%," + _subjectIds.trim() + ",%'";
            }
        } else if (arr.length > 1) {
            StringBuffer sb = new StringBuffer(" (");
            IntStream.range(1, arr.length).forEach(index -> {
                String name = "subjectIds";
                if (StringUtils.isNotEmpty(columnName)) {
                    name = columnName;
                }
                if (index < arr.length - 1) {
                    sb.append(" ").append(name).append(" like '%,").append(arr[index]).append(",%' or ");
                } else {
                    if (isNull) {
                        sb.append(" ").append(name).append(" like '%,").append(arr[index]).append(",%' or ").append(name).append("  ='' or ").append(name).append(" is null");
                    } else {
                        sb.append(" ").append(name).append(" like '%,").append(arr[index]).append(",%'");
                    }
                }
            });
            sb.append(")");
            return sb.toString();
        }
        return "";
    }

    /**
     * 验证专业条件
     *
     * @param subjectIds 专业条件
     * @return 返回null则验证通过，不为空则验证不通过
     */
    public static String checkSubjectCondition(String subjectIds) {
        subjectIds = subHeadTailString(subjectIds, ",");
        if (isNotEmpty(subjectIds)) {
            return null;
        }
        return "请选择专业/科目";
    }

    /**
     * 判断输入字符串长度是否超出指定范围
     *
     * @param input 输入字符
     * @param min   最小长度
     * @param max   最大长度
     * @return {@code true} 字符串为空字符串长度超出指定范围
     * @see #isEmpty(Object)
     */
    public static boolean lengthOutOfRange(CharSequence input, int min, int max) {
        return StringUtils.isEmpty(input) || input.length() < min || input.length() > max;
    }


    /**
     * 生成指定长度的随机字符串
     *
     * @param strLength 长度
     * @return
     * @author liuqinggang
     */
    public static String getRandomString(int strLength) {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < strLength; i++) {
            int charInt;
            char c;
            if (random.nextBoolean()) {
                charInt = 48 + random.nextInt(10);
                c = (char) charInt;
                buffer.append(c);
                continue;
            }
            charInt = 65;
            if (random.nextBoolean())
                charInt = 65 + random.nextInt(26);
            else
                charInt = 97 + random.nextInt(26);
            if (charInt == 79)
                charInt = 111;
            c = (char) charInt;
            buffer.append(c);
        }

        return buffer.toString();
    }

    /**
     * 得到字符串中的img路径
     *
     * @param s 字符串
     */
    public static List<String> getImages(String s) {
        if (StringUtils.isEmpty(s)) {
            return Collections.emptyList();
        }
        List<String> images = new LinkedList<>();
        Pattern img = Pattern.compile("<img.*src\\s*=\\s*(.*?)[^>]*?>");
        Pattern src = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)");
        Matcher matcher = img.matcher(s);
        while (matcher.find()) {
            Matcher local = src.matcher(matcher.group());
            while (local.find()) {
                images.add(local.group().split("=")[1].replaceAll("\"|'", ""));
            }
        }
        return images;
    }

    /**
     * 删除html标签后的字符序列
     *
     * @param cs 字符序列
     * @return 删除html标签后的字符序列
     */
    public static String deleteHtml(CharSequence cs) {
        if (isEmpty(cs)) {
            return "";
        }
        return HTML_PATTERN.matcher(cs).replaceAll("");
    }

    /**
     * 删除不包含在IMG在内的HTML标签
     *
     * @param cs 字符序列
     * @return 删除html标签后的字符序列
     */
    public static String deleteNotImgHtml(CharSequence cs) {
        if (isEmpty(cs)) {
            return "";
        }
        return NOT_IMG_HTML_PATTERN.matcher(cs).replaceAll("");
    }

    /**
     * 删除不包含在IMG在内的HTML标签
     *
     * @param cs 字符序列
     * @return 删除html标签后的字符序列
     */
    public static String deleteNotImgAndPHtml(CharSequence cs) {
        if (isEmpty(cs)) {
            return "";
        }
        return NOT_IMG_P_HTML_PATTERN.matcher(cs).replaceAll("");
    }

    /**
     * 把P标签换成br
     *
     * @param cs
     * @return
     */
    public static String replacePtoBr(CharSequence cs) {
        String str = deleteNotImgAndPHtml(cs);
        if (isNotEmpty(str)) {
            return Pattern.compile("<p([^>]*)>").matcher(str).replaceAll("").replaceAll("</p>", "<br/>");
        }
        return str;
    }

    /**
     * 删除不包含在IMG和br在内的HTML标签
     *
     * @param cs 字符序列
     * @return 删除html标签后的字符序列
     */
    public static String deleteNotImgAndBrHtml(CharSequence cs) {
        if (isEmpty(cs)) {
            return "";
        }
        return NOT_IMG_BR_HTML_PATTERN.matcher(cs).replaceAll("");
    }

    /**
     * 编码emoji
     * <p>
     * 如 😊 => \ud83d\ude0a
     *
     * @param text 文本
     */
    public static String encodeEmoji(String text) {
        if (containsEmoji(text)) {
            StringBuilder sb = new StringBuilder();
            char[] chars = text.toCharArray();
            for (char ch : chars) {
                if (Character.isHighSurrogate(ch) || Character.isLowSurrogate(ch)) {
                    sb.append("\\u").append(Integer.toHexString(ch));
                } else {
                    sb.append(ch);
                }
            }
            return sb.toString();
        }
        return text;
    }

    /**
     * 解码emoji
     * <p>
     * 如 \ud83d\ude0a => 😊
     *
     * @param text 文本
     */
    public static String decodeEmoji(String text) {
        if (DECODE_EMOJI_PATTERN.matcher(text).find()) {
            StringBuilder sb = new StringBuilder(text.length());
            char[] chars = text.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                // 排除 \\ud800\\udc00 否则ios 解析有问题
                if (c == '\\' && chars[i + 1] == 'u' && (i == 0 || chars[i - 1] != '\\')) {
                    char cc = 0;
                    for (int j = 0; j < 4; j++) {
                        char ch = Character.toLowerCase(chars[i + 2 + j]);
                        if ('0' <= ch && ch <= '9' || 'a' <= ch && ch <= 'f') {
                            cc |= (Character.digit(ch, 16) << (3 - j) * 4);
                        } else {
                            cc = 0;
                            break;
                        }
                    }
                    if (cc > 0) {
                        i += 5;
                        sb.append(cc);
                        continue;
                    }
                }
                sb.append(c);
            }
            return sb.toString();
        }
        return text;
    }

    /**
     * 数字转成字母，1-26 ： A-Z
     *
     * @param num 数字
     * @return 字母
     */
    public static String numberToLetter(int num) {
        if (num <= 0) {
            return null;
        }
        String letter = "";
        num--;
        do {
            if (letter.length() > 0) {
                num--;
            }
            letter = ((char) (num % 26 + (int) 'A')) + letter;
            num = ((num - num % 26) / 26);
        } while (num > 0);
        return letter;
    }

    /**
     * 字母转数字  A-Z ：1-26
     *
     * @param letter
     * @return
     */
    public static int letterToNumber(String letter) {
        int length = letter.length();
        int num = 0;
        int number = 0;
        for (int i = 0; i < length; i++) {
            char ch = letter.charAt(length - i - 1);
            num = (ch - 'A' + 1);
            num *= Math.pow(26, i);
            number += num;
        }
        return number;
    }

    public static boolean checkUrl(String url) {
        return url.matches("(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");
    }

    /**
     * 是否包含emoji
     *
     * @param text 文本
     * @return {@code true}
     */
    private static boolean containsEmoji(String text) {
        return ENCODE_EMOJI_PATTERN.matcher(text).find();
    }

}
