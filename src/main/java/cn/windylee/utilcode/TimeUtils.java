package cn.windylee.utilcode;

import cn.windylee.constant.TimeConstant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    private static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private TimeUtils() {
        throw new UnsupportedOperationException("u cann't instantiate me....");
    }

    public static String millis2String(long millis) {
        return millis2String(millis, DEFAULT_FORMAT);
    }

    public static String millis2String(long millis, DateFormat format) {
        return format.format(new Date(millis));
    }

    public static long string2Millis(String time) {
        return string2Millis(time, DEFAULT_FORMAT);
    }

    public static long string2Millis(String time, DateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static Date string2Date(String time) {
        return string2Date(time, DEFAULT_FORMAT);
    }

    public static Date string2Date(String time, DateFormat format) {
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String date2String(Date date) {
        return date2String(date, DEFAULT_FORMAT);
    }

    public static String date2String(Date date, DateFormat format) {
        return format.format(date);
    }

    public static long date2Millis(Date date) {
        return date.getTime();
    }

    public static Date millis2Date(long millis) {
        return new Date(millis);
    }

    public static long getTimeSpan(final String time1, final String time2, final int unit) {
        return getTimeSpan(time1, time2, DEFAULT_FORMAT, unit);
    }

    public static long getTimeSpan(final String time1, final String time2, DateFormat format, int unit) {
        return millis2TimeSpan(string2Millis(time1, format) - string2Millis(time2, format), unit);
    }

    public static long getTimeSpan(final Date date1, final Date date2, final int unit) {
        return getTimeSpan(date2Millis(date1), date2Millis(date2), unit);
    }

    public static long getTimeSpan(final long millis1, final long millis2, int unit) {
        return millis2TimeSpan(millis1 - millis2, unit);
    }

    public static String getFitTimeSpan(final String time1, final String time2, final int precision) {
        long delta = string2Millis(time1) - string2Millis(time2);
        return millis2FitTimeSpan(delta, precision);
    }

    public static String getFitTimeSpan(final String time1, final String time2, DateFormat format, int precision) {
        long delta = string2Millis(time1, format) - string2Millis(time2, format);
        return millis2FitTimeSpan(delta, precision);
    }

    public static String getFitTimeSpan(final Date date1, final Date date2, int precision) {
        long delta = date1.getTime() - date2.getTime();
        return millis2FitTimeSpan(delta, precision);
    }

    public static String getFitTimeSpan(final long millis1, final long millis2, int precision) {
        return millis2FitTimeSpan(millis1 - millis2, precision);
    }

    public static long getNowMills() {
        return System.currentTimeMillis();
    }

    public static String getNowString() {
        return millis2String(System.currentTimeMillis(), DEFAULT_FORMAT);
    }

    public static String getNowString(DateFormat format) {
        return millis2String(System.currentTimeMillis(), format);
    }

    public static Date getNowDate() {
        return new Date();
    }

    public static long getTimeSpanByNow(String time, int unit) {
        return getTimeSpan(time, getNowString(), DEFAULT_FORMAT, unit);
    }

    public static long getTimeSpanByNow(String time, DateFormat format, int unit) {
        return getTimeSpan(time, getNowString(), format, unit);
    }

    public static long getTimeSpanByNow(Date date, int unit) {
        return getTimeSpan(date, new Date(), unit);
    }

    public static long getTimeSpanByNow(long millis, int unit) {
        return getTimeSpan(millis, getNowMills(), unit);
    }

    public static String getFitTimeSpanByNow(String time, int precision) {
        return getFitTimeSpan(time, getNowString(), DEFAULT_FORMAT, precision);
    }

    public static String getFitTimeSpanByNow(String time, DateFormat format, int precision) {
        return getFitTimeSpan(time, getNowString(format), format, precision);
    }

    public static String getFitTimeSpanByNow(Date date, int precision) {
        return getFitTimeSpan(date, getNowDate(), precision);
    }

    public static String getFitTimeSpanByNow(long millis, int precision) {
        return getFitTimeSpan(millis, getNowMills(), precision);
    }

    public static String getFriendlyTimeSpanByNow(String time) {
        return getFriendlyTimeSpanByNow(time, DEFAULT_FORMAT);
    }

    public static String getFriendlyTimeSpanByNow(String time, DateFormat format) {
        return getFriendlyTimeSpanByNow(string2Millis(time, format));
    }

    public static String getFriendlyTimeSpanByNow(Date date) {
        return getFriendlyTimeSpanByNow(date.getTime());
    }

    public static String getFriendlyTimeSpanByNow(long millis) {
        long now = getNowMills();
        long span = now - millis;
        if (span < 0) {
            return String.format("%tc", millis);
        }
        if (span < TimeConstant.SEC) {
            return "刚刚";
        } else if (span < TimeConstant.MIN) {
            return String.format(Locale.getDefault(), "%s秒前", span / TimeConstant.SEC);
        } else if (span < TimeConstant.HOUR) {
            return String.format(Locale.getDefault(), "%s分钟前", span / TimeConstant.MIN);
        }

        long wee = getWeekOfToday();
        if (millis >= wee) {
            return String.format("今天%tR", millis);
        } else if (millis >= wee - TimeConstant.DAY) {
            return String.format("昨天%tR", millis);
        } else {
            return String.format("%tF", millis);
        }
    }

    public static long getWeekOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static long getMillis(long millis, long timeSpan, int unit) {
        return millis + timeSpan2Millis(timeSpan, unit);
    }

    public static long getMillis(String time, long timeSpan, int unit) {
        return getMillis(string2Millis(time), timeSpan, unit);
    }

    public static long getMillis(String time, DateFormat format, long timeSpan, int unit) {
        return getMillis(string2Millis(time, format), timeSpan, unit);
    }

    public static long getMillis(Date date, long timeSpan, int unit) {
        return getMillis(date.getTime(), timeSpan, unit);
    }

    public static String getString(long millis, long timeSpan, int unit) {
        return getString(millis, DEFAULT_FORMAT, timeSpan, unit);
    }

    public static String getString(long millis, DateFormat format, long timeSpan, int unit) {
        return millis2String(millis + timeSpan2Millis(timeSpan, unit), format);
    }

    public static String getString(String time, long timeSpan, int unit) {
        return getString(time, DEFAULT_FORMAT, timeSpan, unit);
    }

    public static String getString(String time, DateFormat format, long timeSpan, int unit) {
        return millis2String(string2Millis(time, format) + timeSpan2Millis(timeSpan, unit), format);
    }

    public static String getString(Date date, long timeSpan, int unit) {
        return getString(date, DEFAULT_FORMAT, timeSpan, unit);
    }

    public static String getString(Date date, DateFormat format, long timeSpan, int unit) {
        return millis2String(date.getTime() + timeSpan2Millis(timeSpan, unit), format);
    }

    public static Date getDate(long millis, long timeSpan, int unit) {
        return millis2Date(millis + timeSpan2Millis(timeSpan, unit));
    }

    public static Date getDate(String time, long timeSpan, int unit) {
        return getDate(time, DEFAULT_FORMAT, timeSpan, unit);
    }

    public static Date getDate(String time, DateFormat format, long timeSpan, int unit) {
        return millis2Date(string2Millis(time, format) + timeSpan2Millis(timeSpan, unit));
    }

    public static Date getDate(Date date, long timeSpan, int unit) {
        return millis2Date(date.getTime() + timeSpan2Millis(timeSpan, unit));
    }

    public static long getMillisByNow(long timeSpan, int unit) {
        return getMillis(getNowMills(), timeSpan, unit);
    }

    public static String getStringByNow(long timeSpan, int unit) {
        return getStringByNow(timeSpan, DEFAULT_FORMAT, unit);
    }

    public static String getStringByNow(long timeSpan, DateFormat format, int unit) {
        return getString(getNowMills(), format, timeSpan, unit);
    }

    public static Date getDateByNow(long timeSpan, int unit) {
        return getDate(getNowDate(), timeSpan, unit);
    }

    public static boolean isToday(String time) {
        return isToday(time, DEFAULT_FORMAT);
    }

    public static boolean isToday(String time, DateFormat format) {
        return isToday(string2Millis(time, format));
    }

    public static boolean isToday(Date date) {
        return isToday(date.getTime());
    }

    public static boolean isToday(long millis) {
        long wee = getWeekOfToday();
        return millis > wee && millis < wee + TimeConstant.DAY;
    }

    public static boolean isLeapYear(String time) {
        return isLeapYear(time, DEFAULT_FORMAT);
    }

    public static boolean isLeapYear(String time, DateFormat format) {
        return isLeapYear(string2Date(time, format));
    }

    public static boolean isLeapYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return isLeapYear(year);
    }

    public static boolean isLeapYear(int year) {
        return year % 100 != 0 && year % 4 == 0 || year % 400 == 0;
    }

    private static long timeSpan2Millis(long timeSpan, int unit) {
        return timeSpan * unit;
    }

    private static long millis2TimeSpan(long timeSpan, int unit) {
        return timeSpan / unit;
    }

    private static String millis2FitTimeSpan(long millis, int precision) {
        if (precision <= 0) return null;
        precision = Math.min(precision, 5);
        String[] units = new String[]{"天", "小时", "分钟", "秒", "毫秒"};
        if(millis==0){
            return 0 + units[precision - 1];
        }
        StringBuilder sb = new StringBuilder();
        if(millis<0){
            sb.append("-");
            millis = -millis;
        }
        int[] unitLen = {86400000, 3600000, 60000, 1000, 1};
        for(int i=0;i<precision;i++){
            if(millis>=unitLen[i]){
                long mode = millis/unitLen[i];
                millis -= mode * unitLen[i];
                sb.append(mode).append(units[i]);
            }
        }
        return sb.toString();
    }

}
