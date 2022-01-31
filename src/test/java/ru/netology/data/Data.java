package ru.netology.data;

import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Data {
        static Faker faker = new Faker(new Locale("ru"));
        public static String cardOwner = "IVAN PETROV";
        public static String cardOwnerRussian = "ИВАН ПЕТРОВ";
        public static String cardOwnerName = "IVAN";
        public static String firstGivenCardNumber = "4444 4444 4444 4441";
        public static String secondGivenCardNumber = "4444 4444 4444 4442";
        /*public static String cardNumberMaestro = "4276 5500 1111 2222 33";*/
        public static String randomNumber = "123789";
        public static String characters = "qwertyйцукен";
        public static String symbols = "/.&!:*@:#,";
        public static String errorStatus = "DECLINED";
        public static String successStatus = "APPROVED";

        private static String getCardNumber () {
            String cardNum = faker.finance().creditCard(CreditCardType.MASTERCARD);
            return cardNum.replaceAll("-", "");
        }

        private static String monthCalculating() {
            LocalDate date = LocalDate.now();
            DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");
            return date.format(monthFormatter);
        }

        private static int yearCalculating() {
            LocalDate cardDate = LocalDate.now();
            DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yy");
            return Integer.parseInt(cardDate.format(yearFormatter));
        }

        private static String getCVV () {
            int code = 100 + (int) (Math.random() * 900);
            return String.valueOf(code);
        }

        private static String getShortCardNumber () {
            String shortCardNum = faker.finance().creditCard(CreditCardType.AMERICAN_EXPRESS);
            return shortCardNum.replaceAll("-", "");
        }

        @Value
        public static class CardInfo {
            String cardNumber;
            String cardMonth;
            String cardYear;
            String cardOwner;
            String cardCVV;
        }

        public static CardInfo getCardInfoSample () {
            return new CardInfo(getCardNumber(), monthCalculating(), String.valueOf(yearCalculating()+2), cardOwner, getCVV());
        }

        public static CardInfo getFirstGivenCard () {
            return new CardInfo(firstGivenCardNumber, monthCalculating(), String.valueOf(yearCalculating()+2), cardOwner, getCVV());
        }

        public static CardInfo getSecondGivenCard () {
            return new CardInfo(secondGivenCardNumber, monthCalculating(), String.valueOf(yearCalculating()+2), cardOwner, getCVV());
        }

        public static CardInfo getCharactersInsteadOfCardNumber () {
            return new CardInfo(characters, monthCalculating(), String.valueOf(yearCalculating()+2), cardOwner, getCVV());
        }

        public static CardInfo getCardWithShortNumber () {
            return new CardInfo(getShortCardNumber(), monthCalculating(), String.valueOf(yearCalculating()+2), cardOwner, getCVV());
        }

        public static CardInfo getSymbolsInsteadOfCardNumber () {
            return new CardInfo(symbols, monthCalculating(), String.valueOf(yearCalculating()+2), cardOwner, getCVV());
        }

        public static CardInfo getExpiredCardInfo () {
            return new CardInfo(getCardNumber(), monthCalculating(), String.valueOf(yearCalculating()-4), cardOwner, getCVV());
        }

        public static CardInfo getOnlyOwnerName () {
            return new CardInfo(getCardNumber(), monthCalculating(), String.valueOf(yearCalculating()+2), cardOwnerName, getCVV());
        }

        public static CardInfo getRussianOwnerName () {
            return new CardInfo(getCardNumber(), monthCalculating(), String.valueOf(yearCalculating()+2), cardOwnerRussian, getCVV());
        }

        public static CardInfo getNumberInsteadOfName () {
            return new CardInfo(getCardNumber(), monthCalculating(), String.valueOf(yearCalculating()+2), randomNumber, getCVV());
        }

        public static CardInfo getSymbolsInsteadOfName () {
            return new CardInfo(getCardNumber(), monthCalculating(), String.valueOf(yearCalculating()+2), symbols, getCVV());
        }

        public static CardInfo getRandomCharactersInsteadOfName () {
            return new CardInfo(getCardNumber(), monthCalculating(), String.valueOf(yearCalculating()+2), characters, getCVV());
        }

        public static CardInfo getSymbolsInsteadOfCVV () {
            return new CardInfo(getCardNumber(), monthCalculating(), String.valueOf(yearCalculating()+2), cardOwner, symbols);
        }

        public static CardInfo getCharactersInsteadOfCVV () {
            return new CardInfo(getCardNumber(), monthCalculating(), String.valueOf(yearCalculating()+2), cardOwner, characters);
        }

        public static CardInfo getCVVLongerThanThree () {
            return new CardInfo(getCardNumber(), monthCalculating(), String.valueOf(yearCalculating()+2), cardOwner, randomNumber);
        }

        public static CardInfo getCVVShorterThanThree () {
            return new CardInfo(getCardNumber(), monthCalculating(), String.valueOf(yearCalculating()+2), cardOwner, "13");
        }
}
