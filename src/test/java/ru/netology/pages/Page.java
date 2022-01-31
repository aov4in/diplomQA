package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class Page {
    private final SelenideElement buyButton = $(byText("Купить"));
    private final SelenideElement creditBuyButton = $(byText("Купить в кредит"));
    private final SelenideElement paymentFormName = $(byText("Оплата по карте"));
    private final SelenideElement creditFormName = $(byText("Кредит по данным карты"));
    private final SelenideElement messageError = $(withText("Ошибка"));
    private final SelenideElement messageSuccessfully = $(withText("Успешно"));

    @Step("Нажать кнопку 'Купить'")
    public PageCards payWithDebitCard () {
        buyButton.click();
        paymentFormName.shouldBe(visible);
        return new PageCards ();
    }

    @Step("Нажать кнопку 'Купить в кредит'")
    public PageCards requestCredit () {
        creditBuyButton.click();
        creditFormName.shouldBe(visible);
        return new PageCards();
    }

    public void successfullyTransaction () {
        messageSuccessfully.shouldBe(visible, Duration.ofDays(10000));
    }

    public void errorTransaction () {
        messageError.shouldBe(visible, Duration.ofDays(10000));
    }
}
