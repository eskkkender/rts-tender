package test_selenium;
import cucumber.api.java.ru.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.codeborne.selenide.Selenide.open;

public class MyStepdefs {

    Tender tender = new Tender();

    @Когда("^открываю сайт$")
    public void открываюСайт() throws Throwable {
        System.setProperty("webdriver.chrome.driver","C://Users/Esken/Downloads/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        open("https://223.rts-tender.ru/supplier/auction/Trade/Search.aspx");
    }

    @Когда("^заполняются даты$")
    public void заполняютсяДаты() throws Throwable {
        tender.searchPage().setInputPublicationDateFrom();
        tender.searchPage().setInputPublicationDateTo();
    }

    @Когда("^начальная цена$")
    public void начальнаяЦена() throws Throwable {
        tender.searchPage().setInputPriceFrom("0");
    }

    @Когда("^тип закупки: Закупка в соответствии с нормами (\\d+)-ФЗ - Активирован$")
    public void типЗакупкиЗакупкаВСоответствииСНормамиФЗАктивирован(int arg1) throws Throwable {
        tender.searchPage().activeCheckbox223FZ();
    }

    @Когда("^тип закупки: Коммерческая закупка - Активирован$")
    public void типЗакупкиКоммерческаяЗакупкаАктивирован() throws Throwable {
        tender.searchPage().activeCheckboxCommercialPurchase();
    }

    @Тогда("^производим поиск$")
    public void производимПоиск() throws Throwable {
        tender.searchPage().clickButtonSearch();
    }








}