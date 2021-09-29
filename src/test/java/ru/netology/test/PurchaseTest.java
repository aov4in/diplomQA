package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PurchaseTest {

    private static String link;
    private int countBefore;
    private int countAfter;
    private static Page elements;
    private static Data.MonthAndYear monthAndYear;
    private static Data.ListCards listCards;

    @BeforeAll
    static void init() {
        SqlUtils.dbConnect();
        link = System.getProperty("url");
        elements = new Page();
        monthAndYear = Data.getMonthAndYear();
        listCards = Data.getListCards();
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void close() {
        SqlUtils.dbCloseConnect();
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Проверка отображения ошибки некорректного формата ввода в меню купить")
    void shouldReturnIncorrectFormatError() {
        countBefore = SqlUtils.countPayment();
        open(link);
        elements.buyButtonClick()
                .continueButtonClick()
                .allFieldsVisible();
        countAfter = SqlUtils.countPayment();
        assertEquals(countBefore, countAfter);
    }

    @Test
    @DisplayName("Проверка отображения ошибки некорректного формата ввода в меню купить в кредит")
    void shouldReturnIncorrectFormatError_credit() {
        countBefore = SqlUtils.countCredit();
        open(link);
        elements.buyButtonClick()
                .continueButtonClick()
                .allFieldsVisible();
        countAfter = SqlUtils.countCredit();
        assertEquals(countBefore, countAfter);
    }

    @Test
    @DisplayName("Проверка отображения подтверждения в проведении операции в меню купить")
    void shouldReturnSuccessNotification() {
        countBefore = SqlUtils.countApprovedPayment();
        open(link);
        elements.buyButtonClick()
                .cardOneInput(listCards)
                .validMonthAndYearInput(monthAndYear)
                .fakerNameAndCvcInput()
                .continueButtonClick()
                .successNotificationVisible();
        countAfter = SqlUtils.countApprovedPayment();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    @DisplayName("Проверка отображения отказа в проведении операции в меню купить")
    void shouldReturnErrorNotification() {
        countBefore = SqlUtils.countDeclinedPayment();
        open(link);
        elements.buyButtonClick()
                .cardTwoInput(listCards)
                .validMonthAndYearInput(monthAndYear)
                .fakerNameAndCvcInput()
                .continueButtonClick()
                .errorNotificationVisible();
        countAfter = SqlUtils.countDeclinedPayment();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    @DisplayName("Проверка отображения отказа в проведении операции со случайной картой в меню купить")
    void shouldReturnErrorNotification_randomCard() {
        countBefore = SqlUtils.countDeclinedPayment();
        open(link);
        elements.buyButtonClick()
                .fakerCardInput()
                .validMonthAndYearInput(monthAndYear)
                .fakerNameAndCvcInput()
                .continueButtonClick()
                .errorNotificationVisible();
        countAfter = SqlUtils.countDeclinedPayment();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    @DisplayName("Проверка отображения ошибки валидности полей в меню купить")
    void shouldReturnErrorValidity() {
        countBefore = SqlUtils.countPayment();
        open(link);
        elements.buyButtonClick()
                .cardTwoInput(listCards)
                .invalidMonthAndYearInput(monthAndYear)
                .fakerNameAndCvcInput()
                .continueButtonClick()
                .monthAndYearError();
        countAfter = SqlUtils.countPayment();
        assertEquals(countBefore, countAfter);
    }

    @Test
    @DisplayName("Проверка отображения подтверждения в проведении операции в меню купить в кредит")
    void shouldReturnSuccessNotification_credit() {
        countBefore = SqlUtils.countApprovedCredit();
        open(link);
        elements.creditBuyButtonClick()
                .cardOneInput(listCards)
                .validMonthAndYearInput(monthAndYear)
                .fakerNameAndCvcInput()
                .continueButtonClick()
                .successNotificationVisible();
        countAfter = SqlUtils.countApprovedCredit();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    @DisplayName("Проверка отображения отказа в проведении операции в меню купить в кредит")
    void shouldReturnErrorNotification_credit() {
        countBefore = SqlUtils.countDeclinedCredit();
        open(link);
        elements.creditBuyButtonClick()
                .cardTwoInput(listCards)
                .validMonthAndYearInput(monthAndYear)
                .fakerNameAndCvcInput()
                .continueButtonClick()
                .errorNotificationVisible();
        countAfter = SqlUtils.countDeclinedCredit();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    @DisplayName("Проверка отображения отказа в проведении операции со случайной картой в меню купить в кредит")
    void shouldReturnErrorNotification_randomCard_credit(){
        countBefore = SqlUtils.countDeclinedCredit();
        open(link);
        elements.creditBuyButtonClick()
                .fakerCardInput()
                .validMonthAndYearInput(monthAndYear)
                .fakerNameAndCvcInput()
                .continueButtonClick()
                .errorNotificationVisible();
        countAfter = SqlUtils.countDeclinedCredit();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    @DisplayName("Проверка отображения ошибки валидности полей в меню купить в кредит")
    void shouldReturnErrorValidity_credit(){
        countBefore = SqlUtils.countCredit();
        open(link);
        elements.creditBuyButtonClick()
                .cardTwoInput(listCards)
                .invalidMonthAndYearInput(monthAndYear)
                .fakerNameAndCvcInput()
                .continueButtonClick()
                .monthAndYearError();
        countAfter = SqlUtils.countCredit();
        assertEquals(countBefore, countAfter);
    }

}
