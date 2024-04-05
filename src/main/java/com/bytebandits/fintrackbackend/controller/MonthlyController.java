package com.bytebandits.fintrackbackend.controller;

import com.bytebandits.fintrackbackend.dto.MonthlyData;
import com.bytebandits.fintrackbackend.dto.MonthlyTotal;
import com.bytebandits.fintrackbackend.dto.WeekData;
import com.bytebandits.fintrackbackend.dto.WeeklyTotal;
import com.bytebandits.fintrackbackend.service.MonthlyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/data")
public class MonthlyController {

    MonthlyService monthlyService;

    public MonthlyController(MonthlyService monthlyService) {
        this.monthlyService = monthlyService;
    }

    @GetMapping("/monthly")
    public List<MonthlyTotal> getMonthlyData(@RequestParam int year) {
       return monthlyService.getMonthsOfYear(year)
               .stream()
               .map(monthlyData -> new MonthlyTotal(
                        Month.of(monthlyData.month()).getDisplayName(TextStyle.FULL, Locale.US),
                        "€0",
                        "€0",
                        monthlyData.weeks()
                                .stream()
                                .map(weekData -> new WeeklyTotal(
                                        weekData.start().toString(),
                                        weekData.end().toString(),
                                        "0€",
                                        "0€"))
                                .toList()
                        )
                ).toList();

    }

}
