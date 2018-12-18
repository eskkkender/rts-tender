package test_selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.io.FileWriter;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.List;
import static com.codeborne.selenide.Selenide.open;

public class TestClass {

    private static final Logger log = Logger.getLogger(TestClass.class.getName());



   @Test
    public void testClass() throws IOException {

        System.setProperty("webdriver.chrome.driver","C://Users/Esken/Downloads/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        open("https://223.rts-tender.ru/supplier/auction/Trade/Search.aspx");
        Tender tender = new Tender();

        tender.searchPage().setInputPublicationDateFrom();
        tender.searchPage().setInputPublicationDateTo();
        tender.searchPage().setInputPriceFrom("0");
        tender.searchPage().activeCheckbox223FZ();
        tender.searchPage().activeCheckboxCommercialPurchase();
        tender.searchPage().clickButtonSearch();
        tender.searchPage().setListBox("100");
        List<TableTender> tableAll = tender.searchPage().getTable(tender.searchPage().getPageCount());
        log.info("Выполнено");
        log.info("Количество лотов : " + tableAll.size() + ", Всего на сумму: " + tender.searchPage().price(tableAll) + " руб.");
        tender.searchPage().clickTabCancel();
        List<TableTender> tableCloset = tender.searchPage().getTable(tender.searchPage().getPageCount());
        log.info("Количество отмененных лотов: " + tableCloset.size() + ", Всего на сумму: " + tender.searchPage().price(tableCloset) + " руб.");
        List<TableTender> resultTable = tender.searchPage().getResultLots(tableAll, tableCloset);
        log.info("Общее количество лотов: " + resultTable.size() + ", Всего на сумму: " + tender.searchPage().price(resultTable) + " руб.");

        try(FileWriter writer = new FileWriter("result.txt", false))
        {
            // запись всей строки
            String text = "Общее количество лотов: " + resultTable.size() + ", Всего на сумму: " + tender.searchPage().price(resultTable) + " руб.";
            writer.write(text);

            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }

    }


}
