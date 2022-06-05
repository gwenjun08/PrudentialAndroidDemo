package com.dudu.prudential.utils;

import com.dudu.framework.utils.AppUtil;
import com.dudu.prudential.R;

import java.util.Calendar;
import java.util.Objects;

public class DateUtil {

    public static String getWeekDayByIndex(int index) {
        int realIndex = index % 7;
        try {
            if (realIndex == 0) {
                return Objects.requireNonNull(AppUtil.getAppContext()).getResources().getString(R.string.saturday);
            } else if (realIndex == 1) {
                return Objects.requireNonNull(AppUtil.getAppContext()).getResources().getString(R.string.sunday);
            } else if (realIndex == 2) {
                return Objects.requireNonNull(AppUtil.getAppContext()).getResources().getString(R.string.monday);
            } else if (realIndex == 3) {
                return Objects.requireNonNull(AppUtil.getAppContext()).getResources().getString(R.string.tuesday);
            } else if (realIndex == 4) {
                return Objects.requireNonNull(AppUtil.getAppContext()).getResources().getString(R.string.wednesday);
            } else if (realIndex == 5) {
                return Objects.requireNonNull(AppUtil.getAppContext()).getResources().getString(R.string.thursday);
            } else if (realIndex == 6) {
                return Objects.requireNonNull(AppUtil.getAppContext()).getResources().getString(R.string.friday);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getCurWeekDay() {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        try {
            switch (day) {
                case Calendar.SUNDAY:
                    return Objects.requireNonNull(AppUtil.getAppContext()).getResources().getString(R.string.sunday);
                case Calendar.MONDAY:
                    return Objects.requireNonNull(AppUtil.getAppContext()).getResources().getString(R.string.monday);
                case Calendar.TUESDAY:
                    return Objects.requireNonNull(AppUtil.getAppContext()).getResources().getString(R.string.tuesday);
                case Calendar.WEDNESDAY:
                    return Objects.requireNonNull(AppUtil.getAppContext()).getResources().getString(R.string.wednesday);
                case Calendar.THURSDAY:
                    return Objects.requireNonNull(AppUtil.getAppContext()).getResources().getString(R.string.thursday);
                case Calendar.FRIDAY:
                    return Objects.requireNonNull(AppUtil.getAppContext()).getResources().getString(R.string.friday);
                case Calendar.SATURDAY:
                    return Objects.requireNonNull(AppUtil.getAppContext()).getResources().getString(R.string.saturday);
                default:
                    return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
