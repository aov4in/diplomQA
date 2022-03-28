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

public class DebitCardTest {

    @BeforeAll
    static void setUpAll() {

        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
        SqlUtils.cleanDB();
    }
    

    @BeforeEach
    public void openSource() {

        String url = System.getProperty("sut.url");
        open(url);
    }

    //"Валидные данные карты"
    @Test
    @DisplayName("DebitCard. The operation was successful, a record appeared with the status APPROVED")
    void shouldDebitCarddebitCardApproved () {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        debitCard.fillData(Data.getValidCard());
        debitCard.approvedOperationBank();
        assertEquals("APPROVED", SqlUtils.getPaymentStatus());
    }

    //"Не валидные данные карты"
    @Test
    @DisplayName("DebitCard. The operation was successful, a record appeared with the status DECLINED")
    void shouldDebitCarddebitCardDeclined () {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        debitCard.fillData(Data.getDeclinedCard());
        debitCard.refusedOperationBank();
        assertEquals("DECLINED", SqlUtils.getRequestStatus());
    }

    // "Несуществующая карта"
    @Test
    @DisplayName("DebitCard. Purchase with an invalid card. The operation was rejected by the bank. Missing entry in database")
    void payingWithNonExistingCreditCard () {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        debitCard.fillData(Data.getNotExistedCard());
        debitCard.refusedOperationBank();
        assertEquals("3", SqlUtils.getCreatedOrderStatus());
    }

    // "Недействительная карта"
    @Test
    @DisplayName("DebitCard. Invalid credit card format. No entry in database")
    void shouldInvalidCreditCardFormat() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        debitCard.fillData(Data.getNotValidCard());
        debitCard.incorrectNotificationFormat();
        assertEquals("5", SqlUtils.getCreatedOrderStatus());
    }

    // "Неверно указан срок действия карты. Не верно указан месяц"
    @Test
    @DisplayName("DebitCard. Invalid card expiration date. One month has expired. No entry in database")
    void shouldCreditCardInvalidDateMonth() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        debitCard.fillData(Data.getExpiredMonthCard());
        debitCard.notificationValidityErrorVisible();
        assertEquals("2", SqlUtils.getCreatedOrderStatus());
    }

    // "Поле обязательно для заполнения. Не указан год"
    @Test
    @DisplayName("DebitCard. Invalid card expiration date. One year has expired. No entry in database")
    void shouldCreditCardInvalidDateYear() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        debitCard.fillData(Data.getExpiredYearCard());
        debitCard.notificationRequiredFieldVisible();
        assertEquals("0", SqlUtils.getCreatedOrderStatus());
    }

    // "Истёк срок действия карты. Больше года"
    @Test
    @DisplayName("DebitCard. Invalid card expiration date. The card is valid for more than 5 years. No entry in database")
    void shouldCreditCardExceedYear() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        debitCard.fillData(Data.getExceedYearCard());
        debitCard.notificationCardExpiredVisibleError();
        assertEquals("5", SqlUtils.getCreatedOrderStatus());
    }

    // "Поле обязательно для заполнения. Не указан номер карты"
    @Test
    @DisplayName("DebitCard. The card number field is not filled. Required field. No entry in database")
    void shouldCreditCardEmptyNumber() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        debitCard.fillData(Data.getWithoutNumberCard());
        debitCard.notificationRequiredFieldVisible();
        assertEquals("0", SqlUtils.getCreatedOrderStatus());
    }

    // "Не верный формат. Указан нулевой месяц"
    @Test
    @DisplayName("DebitCard. Value \"00\" in the month field. Invalid format. No entry in database")
    void shouldCreditCardNullMonthValue() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        debitCard.fillData(Data.getNullMonthCard());
        debitCard.incorrectNotificationFormat();
        assertEquals("0", SqlUtils.getCreatedOrderStatus());
    }

    // "Неверно указан срок действия карты. Указан не корректный месяц"
    @Test
    @DisplayName("DebitCard. Not a valid month value. Invalid format. No entry in database")
    void shouldCreditCardNotExistedMonth() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        debitCard.fillData(Data.getIncorrectMonthCard());
        debitCard.notificationValidityErrorVisible();
        assertEquals("5", SqlUtils.getCreatedOrderStatus());
    }

    // "Поле обязательно для заполнения. Не заполнены данные карты"
    @Test
    @DisplayName("DebitCard. Purchase by credit card with empty fields. \"Invalid Format\" and \"Required Field\" Error Messages. No entry in database")
    void shouldCreditCardEmptyField() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        debitCard.fillData(Data.getEmptyFieldCard());
        debitCard.notificationDisplayedInvalidFormat();
        assertEquals("5", SqlUtils.getCreatedOrderStatus());
    }

    // "Не верный формат.Поле Владелец заполнено на русском"
    @Test
    @DisplayName("DebitCard. Invalid format, the Owner field is filled in Cyrillic. No entry in database")
    void shouldCreditCardRusNameOwner() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        debitCard.fillData(Data.getRusNameOwnerCard());
        debitCard.incorrectNotificationFormat();
        assertEquals("0", SqlUtils.getCreatedOrderStatus());
    }

    // "Не верный формат. Не корректно заполнено поле Владелец"
    @Test
    @DisplayName("DebitCard. The Owner field is filled with characters. Invalid format. No entry in database")
    void shouldCreditCardNotValidName() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        debitCard.fillData(Data.getNotValidName());
        debitCard.incorrectNotificationFormat();
        assertEquals("0", SqlUtils.getCreatedOrderStatus());
    }

    // "Не верный формат. Не корректно заполнено поле CVC/CVV"
    @Test
    @DisplayName("DebitCard. Not valid CVC / CVV values, Invalid format. No entry in database")
    void shouldCreditCardNotValidCVC() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        debitCard.fillData(Data.getNotValidCVCCard());
        debitCard.incorrectNotificationFormat();
        assertEquals("5", SqlUtils.getCreatedOrderStatus());
    }

    //"Срок действия карты менее одного года"
    @Test
    @DisplayName("DebitCard. Debit card expired")
    void shouldCreditCardExpired() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        debitCard.fillData(Data.getCardDateLessOneYear());
        debitCard.approvedOperationBank();
        assertEquals("2", SqlUtils.getCreatedOrderStatus());
    }
}
