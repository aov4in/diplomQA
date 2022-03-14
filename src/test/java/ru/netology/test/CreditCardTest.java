package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.Data;
import ru.netology.data.SqlUtils;
import ru.netology.pages.Page;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardTest {

    @BeforeEach
    public void openSource() {
        String url = System.getProperty("sut.url");
        open(url);
    }

    @AfterEach
    public void cleanDB() {
        SqlUtils.cleanDB();
    }


    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @Test
    @DisplayName("CreditCard. The operation was successful, a record appeared with the status APPROVED")
    void confirmPaymentWithValidCreditCard () {
        val mainPage = new Page();
        val payment = mainPage.requestCredit();
        payment.fillForm(Data.getValidCard());
        payment.approvedOperationBank();
        assertEquals("APPROVED", SqlUtils.getRequestStatus());
    }

    @Test
    @DisplayName("CreditCard. The operation was successful, a record appeared with the status DECLINED")
    void shouldCreditCardPaymentDeclined () {
        val mainPage = new Page();
        val payment = mainPage.requestCredit();
        payment.fillForm(Data.getDeclinedCard());
        payment.refusedOperationBank();
        assertEquals("DECLINED", SqlUtils.getRequestStatus());
    }

    @Test
    @DisplayName("CreditCard. Purchase with an invalid card. The operation was rejected by the bank. Missing entry in database")
    void payingWithNonExistingCreditCard () {
        val mainPage = new Page();
        val payment = mainPage.requestCredit();
        payment.fillForm(Data.getNotExistedCard());
        payment.refusedOperationBank();
        assertEquals("0", SqlUtils.getCreatedOrderStatus());
    }

    @Test
    @DisplayName("CreditCard. Invalid credit card format. No entry in database")
    void shouldInvalidCreditCardFormat() {
        val mainPage = new Page();
        val payment = mainPage.requestCredit();
        payment.fillForm(Data.getNotValidCard());
        payment.incorrectNotificationFormat();
        assertEquals("0", SqlUtils.getCreatedOrderStatus());
    }

    @Test
    @DisplayName("CreditCard. Invalid card expiration date. One month has expired. No entry in database")
    void shouldCreditCardInvalidDateMonth() {
        val mainPage = new Page();
        val payment = mainPage.requestCredit();
        payment.fillForm(Data.getExpiredMonthCard());
        payment.notificationValidityErrorVisible();
        assertEquals("0", SqlUtils.getCreatedOrderStatus());
    }

    @Test
    @DisplayName("CreditCard. Invalid card expiration date. One year has expired. No entry in database")
    void shouldCreditCardInvalidDateYear() {
        val mainPage = new Page();
        val payment = mainPage.requestCredit();
        payment.fillForm(Data.getExpiredYearCard());
        payment.notificationValidityErrorVisible();
        assertEquals("0", SqlUtils.getCreatedOrderStatus());
    }

    @Test
    @DisplayName("CreditCard. Invalid card expiration date. The card is valid for more than 5 years. No entry in database")
    void shouldCreditCardExceedYear() {
        val mainPage = new Page();
        val payment = mainPage.requestCredit();
        payment.fillForm(Data.getExceedYearCard());
        payment.notificationValidityErrorVisible();
        assertEquals("0", SqlUtils.getCreatedOrderStatus());
    }

    @Test
    @DisplayName("CreditCard. The card number field is not filled. Required field. No entry in database")
    void shouldCreditCardEmptyNumber() {
        val mainPage = new Page();
        val payment = mainPage.requestCredit();
        payment.fillForm(Data.getWithoutNumberCard());
        payment.notificationRequiredFieldVisible();
        assertEquals("0", SqlUtils.getCreatedOrderStatus());
    }

    @Test
    @DisplayName("CreditCard. Value \"00\" in the month field. Invalid format. No entry in database")
    void shouldCreditCardNullMonthValue() {
        val mainPage = new Page();
        val payment = mainPage.requestCredit();
        payment.fillForm(Data.getNullMonthCard());
        payment.incorrectNotificationFormat();
        assertEquals("0", SqlUtils.getCreatedOrderStatus());
    }

    @Test
    @DisplayName("CreditCard. Not a valid month value. Invalid format. No entry in database")
    void shouldCreditCardNotExistedMonth() {
        val mainPage = new Page();
        val payment = mainPage.requestCredit();
        payment.fillForm(Data.getIncorrectMonthCard());
        payment.notificationValidityErrorVisible();
        assertEquals("0", SqlUtils.getCreatedOrderStatus());
    }

    @Test
    @DisplayName("CreditCard. Purchase by credit card with empty fields. \"Invalid Format\" and \"Required Field\" Error Messages. No entry in database")
    void shouldCreditCardEmptyField() {
        val mainPage = new Page();
        val payment = mainPage.requestCredit();
        payment.fillForm(Data.getEmptyFieldCard());
        payment.notificationDisplayedInvalidFormat();
        assertEquals("0", SqlUtils.getCreatedOrderStatus());
    }

    @Test
    @DisplayName("CreditCard. Invalid format, the Owner field is filled in Cyrillic. No entry in database")
    void shouldCreditCardRusNameOwner() {
        val mainPage = new Page();
        val payment = mainPage.requestCredit();
        payment.fillForm(Data.getRusNameOwnerCard());
        payment.incorrectNotificationFormat();
        assertEquals("0", SqlUtils.getCreatedOrderStatus());
    }

    @Test
    @DisplayName("CreditCard. The Owner field is filled with characters. Invalid format. No entry in database")
    void shouldCreditCardNotValidName() {
        val mainPage = new Page();
        val payment = mainPage.requestCredit();
        payment.fillForm(Data.getNotValidName());
        payment.incorrectNotificationFormat();
        assertEquals("0", SqlUtils.getCreatedOrderStatus());
    }

    @Test
    @DisplayName("CreditCard. Not valid CVC / CVV values, Invalid format. No entry in database")
    void shouldCreditCardNotValidCVC() {
        val mainPage = new Page();
        val payment = mainPage.requestCredit();
        payment.fillForm(Data.getNotValidCVCCard());
        payment.incorrectNotificationFormat();
        assertEquals("0", SqlUtils.getCreatedOrderStatus());
    }

    @Test
    @DisplayName("CreditCard. Credit card expired")
    void shouldCreditCardExpired() {
        val mainPage = new Page();
        val payment = mainPage.requestCredit();
        payment.fillForm(Data.getCardDateLessOneYear());
        payment.notificationCardExpiredVisibleError();
        assertEquals("0", SqlUtils.getCreatedOrderStatus());
    }

}
