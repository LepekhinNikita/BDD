package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.getFirstCardDetails;
import static ru.netology.data.DataHelper.getSecondCardDetails;

public class MoneyTransferTest {


    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsV1() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardDetails = getFirstCardDetails();
        var secondCardDetails = getSecondCardDetails();

        int amount = 1100;
        var expectedBalanceFirstCard = dashboardPage.getCardBalance(firstCardDetails) - amount;
        var expectedBalanceSecondCard = dashboardPage.getCardBalance(secondCardDetails) + amount;

        var transferPage = dashboardPage.selectCardToTransfer(secondCardDetails);
        dashboardPage = transferPage.madeTransfer(Integer.parseInt(String.valueOf(amount)), firstCardDetails);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardDetails);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardDetails);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

    @Test
    void shouldTransferFromSecondToFirst() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardDetails = getFirstCardDetails();
        var secondCardDetails = getSecondCardDetails();

        int amount = 500;
        var expectedBalanceFirstCard = dashboardPage.getCardBalance(firstCardDetails) + amount;
        var expectedBalanceSecondCard = dashboardPage.getCardBalance(secondCardDetails) - amount;
        var transferPage = dashboardPage.selectCardToTransfer(firstCardDetails);
        dashboardPage = transferPage.madeTransfer(Integer.parseInt(String.valueOf(amount)), secondCardDetails);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardDetails);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardDetails);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

    @Test
    void shouldTransferFromSecondToFirstMoreThanBalance() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardDetails = getFirstCardDetails();
        var secondCardDetails = getSecondCardDetails();

        int amount = 20_000;
        var expectedBalanceFirstCard = dashboardPage.getCardBalance(firstCardDetails) + amount;
        var expectedBalanceSecondCard = dashboardPage.getCardBalance(secondCardDetails) - amount;
        var transferPage = dashboardPage.selectCardToTransfer(firstCardDetails);
        dashboardPage = transferPage.madeTransfer(Integer.parseInt(String.valueOf(amount)), secondCardDetails);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardDetails);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardDetails);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

    @AfterEach
    void tearDown() {
    }
}
