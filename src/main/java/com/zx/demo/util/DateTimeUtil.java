package com.zx.demo.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
    /**
     * 将UTC时间转换为CST时间
     * */
    public static Date getCSTTimeFromUTC(Date date){
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY)+8);

        return calendar.getTime();
    }
}
