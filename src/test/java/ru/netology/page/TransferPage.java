package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private final SelenideElement amount = $("[data-test-id=amount] input");
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private SelenideElement transferHead = $(byText("Пополнение карты"));
    private SelenideElement fromCard = $("[data-test-id='from'] input");

    public TransferPage() {
        transferHead.shouldBe(visible);
    }

    public DashboardPage madeTransfer(int amountForTransfer, DataHelper.CardDetails cardDetails) {
        amount.setValue(String.valueOf(amountForTransfer));
        fromCard.setValue(cardDetails.getNumber());
        transferButton.click();
        return new DashboardPage();
    }
}
