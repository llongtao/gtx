package com.gtx.common.utils;

import com.gtx.common.constants.GtxConstants;
import com.gtx.common.exception.GtxException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @author LILONGTAO
 * @date 2019-11-01
 */
public class StringUtils {

    /**
     * 空字符串
     */
    public static final String EMPTY = "";

    private static final char MINUS = '-';
    private static final char UNDERLINE = '_';
    private static final char DOT = '.';

    /**
     * 是否为空
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return (str == null) || (str.isEmpty());
    }

    /**
     * 是否空白字符串
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * 字符串是否相等
     *
     * @param a 字符串
     * @param b 字符串
     * @return boolean
     */
    public static boolean equals(String a, String b) {
        if (a == null) {
            return b == null;
        }
        return a.equals(b);
    }

    /**
     * 字符串是否相等忽略大小写
     *
     * @param a 字符串
     * @param b 字符串
     * @return the boolean
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        if (a == null) {
            return b == null;
        }
        return a.equalsIgnoreCase(b);
    }

    /**
     * 流转字符串
     *
     * @param is InputStream
     * @return 字符串
     */
    public static String inputStream2String(InputStream is) {
        if (is == null) {
            return null;
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int i = -1;
            while ((i = is.read()) != -1) {
                baos.write(i);
            }
            return baos.toString(GtxConstants.DEFAULT_CHARSET_NAME);
        } catch (Exception e) {
            throw new GtxException(e);
        }
    }

    /**
     * 流转字符数组
     *
     * @param is InputStream
     * @return 字符数组
     */
    public static byte[] inputStream2Bytes(InputStream is) {
        if (is == null) {
            return null;
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int i = -1;
            while ((i = is.read()) != -1) {
                baos.write(i);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            throw new GtxException(e);
        }
    }

    /**
     * Object.toString()
     *
     * @param obj the obj
     * @return string string
     */
    public static String toString(Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj.getClass().isPrimitive()) {
            return String.valueOf(obj);
        }
        if (obj instanceof String) {
            return (String)obj;
        }
        if (obj instanceof Number || obj instanceof Character || obj instanceof Boolean) {
            return String.valueOf(obj);
        }
        if (obj instanceof Date) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(obj);
        }
        if (obj instanceof Collection) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            if (!((Collection)obj).isEmpty()) {
                for (Object o : (Collection)obj) {
                    sb.append(toString(o)).append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("]");
            return sb.toString();
        }
        if (obj instanceof Map) {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            if (!((Map)obj).isEmpty()) {
                for (Object k : ((Map)obj).keySet()) {
                    Object v = ((Map)obj).get(k);
                    sb.append(toString(k)).append("->").append(toString(v)).append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("}");
            return sb.toString();
        }
        StringBuilder sb = new StringBuilder();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            sb.append(field.getName());
            sb.append("=");
            try {
                Object f = field.get(obj);
                sb.append(toString(f));
            } catch (Exception ignored) {
            }
            sb.append(";");
        }
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     *
     * @param param 驼峰字符串
     * @return 下划线 string
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     *
     * @param param 下划线
     * @return 驼峰 string
     */
    public static String underlineToCamel(String param) {
        return formatCamel(param, UNDERLINE);
    }

    /**
     * -转驼峰
     *
     * @param param -
     * @return 驼峰 string
     */
    public static String minusToCamel(String param) {
        return formatCamel(param, MINUS);
    }

    /**
     * .转驼峰
     *
     * @param param .
     * @return 驼峰 string
     */
    public static String dotToCamel(String param) {
        return formatCamel(param, DOT);
    }

    /**
     * 转驼峰
     *
     * @param param 字符串
     * @param sign 待转字符
     * @return 驼峰 string
     */
    private static String formatCamel(String param, char sign) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == sign) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
