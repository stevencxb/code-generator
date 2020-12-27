package com.chenxiaobo.generator.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Title: DateUtil
 * @Description: 时间工具类
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2019-05-18 12:40
 * @Version V1.0
 */
public class DateUtil {

    /**
      * 常用变量
      */
    public static final String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_HMS = "HH:mm:ss";
    public static final String DATE_FORMAT_HM = "HH:mm";
    public static final String DATE_FORMAT_YMDHM = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_YMDHMS = "yyyyMMddHHmmss";
    public static final long ONE_DAY_MILLS = 3600000 * 24;
    public static final int WEEK_DAYS = 7;
    private static final int dateLength = DATE_FORMAT_YMDHM.length();


    /**
      * 日期转换为制定格式字符串
      *
      * @param time
      * @param format
      * @return
      */
    public static String formatDateToString(Date time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(time);
    }

    /**
      * 字符串转换为制定格式日期
      * (注意：当你输入的日期是2014-12-21 12:12，format对应的应为yyyy-MM-dd HH:mm
      * 否则异常抛出)
      * @param date
      * @param format
      * @return
      * @throws ParseException
      *       @
      */
    public static Date formatStringToDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex.toString());
        }
    }

}
