package com.adups.distancedays.utils;

import com.alibaba.fastjson.asm.Opcodes;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 阴历阳历转换
 * 依赖 com.alibaba:fastjson:1.1.46.sec01
 * <p>
 * Created by Chang.Xiao on 2019/10/14.
 *
 * @version 1.0
 */
public class LunarCalendar {

    /* renamed from: a */
    public static List<String> f5613a = Arrays.asList(new String[]{"正月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "冬月", "腊月"});

    /* renamed from: b */
    public static final String[] f5614b = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};

    /* renamed from: c */
    public static final List<String> f5615c = Arrays.asList(new String[]{"初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十", "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十", "廿一", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八", "廿九"});

    /* renamed from: d */
    private static final int[] f5616d = {0, 31, 59, 90, 120, Opcodes.DCMPL, Opcodes.PUTFIELD, 212, 243, 273, 304, 334, 365};

    /* renamed from: e */
    private static final int[] f5617e = {8697535, 306771, 677704, 5580477, 861776, 890180, 4631225, 354893, 634178, 2404022, 306762, 6966718, 675154, 861510, 6116026, 742478, 879171, 2714935, 613195, 7642049, 300884, 674632, 5973436, 435536, 447557, 4905656, 177741, 612162, 2398135, 300874, 6703934, 870993, 959814, 5690554, 372046, 177732, 3749688, 601675, 8165055, 824659, 870984, 7185723, 742735, 354885, 4894137, 154957, 601410, 2921910, 693578, 8080061, 445009, 742726, 5593787, 318030, 678723, 3484600, 338764, 9082175, 955730, 436808, 7001404, 701775, 308805, 4871993, 677709, 337474, 4100917, 890185, 7711422, 354897, 617798, 5549755, 306511, 675139, 5056183, 861515, 9261759, 742482, 748103, 6909244, 613200, 301893, 4869049, 674637, 11216322, 435540, 447561, 7002685, 702033, 612166, 5543867, 300879, 412484, 3581239, 959818, 8827583, 371795, 702023, 5846716, 601680, 824901, 5065400, 870988, 894273, 2468534, 354889, 8039869, 154962, 601415, 6067642, 693582, 739907, 4937015, 709962, 9788095, 309843, 678728, 6630332, 338768, 693061, 4672185, 436812, 709953, 2415286, 308810, 6969149, 675409, 861766, 6198074, 873293, 371267, 3585335, 617803, 11841215, 306515, 675144, 7153084, 861519, 873028, 6138424, 744012, 355649, 2403766, 301898, 8014782, 674641, 697670, 5984954, 447054, 711234, 3496759, 603979, 8689601, 300883, 412488, 6726972, 959823, 436804, 4896312, 699980, 601666, 3970869, 824905, 8211133, 870993, 894277, 5614266, 354894, 683331, 4533943, 339275, 9082303, 693587, 739911, 7034171, 709967, 350789, 4873528, 678732, 338754, 3838902, 430921, 7809469, 436817, 709958, 5561018, 308814, 677699, 4532024, 861770, 9343806, 873042, 895559, 6731067, 355663, 306757, 4869817, 675148, 857409, 2986677};

    /* renamed from: f */
    private int f5618f;

    /* renamed from: g */
    private int f5619g;

    /* renamed from: h */
    private int f5620h;

    /* renamed from: i */
    private boolean f5621i;

    public boolean isLeapMonth() {
        return this.f5621i;
    }

    public void setLeapMonth(boolean isLeapMonth) {
        this.f5621i = isLeapMonth;
    }

    public int getYear() {
        return this.f5618f;
    }

    public void setYear(int year) {
        this.f5618f = year;
    }

    public int getMonth() {
        return this.f5619g;
    }

    public void setMonth(int month) {
        this.f5619g = month;
    }

    public int getDay() {
        return this.f5620h;
    }

    public void setDay(int day) {
        this.f5620h = day;
    }

    public LunarCalendar(int year, int month, int day, boolean isLeapMonth) {
        this.f5618f = year;
        this.f5619g = month;
        this.f5620h = day;
        this.f5621i = isLeapMonth;
    }

    public LunarCalendar(Calendar solarCalendar) {
        boolean z = true;
        int[] data = solarToLunar(solarCalendar);
        if (data[0] > 2099) {
            this.f5618f = 2099;
        } else if (data[0] < 1900) {
            this.f5618f = 1900;
        } else {
            this.f5618f = data[0];
        }
        this.f5619g = data[1];
        this.f5620h = data[2];
        if (data[3] != 1) {
            z = false;
        }
        this.f5621i = z;
    }

    /* Debug info: failed to restart local var, previous not found, register: 4 */
    public LunarCalendar addOneMonth() {
        int leapMonth = leapMonth(this.f5618f);
        if (this.f5619g < 12 && (this.f5621i || (!this.f5621i && leapMonth != this.f5619g))) {
            this.f5619g++;
            return this;
        } else if (!this.f5621i && leapMonth == this.f5619g) {
            this.f5621i = true;
            return this;
        } else if (this.f5619g != 12) {
            return this;
        } else {
            if (!this.f5621i && (this.f5621i || leapMonth == this.f5619g + 1)) {
                return this;
            }
            if (this.f5618f >= 2099 || this.f5618f < 1900) {
                return null;
            }
            this.f5618f++;
            this.f5619g = 1;
            return this;
        }
    }

    public LunarCalendar addOneYear() {
        if (this.f5618f >= 2099 || this.f5618f < 1900) {
            return null;
        }
        if (leapMonth(this.f5618f + 1) == this.f5619g) {
            this.f5618f++;
            this.f5621i = true;
            return this;
        }
        this.f5618f++;
        this.f5621i = false;
        return this;
    }

    public Calendar getSolarCalendar() {
        return lunarToSolarCalendar(this.f5618f, this.f5619g, this.f5620h, this.f5621i);
    }

    public static String getChinaDayString(int day) {
        if (day > 30 || day <= 0) {
            return "";
        }
        if (day == 30) {
            return "三十";
        }
        return (String) f5615c.get(day - 1);
    }

    public String toString() {
        int monthIndex;
        if (this.f5619g > 12) {
            monthIndex = 11;
        } else {
            monthIndex = this.f5619g - 1;
        }
        return this.f5618f + "年" + (this.f5621i ? "闰" : "") + ((String) f5613a.get(monthIndex)) + getChinaDayString(this.f5620h);
    }

    public static int[] lunarToSolar(int year, int month, int monthDay, boolean isLeapMonth) {
        int dayOffset = 0;
        if (monthDay < 1) {
            monthDay = 1;
        }
        if (year < 1900 || year > 2099 || month < 1 || month > 12 || monthDay < 1 || monthDay > 30) {
            throw new IllegalArgumentException("Illegal lunar date: " + year + "/" + month + "/" + monthDay);
        }
        int dayOffset2 = (f5617e[year - 1900] & 31) - 1;
        if (((f5617e[year - 1900] & 96) >> 5) == 2) {
            dayOffset2 += 31;
        }
        for (int i = 1; i < month; i++) {
            if ((f5617e[year - 1900] & (524288 >> (i - 1))) == 0) {
                dayOffset += 29;
            } else {
                dayOffset += 30;
            }
        }
        int dayOffset3 = dayOffset + monthDay;
        int leapMonth = (f5617e[year - 1900] & 15728640) >> 20;
        if (leapMonth != 0 && (month > leapMonth || (month == leapMonth && isLeapMonth))) {
            dayOffset3 = (f5617e[year + -1900] & (524288 >> (month + -1))) == 0 ? dayOffset3 + 29 : dayOffset3 + 30;
        }
        if (dayOffset3 > 366 || (year % 4 != 0 && dayOffset3 > 365)) {
            year++;
            if (year % 4 == 1) {
                dayOffset3 -= 366;
            } else {
                dayOffset3 -= 365;
            }
        }
        int[] solarInfo = new int[3];
        int i2 = 1;
        while (true) {
            if (i2 >= 13) {
                break;
            }
            int iPos = f5616d[i2];
            if (year % 4 == 0 && i2 > 2) {
                iPos++;
            }
            if (year % 4 == 0 && i2 == 2 && iPos + 1 == dayOffset3) {
                solarInfo[1] = i2;
                solarInfo[2] = dayOffset3 - 31;
                break;
            } else if (iPos >= dayOffset3) {
                solarInfo[1] = i2;
                int iPos2 = f5616d[i2 - 1];
                if (year % 4 == 0 && i2 > 2) {
                    iPos2++;
                }
                if (dayOffset3 > iPos2) {
                    solarInfo[2] = dayOffset3 - iPos2;
                } else if (dayOffset3 != iPos2) {
                    solarInfo[2] = dayOffset3;
                } else if (year % 4 == 0 && i2 == 2) {
                    solarInfo[2] = (f5616d[i2] - f5616d[i2 - 1]) + 1;
                } else {
                    solarInfo[2] = f5616d[i2] - f5616d[i2 - 1];
                }
            } else {
                i2++;
            }
        }
        solarInfo[0] = year;
        return solarInfo;
    }

    public static Calendar lunarToSolarCalendar(int year, int month, int monthDay, boolean isLeapMonth) {
        int[] lunarData = lunarToSolar(year, month, monthDay, isLeapMonth);
        Calendar solarCalendar = Calendar.getInstance();
        solarCalendar.set(lunarData[0], lunarData[1] - 1, lunarData[2]);
        return solarCalendar;
    }

    public static final int[] solarToLunar(int year, int month, int monthDay) {
        int[] lunarDate = new int[4];
        int offset = calcDayOffset(new GregorianCalendar(m2596a(year), month - 1, monthDay).getTime(), new GregorianCalendar(1900, 0, 31).getTime());
        int daysOfYear = 0;
        int iYear = 1900;
        while (iYear <= 2099 && offset > 0) {
            daysOfYear = m2599c(iYear);
            offset -= daysOfYear;
            iYear++;
        }
        if (offset < 0) {
            offset += daysOfYear;
            iYear--;
        }
        lunarDate[0] = iYear;
        int leapMonth = leapMonth(iYear);
        boolean isLeap = false;
        int daysOfMonth = 0;
        int iMonth = 1;
        while (iMonth <= 13 && offset >= 0) {
            if (leapMonth <= 0 || iMonth != leapMonth + 1 || isLeap) {
                isLeap = false;
            } else {
                iMonth--;
                isLeap = true;
            }
            daysOfMonth = daysInMonth(iYear, iMonth, isLeap);
            offset -= daysOfMonth;
            if (isLeap && iMonth == leapMonth + 1) {
                isLeap = false;
            }
            iMonth++;
        }
        if (iMonth > 13) {
            iMonth = 13;
        }
        if (offset < 0) {
            iMonth--;
            offset += daysOfMonth;
        }
        lunarDate[1] = m2598b(iMonth);
        lunarDate[2] = offset + 1;
        lunarDate[3] = isLeap ? 1 : 0;
        return lunarDate;
    }

    public static int calcDayOffset(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(6);
        int day2 = cal2.get(6);
        int year1 = cal1.get(1);
        int year2 = cal2.get(1);
        if (year1 == year2) {
            return day1 - day2;
        }
        int timeDistance = 0;
        for (int i = year2; i < year1; i++) {
            if ((i % 4 != 0 || i % 100 == 0) && i % 400 != 0) {
                timeDistance += 365;
            } else {
                timeDistance += 366;
            }
        }
        return (day1 - day2) + timeDistance;
    }

    public static int[] solarToLunar(Calendar solarCalendar) {
        return solarToLunar(m2596a(solarCalendar.get(1)), solarCalendar.get(2) + 1, solarCalendar.get(5));
    }

    public static final int daysInMonth(int year, int month) {
        return daysInMonth(year, month, false);
    }

    public static int daysInMonth(int year, int month, boolean leap) {
        int year2 = m2596a(year);
        int leapMonth = leapMonth(year2);
        int offset = 0;
        if (leapMonth != 0 && month > leapMonth) {
            offset = 1;
        }
        if (!leap) {
            return m2597a(year2, month + offset);
        }
        if (leapMonth == 0 || leapMonth != month) {
            return 0;
        }
        return m2597a(year2, month + 1);
    }

    /* renamed from: a */
    private static int m2596a(int year) {
        if (year > 2099) {
            year = 2099;
        }
        if (year < 1900) {
            return 1900;
        }
        return year;
    }

    /* renamed from: b */
    private static int m2598b(int month) {
        if (month > 12) {
            month = 12;
        }
        if (month < 1) {
            return 1;
        }
        return month;
    }

    /* renamed from: c */
    private static int m2599c(int year) {
        int year2 = m2596a(year);
        int sum = 348;
        if (leapMonth(year2) != 0) {
            sum = 377;
        }
        int monthInfo = f5617e[year2 - 1900] & 1048448;
        for (int i = 524288; i > 7; i >>= 1) {
            if ((monthInfo & i) != 0) {
                sum++;
            }
        }
        return sum;
    }

    /* renamed from: a */
    private static int m2597a(int year, int month) {
        if ((f5617e[m2596a(year) - 1900] & (1048576 >> month)) == 0) {
            return 29;
        }
        return 30;
    }

    public static int leapMonth(int year) {
        return (f5617e[m2596a(year) - 1900] & 15728640) >> 20;
    }

}
