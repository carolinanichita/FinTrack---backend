package com.bytebandits.fintrackbackend;

import com.bytebandits.fintrackbackend.service.MonthlyService;
import com.bytebandits.fintrackbackend.dto.WeekData;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MonthlyDataTest {

    MonthlyService monthlyService = new MonthlyService();

    @Test
    void checktheCurrentMonthsWeek() {
        var actual= monthlyService.getWeeksOfMonth(2023,10);
        var expected = new ArrayList<WeekData>();
        expected.add(new WeekData(LocalDate.of(2023,10,1),LocalDate.of(2023,10,1),1));
        expected.add(new WeekData(LocalDate.of(2023,10,2),LocalDate.of(2023,10,8),2));
        expected.add(new WeekData(LocalDate.of(2023,10,9),LocalDate.of(2023,10,15),3));
        expected.add(new WeekData(LocalDate.of(2023,10,16),LocalDate.of(2023,10,22),4));
        expected.add(new WeekData(LocalDate.of(2023,10,23),LocalDate.of(2023,10,29),5));
        expected.add(new WeekData(LocalDate.of(2023,10,30),LocalDate.of(2023,10,31),6));

        assertEquals(expected,actual);
    }
}
