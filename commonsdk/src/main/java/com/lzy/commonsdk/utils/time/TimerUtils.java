package com.lzy.commonsdk.utils.time;

import android.os.CountDownTimer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 *
 *
 *   @author bullet
 * @date 2019\1\8 0008.
 */
public class TimerUtils {


    /**
     * 获取当前年
     * @return
     */
    public static int getCurrentYear() {
        Calendar cal=Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前月
     * @return
     */
    public static int getCurrentMonth() {
        Calendar cal=Calendar.getInstance();
        return cal.get(Calendar.MONTH);
    }

    /**
     * 获取当前日
     * @return
     */
    public static int getCurrentDay() {
        Calendar cal=Calendar.getInstance();
        return cal.get(Calendar.DATE);
    }


    /**
     * 通过传入的时间戳获取  下一天  的时间戳
     */
    public static long getDateNext(long time, int Num) {
        return time + (long) Num * 24 * 60 * 60 * 1000;
    }

    /**
     * 通过传入的时间戳获取  前一天  的时间戳
     */
    public static long getDateBefore(long time, int Num) {
        return time - (long) Num * 24 * 60 * 60 * 1000;
    }

    /***
     * 时间戳转换成日期
     *
     */
    public static String dateFormat(String timeStamp, String format) {
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long l = Long.valueOf(timeStamp);
        //单位秒
        timeString = sdf.format(new Date(l));
        return timeString;
    }

    /**
     * 转换为时间戳
     */
    public static long getTimeStamp(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = null;
        try {
            nowDate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return nowDate.getTime();
    }


    /***
     *转换为星期
     */
    public static String getWeek(String pTime) {
        String Week = "周";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "天";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
        }
        return Week;
    }

    /***
     * 毫秒换成00:00:00
     *
     */
    public static String getCountTimeByLong(long finishTime) {
        int totalTime = (int) (finishTime / 1000);
        int hour = 0, minute = 0, second = 0;
        if (3600 <= totalTime) {
            hour = totalTime / 3600;
            totalTime = totalTime - 3600 * hour;
        }
        if (60 <= totalTime) {
            minute = totalTime / 60;
            totalTime = totalTime - 60 * minute;
        }
        if (0 <= totalTime) {
            second = totalTime;
        }
        StringBuilder sb = new StringBuilder();
        if (hour < 10) {
            sb.append("0").append(hour).append(":");
        } else {
            sb.append(hour).append(":");
        }
        if (minute < 20 && minute > 10) {
            sb.append(minute).append(":");
        } else if (minute < 10) {
            sb.append("0").append(minute).append(":");
        } else {
            sb.append(hour).append(":");
        }
        if (second < 10) {
            sb.append("0").append(second);
        } else {
            sb.append(second);
        }
        return sb.toString();
    }

    /**
     * 毫秒转化时分秒毫秒
     */
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;
        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;
        StringBuffer sb = new StringBuffer();
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }
        if (milliSecond > 0) {
            sb.append(milliSecond + "毫秒");
        }
        return sb.toString();
    }

    /***
     * 00'00''
     * */
    public static String timeParse(long duration) {
        String time = "";
        long minute = duration / 60000;
        long seconds = duration % 60000;
        long second = Math.round((float) seconds / 1000);
        if (minute < 10) {
            time = "0" + minute + "'";
        } else {
            time = minute + "'";
        }
        if (second < 10) {
            time = time + "0" + second + "''";
        } else {
            time = time + second + "''";
        }
        return time;
    }

    /**
     * 00:00
     */
    public static String timeParseMs(long duration) {
        String time = "";
        long minute = duration / 60000;
        long seconds = duration % 60000;
        //四舍五入
        long second = Math.round((float) seconds / 1000);
        if (second == 60) {
            minute += 1;
            second = 0;
        }
        if (minute < 10) {
            time += "0";
        }
        time += minute + ":";
        if (second < 10) {
            time += "0";
        }
        time += second;
        return time;
    }




    public static TimeCount timeCount;
    /**
     * 第二种倒计时，用系统自带的，可以在销毁
     * @return
     */
    public static TimeCount setTimer(int time){
        timeCount = new TimeCount(time*1000, 1000);
        timeCount.start();
        return timeCount;
    }

    public static class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            //参数依次为总时长,和计时的时间间隔
            super(millisInFuture, countDownInterval);


        }
        @Override
        public void onFinish() {                            //计时完毕时触发

        }

        @Override
        public void onTick(long millisUntilFinished) {       //计时过程显示

        }
    }



    /**
     * <pre>
     * 判断date和当前日期是否在同一周内
     * 注:
     * Calendar类提供了一个获取日期在所属年份中是第几周的方法，对于上一年末的某一天
     * 和新年初的某一天在同一周内也一样可以处理，例如2012-12-31和2013-01-01虽然在
     * 不同的年份中，但是使用此方法依然判断二者属于同一周内
     * </pre>
     *
     * @param date
     * @return
     */
    public static boolean isSameWeekWithToday(Date date) {
        if (date == null) {
            return false;
        }
        // 0.先把Date类型的对象转换Calendar类型的对象
        Calendar todayCal = Calendar.getInstance();
        Calendar dateCal = Calendar.getInstance();
        todayCal.setTime(new Date());
        dateCal.setTime(date);
        // 1.比较当前日期在年份中的周数是否相同
        if (todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR)) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean isSameDay(Calendar tvCalendar, Calendar today) {
        return tvCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                && tvCalendar.get(Calendar.MONTH) == today.get(Calendar.MONTH)
                && tvCalendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH);
    }

}
