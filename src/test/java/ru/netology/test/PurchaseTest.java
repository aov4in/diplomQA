package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.Data;
import ru.netology.data.SqlUtils;
import ru.netology.pages.Page;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PurchaseTest {
    /*DBTests*/
    @BeforeAll
    static void setUpAll() {

        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() throws SQLException {
        SelenideLogger.removeListener("allure");
        SqlUtils.cleanDB();
    }
    

    @BeforeEach
    public void openSource() {

        open("http://localhost:8080");
    }

    @Test
    @DisplayName("Payment card info can be sent and saved by database")
    void paymentCardInfoCanBeSentAndSavedByDatabase () throws SQLException {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        val cardInfo = Data.getCardInfoSample();
        debitCard.fillForm(cardInfo);
        mainPage.errorTransaction();
        assertEquals(SqlUtils.getCreatedOrderStatus(), SqlUtils.getCreatedPaymentStatus());
    }

    @Test
    @DisplayName("Credit card info can be sent and saved by database")
    void creditCardInfoCanBeSentAndSavedByDatabase () throws SQLException {
        val mainPage = new Page();
        val creditCard = mainPage.requestCredit();
        val cardInfo = Data.getCardInfoSample();
        creditCard.fillForm(cardInfo);
        mainPage.errorTransaction();
        assertEquals(SqlUtils.getCreatedOrderStatus(), SqlUtils.getCreatedRequestStatus());
    }

    @Test
    @DisplayName("Payment with first given card should be approved and saved by database")
    void paymentWithFirstGivenCardShouldBeApprovedAndSavedByDatabase () throws SQLException {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        val cardInfo = Data.getFirstGivenCard();
        debitCard.fillForm(cardInfo);
        mainPage.successfullyTransaction();
        assertEquals(Data.successStatus, SqlUtils.getPaymentStatus());
    }

    @Test
    @DisplayName("Payment with second given card should be declined and saved by database")
    void paymentWithSecondGivenCardShouldBeDeclinedAndSavedByDatabase () throws SQLException {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        val cardInfo = Data.getSecondGivenCard();
        debitCard.fillForm(cardInfo);
        mainPage.errorTransaction();
        assertEquals(Data.errorStatus, SqlUtils.getPaymentStatus());
    }

    @Test
    @DisplayName("Credit request with first given card should be approved and saved by database")
    void creditRequestWithFirstGivenCardShouldBeApprovedAndSavedByDatabase () throws SQLException {
        val mainPage = new Page();
        val creditCard = mainPage.requestCredit();
        val cardInfo = Data.getFirstGivenCard();
        creditCard.fillForm(cardInfo);
        mainPage.successfullyTransaction();
        assertEquals(Data.successStatus, SqlUtils.getRequestStatus());
    }

    @Test
    @DisplayName("Credit request with second given card should be declined and saved by database")
    void creditRequestWithSecondGivenCardShouldBeDeclinedAndSavedByDatabase () throws SQLException {
        val mainPage = new Page();
        val creditCard = mainPage.requestCredit();
        val cardInfo = Data.getSecondGivenCard();
        creditCard.fillForm(cardInfo);
        mainPage.errorTransaction();
        assertEquals(Data.errorStatus, SqlUtils.getRequestStatus());
        mainPage.errorTransaction();
    }

    /*PageTest*/
    
    @Test
    @DisplayName("Should show error message if card filled with characters")
    void shouldShowErrorMessageIfCardFilledWithCharacters () {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        val cardInfo = Data.getCharactersInsteadOfCardNumber();
        debitCard.fillForm(cardInfo);
        debitCard.cardNumberErrorCheck();
    }

    @Test
    @DisplayName("Should show error message if card filled with short number")
    void shouldShowErrorMessageIfCardFilledWithShortNumber() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        val cardInfo = Data.getCardWithShortNumber();
        debitCard.fillForm(cardInfo);
        debitCard.cardNumberErrorCheck();
    }

    @Test
    @DisplayName("Should show error message if card filled with symbols")
    void shouldShowErrorMessageIfCardFilledWithSymbols() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        val cardInfo = Data.getSymbolsInsteadOfCardNumber();
        debitCard.fillForm(cardInfo);
        debitCard.cardNumberErrorCheck();
    }

    @Test
    @DisplayName("Should show error message if card is expired")
    void shouldShowErrorMessageIfCardIsExpired() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        val cardInfo = Data.getExpiredCardInfo();
        debitCard.fillForm(cardInfo);
        debitCard.cardDateErrorCheck();
    }

    @Test
    @DisplayName("Should show error message if owner filled only with first name")
    void shouldShowErrorMessageIfOwnerFilledOnlyWithFirstName() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        val cardInfo = Data.getOnlyOwnerName();
        debitCard.fillForm(cardInfo);
        debitCard.ownerErrorCheck();
    }

    @Test
    @DisplayName("Should show error message if owner filled with name in russian")
    void shouldShowErrorMessageIfOwnerFilledWithNameInRussian() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        val cardInfo = Data.getRussianOwnerName();
        debitCard.fillForm(cardInfo);
        debitCard.ownerErrorCheck();
    }

    @Test
    @DisplayName("Should show error message if owner filled with number")
    void shouldShowErrorMessageIfOwnerFilledWithNumber() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        val cardInfo = Data.getNumberInsteadOfName();
        debitCard.fillForm(cardInfo);
        debitCard.ownerErrorCheck();
    }

    @Test
    @DisplayName("Should show error message if owner filled with symbols")
    void shouldShowErrorMessageIfOwnerFilledWithSymbols() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        val cardInfo = Data.getSymbolsInsteadOfName();
        debitCard.fillForm(cardInfo);
        debitCard.ownerErrorCheck();
    }

    @Test
    @DisplayName("Should show error message if owner filled with random characters")
    void shouldShowErrorMessageIfOwnerFilledWithRandomCharacters() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        val cardInfo = Data.getRandomCharactersInsteadOfName();
        debitCard.fillForm(cardInfo);
        debitCard.ownerErrorCheck();
    }

    @Test
    @DisplayName("Should show error message if CVV filled with symbols")
    void shouldShowErrorMessageIfCVVFilledWithSymbols() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        val cardInfo = Data.getSymbolsInsteadOfCVV();
        debitCard.fillForm(cardInfo);
        debitCard.CVVErrorCheck();
    }

    @Test
    @DisplayName("Should show error message if CVV filled with characters")
    void shouldShowErrorMessageIfCVVFilledWithCharacters() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        val cardInfo = Data.getCharactersInsteadOfCVV();
        debitCard.fillForm(cardInfo);
        debitCard.CVVErrorCheck();
    }

    @Test
    @DisplayName("CVV field should not accept more than three digits")
    void CVVFieldShouldNotAcceptMoreThanThreeDigits() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        val cardInfo = Data.getCVVLongerThanThree();
        debitCard.fillForm(cardInfo);
        debitCard.shouldGetThreeDigits();
    }

    @Test
    @DisplayName("Should show error message if CVV filled with 2 digits")
    void shouldShowErrorMessageIfCVVFilledWith2Digits() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        val cardInfo = Data.getCVVShorterThanThree();
        debitCard.fillForm(cardInfo);
        debitCard.CVVErrorCheck();
    }

    @Test
    @DisplayName("Should show error messages if empty payment form sent")
    void shouldShowErrorMessageIfEmptyPaymentFormSent() {
        val mainPage = new Page();
        val debitCard = mainPage.payWithDebitCard();
        debitCard.sendEmptyForm();
    }

    @Test
    @DisplayName("Should show error messages if empty credit request form sent")
    void shouldShowErrorMessageIfEmptyCreditRequestFormSent() {
        val mainPage = new Page();
        val creditCard = mainPage.requestCredit();
        creditCard.sendEmptyForm();
    }


}
