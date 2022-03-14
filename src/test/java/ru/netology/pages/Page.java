package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class Page {
    private SelenideElement heading = $$("h2").find(Condition.text("Путешествие дня"));
    private SelenideElement buyButton = $$("button").find(exactText("Купить"));
    private SelenideElement creditBuyButton = $$("button").find(exactText("Купить в кредит"));

    @Step("Нажать кнопку 'Купить'")
    public PageDebitCards payWithDebitCard () {
        buyButton.shouldBe(visible);
        return new PageDebitCards();
    }

    @Step("Нажать кнопку 'Купить в кредит'")
    public PageCreditCards requestCredit() {
        creditBuyButton.shouldBe(visible);
        return new PageCreditCards();
    }
    @Step("Путешествие дня")
    public void startPage () {
        heading.shouldBe(visible);
    }
}
