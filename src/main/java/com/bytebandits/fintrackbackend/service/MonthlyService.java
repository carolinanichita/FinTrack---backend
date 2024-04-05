package com.bytebandits.fintrackbackend.service;

import com.bytebandits.fintrackbackend.dto.MonthlyData;
import com.bytebandits.fintrackbackend.dto.WeekData;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class MonthlyService {

    public List<WeekData> getWeeksOfMonth(int year, int month) {
        var results = new ArrayList<WeekData>();
        var yearMonth = Year.of(year).atMonth(month);
        var firstDay = yearMonth.atDay(1);
        var lastDay = yearMonth.atEndOfMonth();
        int order = 0;
        var currentStartDay = firstDay;
        var currentEndDay = firstDay;

        while (currentEndDay.getDayOfWeek() != DayOfWeek.SUNDAY){
            currentEndDay = currentEndDay.plusDays(1);

        }
        while (currentStartDay.getMonth().getValue() == month){
            order++;
            results.add(new WeekData(currentStartDay,currentEndDay,order));
            currentStartDay = currentEndDay.plusDays(1);
            currentEndDay = currentStartDay.plusDays(6);
            if(lastDay.isBefore(currentEndDay)){
                currentEndDay=lastDay;
            }

        }

        return results;

    }

    public List<MonthlyData> getMonthsOfYear(int year){
        int maxMonth=12;
        return IntStream.rangeClosed(1,maxMonth)
                .mapToObj(month -> new MonthlyData(month, getWeeksOfMonth(year,month)))
                .toList();

    }

}
