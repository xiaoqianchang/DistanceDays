package com.adups.distancedays;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 测试
 *
 * https://www.cnblogs.com/code-fly-blogs/p/9974949.html
 *
 * <p>
 * Created by Chang.Xiao on 2019/10/23.
 *
 * @version 1.0
 */
public class Test {

    public static void main(String args[]) {
        SimpleDateFormat simdf = new SimpleDateFormat("MM月dd日");

        Calendar cal = Calendar.getInstance();
        System.out.println("现在时间："+simdf.format(cal.getTime()));
        //分别获取年、月、日
        System.out.println("年："+cal.get(cal.YEAR));
        System.out.println("月："+(cal.get(cal.MONTH)+1));//老外把一月份整成了0，翻译成中国月份要加1
        System.out.println("日："+cal.get(cal.DATE));

        cal.set(cal.DAY_OF_WEEK, cal.MONDAY);
        String weekhand = simdf.format(cal.getTime());
        System.out.println("当前时间所在周周一日期："+weekhand);
//        cal.set(cal.DAY_OF_WEEK, cal.SUNDAY); // 这个不符合中国人的时间观，老外把上周周日定为一周的开始。

        cal.set(Calendar.DATE, cal.get(cal.DATE) + 6);
        String weeklast = simdf.format(cal.getTime());
        System.out.println("当前时间所在周周日日期："+weeklast);


//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar., calendar.get(cal.DATE) + 6);
//        String weekYear = simdf.format(calendar.getTime());
//        System.out.println("当前时间所在春节日期："+weekYear);

    }
}
