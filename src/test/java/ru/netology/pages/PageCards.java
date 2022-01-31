package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.Data;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PageCards {
    private final SelenideElement cardNumberField = $(byText("Номер карты")).parent().$(".input__control");
    private final SelenideElement cardMonthField = $(byText("Месяц")).parent().$(".input__control");
    private final SelenideElement cardYearField = $(byText("Год")).parent().$(".input__control");
    private final SelenideElement cardOwnerField = $(byText("Владелец")).parent().$(".input__control");
    private final SelenideElement cardCVVField = $(byText("CVC/CVV")).parent().$(".input__control");
    private final SelenideElement continueButton = $(byText("Продолжить"));
    private final SelenideElement errorCardMessage = $(byText("Номер карты")).parent().$(".input__sub");
    private final SelenideElement errorMonthMessage = $(byText("Месяц")).parent().$(".input__sub");
    private final SelenideElement errorYearMessage = $(byText("Год")).parent().$(".input__sub");
    private final SelenideElement errorOwnerMessage = $(byText("Владелец")).parent().$(".input__sub");
    private final SelenideElement errorCVVMessage = $(byText("CVC/CVV")).parent().$(".input__sub");
    private final SelenideElement errorExpiredCardMessage = $(byText("Истёк срок действия карты"));

    public void fillForm(Data.CardInfo cardInfo) {
        cardNumberField.setValue(cardInfo.getCardNumber());
        cardMonthField.setValue(cardInfo.getCardMonth());
        cardYearField.setValue(cardInfo.getCardYear());
        cardOwnerField.setValue(cardInfo.getCardOwner());
        cardCVVField.setValue(cardInfo.getCardCVV());
        continueButton.click();
    }

    public void cardNumberErrorCheck () {

        errorCardMessage.shouldBe(visible);
    }

    public void cardDateErrorCheck () {

        errorExpiredCardMessage.shouldBe(visible);
    }

    public void ownerErrorCheck ()
    {
        errorOwnerMessage.shouldBe(visible);
    }

    public void CVVErrorCheck ()
    {
        errorCVVMessage.shouldBe(visible);
    }

    public void shouldGetThreeDigits () {

        assertNotEquals(Data.randomNumber, cardCVVField.getValue());
    }

    public void sendEmptyForm () {
        continueButton.click();
        errorCardMessage.shouldBe(visible);
        errorMonthMessage.shouldBe(visible);
        errorYearMessage.shouldBe(visible);
        errorOwnerMessage.shouldBe(visible);
        errorCVVMessage.shouldBe(visible);
    }
}
