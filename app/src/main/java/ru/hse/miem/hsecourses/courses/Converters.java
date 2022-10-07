package ru.hse.miem.hsecourses.courses;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static ArrayList<Integer> fromStringToIntArray(String value) {
        String[] strings = value.split(";");
        ArrayList<Integer> result = new ArrayList<>();

        for (String word : strings) {
            if(word.equals("null")){
                result.add(null);
            } else {
                result.add(Integer.parseInt(word));
            }
        }
        return result;
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Integer> list) {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i<list.size(); i++){
            result.append(list.get(i).toString());
            result.append(";");
        }
        return result.toString();
    }

    @TypeConverter
    public static Calendar toCalendar(Long l) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(l);
        return c;
    }

    @TypeConverter
    public static Long fromCalendar(Calendar c){
        return c == null ? null : c.getTime().getTime();
    }

    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }
}
