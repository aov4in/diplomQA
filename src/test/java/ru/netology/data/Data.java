package ru.netology.data;

import lombok.Value;

public class Data {

    //"Валидные данные карты"
    public static CardInfo getValidCard() {
        return new CardInfo("4444 4444 4444 4441", "12", "23", "IVAN PETROV", "321");
    }

    //"Не валидные данные карты"
    public static CardInfo getDeclinedCard() {
        return new CardInfo("4444 4444 4444 4442", "12", "23", "IVAN PETROV", "321");
    }

    // "Несуществующая карта"
    public static CardInfo getNotExistedCard() {
        return new CardInfo("4444 4444 4444 4449", "12", "23", "IVAN PETROV", "321");
    }

    // "Недействительная карта"
    public static CardInfo getNotValidCard() {
        return new CardInfo("4444 4444 4444 444", "12", "23", "IVAN PETROV", "321");
    }

    // "Неверно указан срок действия карты. Не верно указан месяц"
    public static CardInfo getExpiredMonthCard() {
        return new CardInfo("4444 4444 4444 4441", "13", "23", "IVAN PETROV", "321");
    }

    // "Поле обязательно для заполнения. Не указан год"
    public static CardInfo getExpiredYearCard() {
        return new CardInfo("4444 4444 4444 4441", "12", "", "IVAN PETROV", "321");
    }

    // "Истёк срок действия карты. Больше года"
    public static CardInfo getExceedYearCard() {
        return new CardInfo("4444 4444 4444 4441", "12", "20", "IVAN PETROV", "321");
    }

    // "Поле обязательно для заполнения. Не указан номер карты"
    public static CardInfo getWithoutNumberCard() {
        return new CardInfo(" ", "12", "23", "IVAN PETROV", "321");
    }

    //"Срок действия карты менее одного года"
    public static CardInfo getCardDateLessOneYear() {
        return new CardInfo("4444 4444 4444 4441", "12", "22", "IVAN PETROV", "321");
    }

    // "Указан нулевой месяц"
    public static CardInfo getNullMonthCard() {
        return new CardInfo("4444 4444 4444 4441", "00", "23", "IVAN PETROV", "321");
    }

    // "Указан не корректный месяц"
    public static CardInfo getIncorrectMonthCard() {
        return new CardInfo("4444 4444 4444 4441", "13", "23", "IVAN PETROV", "321");
    }

    // "Поле обязательно для заполнения. Не заполнены данные карты"
    public static CardInfo getEmptyFieldCard() {
        return new CardInfo("", "", "", "", "");
    }

    // "Поле Владелец заполнено на русском"
    public static CardInfo getRusNameOwnerCard() {
        return new CardInfo("4444 4444 4444 4441", "12", "23", "ИВАН ПЕТРОВ", "321");
    }

    // "Не корректно заполнено поле Владелец"
    public static CardInfo getNotValidName() {
        return new CardInfo("4444 4444 4444 4441", "12", "23", "123@#$%^", "321");
    }

    // "Не корректно заполнено поле CVC/CVV"
    public static CardInfo getNotValidCVCCard() {
        return new CardInfo("4444 4444 4444 4441", "12", "23", "IVAN PETROV", "31");
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
