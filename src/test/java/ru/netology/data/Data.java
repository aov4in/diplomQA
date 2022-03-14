package ru.netology.data;

import lombok.Value;

public class Data {

    public static CardInfo getValidCard() {
        return new CardInfo("4444 4444 4444 4441", "12", "23", "IVAN PETROV", "321");
    }

    public static CardInfo getDeclinedCard() {
        return new CardInfo("4444 4444 4444 4442", "12", "23", "IVAN PETROV", "321");
    }

    public static CardInfo getNotExistedCard() {
        return new CardInfo("4444 4444 4444 4449", "12", "23", "IVAN PETROV", "321");
    }

    public static CardInfo getNotValidCard() {
        return new CardInfo("4444 4444 4444 444", "12", "23", "IVAN PETROV", "321");
    }

    public static CardInfo getExpiredMonthCard() {
        return new CardInfo("4444 4444 4444 4441", "12", "23", "IVAN PETROV", "321");
    }

    public static CardInfo getExpiredYearCard() {
        return new CardInfo("4444 4444 4444 4441", "12", "23", "IVAN PETROV", "321");
    }

    public static CardInfo getExceedYearCard() {
        return new CardInfo("4444 4444 4444 4441", "12", "23", "IVAN PETROV", "321");
    }

    public static CardInfo getWithoutNumberCard() {
        return new CardInfo(" ", "12", "23", "IVAN PETROV", "321");
    }

    public static CardInfo getCardDateLessOneYear() {
        return new CardInfo("4444 4444 4444 4441", "01", "19", "IVAN PETROV", "321");
    }

    public static CardInfo getNullMonthCard() {
        return new CardInfo("4444 4444 4444 4441", "00", "23", "IVAN PETROV", "321");
    }

    public static CardInfo getIncorrectMonthCard() {
        return new CardInfo("4444 4444 4444 4441", "13", "23", "IVAN PETROV", "321");
    }

    public static CardInfo getEmptyFieldCard() {
        return new CardInfo("", "", "", "", "");
    }

    public static CardInfo getRusNameOwnerCard() {
        return new CardInfo("4444 4444 4444 4441", "12", "23", "ИВАН ПЕТРОВ", "321");
    }

    public static CardInfo getNotValidName() {
        return new CardInfo("4444 4444 4444 4441", "12", "23", "123@#$%^", "321");
    }

    public static CardInfo getNotValidCVCCard() {
        return new CardInfo("4444 4444 4444 4441", "13", "23", "IVAN PETROV", "31");
    }

    @Value
    public static class CardInfo {
        String cardNumber;
        String cardMonth;
        String cardYear;
        String cardOwner;
        String cardCVV;
    }
}
