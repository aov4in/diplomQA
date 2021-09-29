package ru.netology.test;

import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import lombok.val;

import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;

public class Page {

    private final SelenideElement buyButton = $(byText("Купить"));
    private final SelenideElement creditBuyButton = $(byText("Купить в кредит"));
    private final SelenideElement cardInput = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthInput = $("[placeholder='08']");
    private final SelenideElement monthError = $$("[class=input__inner]").findBy(text("Месяц"))
            .$(withText("Неверно указан срок действия карты"));
    private final SelenideElement yearInput = $("[placeholder='22']");
    private final SelenideElement yearError = $$("[class=input__inner]").findBy(text("Год"))
            .$(withText("Неверно указан срок действия карты"));
    private final SelenideElement nameInput = $$("[class=input__inner]").findBy(text("Владелец"))
            .$("[class=input__control]");
    private final SelenideElement cvcInput = $("[placeholder='999']");
    private final SelenideElement continueButton = $(byText("Продолжить"));
    private final SelenideElement errorNotification = $(withText("Банк отказал в проведении операции"));
    private final SelenideElement successNotification = $(withText("Операция одобрена Банком"));
    private final SelenideElement cardIncorrect = $$("[class=input__inner]").findBy(text("Номер карты"))
            .$(withText("Неверный формат"));
    private final SelenideElement monthIncorrect = $$("[class=input__inner]").findBy(text("Месяц"))
            .$(withText("Неверный формат"));
    private final SelenideElement yearIncorrect = $$("[class=input__inner]").findBy(text("Год"))
            .$(withText("Неверный формат"));
    private final SelenideElement nameIncorrect = $$("[class=input__inner]").findBy(text("Владелец"))
            .$(withText("Поле обязательно для заполнения"));
    private final SelenideElement cvcIncorrect = $$("[class=input__inner]").findBy(text("CVC/CVV"))
            .$(withText("Неверный формат"));

    @Step("Проверка видимости всех полей ввода")
    public void allFieldsVisible() {
        cardIncorrect.shouldBe(visible);
        monthIncorrect.shouldBe(visible);
        yearIncorrect.shouldBe(visible);
        nameIncorrect.shouldBe(visible);
        cvcIncorrect.shouldBe(visible);
        errorNotification.shouldNotBe(visible);
        successNotification.shouldNotBe(visible);
    }

    @Step("Ввод валидных месяца и года")
    public Page validMonthAndYearInput(Data.MonthAndYear monthAndYear) {
        monthInput.setValue(monthAndYear.getValidMonth());
        yearInput.setValue(monthAndYear.getValidYear());
        return this;
    }

    @Step("Ввод инвалидных месяца и года")
    public Page invalidMonthAndYearInput(Data.MonthAndYear monthAndYear) {
        monthInput.setValue(monthAndYear.getInvalidMonth());
        yearInput.setValue(monthAndYear.getInvalidYear());
        return this;
    }

    @Step("Нажать кнопку 'Продолжить'")
    public Page continueButtonClick() {
        continueButton.click();
        return this;
    }

    @Step("Проверка отображения выпадающего окна подтверждения операции")
    public void successNotificationVisible() {
        successNotification.shouldBe(visible, ofSeconds(10000));
        errorNotification.shouldNotBe(visible);
    }

    @Step("Проверка отображения выпадающего окна отказа операции")
    public void errorNotificationVisible() {
        errorNotification.shouldBe(visible, ofSeconds(10000));
        successNotification.shouldNotBe(visible);
    }

    @Step("Проверка видимости ошибки корректного ввода в поле месяц и год")
    public void monthAndYearError() {
        monthError.shouldBe(visible);
        yearError.shouldBe(visible);
        errorNotification.shouldNotBe(visible);
        successNotification.shouldNotBe(visible);
    }

    @Step("Ввод случайного имени и CVC/CVV")
    public Page fakerNameAndCvcInput() {
        val faker = new Faker(new Locale("ru"));
        nameInput.setValue(faker.name().fullName());
        cvcInput.setValue(faker.numerify("###"));
        return this;
    }

    @Step("Нажать кнопку 'Купить'")
    public Page buyButtonClick() {
        buyButton.shouldBe(visible).click();
        return this;
    }

    @Step("Нажать кнопку 'Купить в кредит'")
    public Page creditBuyButtonClick() {
        creditBuyButton.shouldBe(visible).click();
        return this;
    }

    @Step("Ввод указанной в тестах карты №1")
    public Page cardOneInput(Data.ListCards listCards) {
        cardInput.shouldBe(visible, ofSeconds(10000)).setValue(listCards.getCard1());
        return this;
    }

    @Step("Ввод указанной в тестах карты №2")
    public Page cardTwoInput(Data.ListCards listCards) {
        cardInput.shouldBe(visible, ofSeconds(10000)).setValue(listCards.getCard2());
        return this;
    }

    @Step("Ввод случайной карты")
    public Page fakerCardInput() {
        val faker = new Faker();
        cardInput.shouldBe(visible, ofSeconds(10000))
                .setValue(faker.numerify("#### #### #### ####"));
        return this;
    }

}
