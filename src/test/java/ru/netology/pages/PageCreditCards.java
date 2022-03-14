package ru.netology.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.Data;
import ru.netology.data.Data.CardInfo;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


public class PageCreditCards {
    private final SelenideElement heading = $$("h3").find(exactText("Кредит по данным карты"));
    private final SelenideElement cardNumberField = $(byText("Номер карты")).parent().$(".input__control"); //$("[placeholder=\"0000 0000 0000 0000\"]");
    private final SelenideElement cardMonthField = $(byText("Месяц")).parent().$(".input__control");
    private final SelenideElement cardYearField = $(byText("Год")).parent().$(".input__control");
    private final SelenideElement cardOwnerField = $(byText("Владелец")).parent().$(".input__control");
    private final SelenideElement cardCVVField = $(byText("CVC/CVV")).parent().$(".input__control");
    private final SelenideElement approvedOperation = $$("[class=\"notification__content\"]").find(text("Операция одобрена Банком."));
    private final SelenideElement errorOperation = $$("[class=\"notification__content\"]").find(text("Ошибка! Банк отказал в проведении операции."));
    private final SelenideElement wrongFormatError = $(byText("Неверный формат"));
    private final ElementsCollection wrongFormatFourError = $$(byText("Неверный формат"));
    private final SelenideElement validityError = $(byText("Неверно указан срок действия карты"));
    private final SelenideElement cardExpiredError = $(byText("Истёк срок действия карты"));
    private final SelenideElement fieldRequiredError = $(byText("Поле обязательно для заполнения"));
    private final SelenideElement cancelSuccessField = $$("[class=\"icon-button__text\"]").first();
    private final SelenideElement continueButton = $$("button").find(exactText("Продолжить"));

    public PageCreditCards() {
        heading.shouldBe(visible);
    }
    public void fillForm(CardInfo cardInfo) {
        cardNumberField.setValue(cardInfo.getCardNumber());
        cardMonthField.setValue(cardInfo.getCardMonth());
        cardYearField.setValue(cardInfo.getCardYear());
        cardOwnerField.setValue(cardInfo.getCardOwner());
        cardCVVField.setValue(cardInfo.getCardCVV());
        continueButton.click();
    }
    public void approvedOperationBank () {
        approvedOperation.waitUntil(visible, 10000);
        cancelSuccessField.click();
    }
    public void refusedOperationBank () {
        errorOperation.waitUntil(visible, 10000);
    }

    public void incorrectNotificationFormat() {
        wrongFormatError.waitUntil(visible, 10000);
    }

    public void notificationValidityErrorVisible() {
        validityError.waitUntil(visible, 10000);
    }

    public void notificationCardExpiredVisibleError() {
        cardExpiredError.waitUntil(visible, 10000);
    }

    public void notificationRequiredFieldVisible() {
        fieldRequiredError.waitUntil(visible, 10000);
    }

    public void notificationDisplayedInvalidFormat() {
        wrongFormatFourError.shouldHaveSize(4);
        fieldRequiredError.waitUntil(visible, 10000);
    }

}
