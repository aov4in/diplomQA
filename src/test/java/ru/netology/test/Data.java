package ru.netology.test;

import lombok.Value;

public class Data {
    private Data() {}

    @Value
    public static class MonthAndYear {
        String validMonth;
        String validYear;
        String invalidMonth;
        String invalidYear;
    }

    public static MonthAndYear getMonthAndYear() {
        return new MonthAndYear("10", "22", "19", "30");
    }

    @Value
    public static class ListCards {
        String card1;
        String card2;
    }

    public static ListCards getListCards() {
        return new ListCards("4444 4444 4444 4441", "4444 4444 4444 4442");
    }
}
