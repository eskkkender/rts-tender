package test_selenium;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import utilities.PropertyManager;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class Search {

    private SelenideElement title = $(".auction_title");
    private SelenideElement inputPublicationDateFrom = $("#BaseMainContent_MainContent_txtPublicationDate_txtDateFrom");
    private SelenideElement inputPublicationDateTo = $("#BaseMainContent_MainContent_txtPublicationDate_txtDateTo");
    private SelenideElement inputPriceFrom = $("#BaseMainContent_MainContent_txtStartPrice_txtRangeFrom");
    private SelenideElement inputPriceTo = $("#BaseMainContent_MainContent_txtStartPrice_txtRangeTo");
    private SelenideElement checkbox223FZ = $("#BaseMainContent_MainContent_chkPurchaseType_0");
    private SelenideElement checkboxCommercialPurchase = $("#BaseMainContent_MainContent_chkPurchaseType_1");
    private SelenideElement buttonSearch = $("#BaseMainContent_MainContent_btnSearch");
    private SelenideElement buttonReset = $("#BaseMainContent_MainContent_btnCancel");
    private SelenideElement table = $("#BaseMainContent_MainContent_jqgTrade");
    private SelenideElement pageCount = $("#sp_1_BaseMainContent_MainContent_jqgTrade_toppager");
    private SelenideElement listBox = $(".ui-pg-selbox");
    private SelenideElement buttonNextPage = $("#next_t_BaseMainContent_MainContent_jqgTrade_toppager");
    private SelenideElement tabCancel = $$("#lotStateTabs li").get(10);


    public String setInputPublicationDateFrom(){
        String beginDateNotice = PropertyManager.getInstance().getBeginDateNotice();
        inputPublicationDateFrom.setValue(beginDateNotice);
        title.click();
        return beginDateNotice;
    }

    public String setInputPublicationDateTo(){
        String endDateNotice = PropertyManager.getInstance().getEndDateNotice();
        inputPublicationDateTo.setValue(endDateNotice);
        title.click();
        return endDateNotice;
    }

    public String setInputPriceFrom(String priceFrom){
        inputPriceFrom.setValue(priceFrom);
        return priceFrom;
    }

    public void activeCheckbox223FZ(){
        if (!checkbox223FZ.isSelected()){
            checkbox223FZ.click();
        }
    }

    public void activeCheckboxCommercialPurchase(){
        if (!checkboxCommercialPurchase.isSelected()){
            checkboxCommercialPurchase.click();
        }
    }

    public void clickButtonSearch(){
        buttonSearch.click();
        sleep(10000);
    }

    public void clickButtonNextPage(){
        buttonNextPage.click();
        sleep(10000);
    }

    public void setListBox(String value){
        listBox.$$("option").find(Condition.value(value)).click();
        sleep(2000);
    }

    public int getPageCount() {
        return Integer.parseInt(pageCount.getText());
    }

    public void clickTabCancel(){
        tabCancel.click();
        sleep(10000);
    }

    public List<TableTender> getTable(int pageCount){
        List<TableTender> table = new ArrayList<>();
        By byRows = By.cssSelector("tbody tr:not(:first-child)");
        List<SelenideElement> rows = this.table.$$(byRows);
        for (int i = 0; i < pageCount; i++){
            for (SelenideElement row : rows) {
                if (!row.$$("td").get(5).getAttribute("title").isEmpty()) {
                    table.add(createTable(row));
                }
            }
            clickButtonNextPage();
        }
        return table;
    }

    private TableTender createTable(SelenideElement row) {
        By byColumn = By.cssSelector("td");
        List<SelenideElement> columns = row.$$(byColumn);
        TableTender table = new TableTender();
        table.setTradeNumber(columns.get(4).getText());
        table.seteusNumber(columns.get(5).getText());
        table.setStartPrice(columns.get(10).getText());
        return table;
    }

    public long price(List<TableTender> lots){
        long price = 0L;
        for (int i = 0; i < lots.size(); i++){
            if (lots.get(i).getStartPrice().contains("USD")){
                Float etc = Float.parseFloat(lots.get(i).getStartPrice().replaceAll("\\s|USD", "").replace(",", "."));
                lots.get(i).setStartPrice(String.valueOf((int)(etc * 66.79f)));
            }
            if (lots.get(i).getStartPrice().contains("EUR")){
                Float etc = Float.parseFloat(lots.get(i).getStartPrice().replaceAll("\\s|EUR", "").replace(",","."));
                lots.get(i).setStartPrice(String.valueOf((int)(etc * 75.54)));
            }
            price = price + (long) Float.parseFloat(lots.get(i).getStartPrice()
                    .replaceAll("\\s|руб.", "")
                    .replace(",", "."));
        }
        return price;
    }

    public List<TableTender> getResultLots(List<TableTender> allLots, List<TableTender> cancelLost){
        List<TableTender> resultTable = new ArrayList<>();
        for (int i = 0; i < cancelLost.size(); i++){
            for (int k = 0; k < allLots.size(); k++){
                if (allLots.get(k).getTradeNumber().equals(cancelLost.get(i).getTradeNumber())){
                    allLots.remove(k);
                }
            }
        }
        resultTable.addAll(allLots);
        return resultTable;
    }


}
