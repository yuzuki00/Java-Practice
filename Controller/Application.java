package practice1.Controller;

import practice1.Model.Position;
import practice1.Model.Stock;
import practice1.Model.Trade;
import practice1.Viewer.Viewer;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Application {
    Viewer viewer = new Viewer();
    StockListController stockListController = new StockListController();
    TradeListController tradeListController = new TradeListController();
    PositionController positionController = new PositionController();
    CSVFileController csvFileController = new CSVFileController();

    public String selectMenu() {
        Scanner scanner = new Scanner(System.in);

        viewer.displayMenu();
        System.out.print("入力してください> ");
        return scanner.nextLine();
    }

    public void displayStock() {
        Viewer viewer = new Viewer();

        System.out.println("銘柄マスタを表示します");
        List<Stock> stocks = stockListController.readStocksFromCSV();
        viewer.displayStockList(stocks);
    }

    public void registerNewStock() {
        System.out.println("銘柄マスタ新規登録");
        List<Stock> stocks = stockListController.readStocksFromCSV();
        Stock newStock = stockListController.addNewStock(stocks);
        csvFileController.addStockToCSV(newStock);
    }

    public void registerNewTrade() {
        System.out.println("新規取引入力");
        List<Stock> stocks = stockListController.readStocksFromCSV();
        List<Trade> existTrades = tradeListController.readTradeFromCSV();
        Map<String, Integer> existPositions = positionController.calculateOwnPosition(existTrades,stocks);
        Trade newTrade = tradeListController.addNewTrade(stocks,existTrades, existPositions);
        csvFileController.addTradeToCSV(newTrade);
    }

    public void displayTrade() {
        System.out.println("取引一覧表示");
        List<Trade> trades = tradeListController.readTradeFromCSV();
        viewer.displayTradeList(trades);
    }

    public void displayPosition () {
        System.out.println("保有ポジション表示");
        List<Stock> stocks = stockListController.readStocksFromCSV();
        List<Trade> trades = tradeListController.readTradeFromCSV();
        List<Position> positions = positionController.positionList(trades, stocks);
        viewer.displayPositionData(positions, stocks);
    }
}
