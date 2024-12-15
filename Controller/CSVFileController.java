package practice1.Controller;

import practice1.Model.Stock;
import practice1.Model.Trade;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.time.format.DateTimeFormatter;

public class CSVFileController {
    public void addStockToCSV(Stock newStock) {
        MarketManager marketManager = new MarketManager();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("DataFile/StockData.csv", true));
            writer.newLine();
            writer.append(String.format("%s,%s,%s,%d",
                    newStock.getTicker(),
                    newStock.getName(),
                    marketManager.parseMarketToString(newStock.getMarket()),
                    newStock.getSharesIssued()));
            writer.close();
        } catch (NoSuchFileException e) {
            System.out.println("ファイルが見つかりません");
        } catch (IOException e) {
            System.out.println("データが読み込めません");
        }

    }
    public void addTradeToCSV(Trade newTrade) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("DataFile/TradeData.csv", true));
            writer.newLine();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            writer.append(String.format("%s,%s,%s,%s,%s,%s",
                    dateTimeFormatter.format(newTrade.getTradedDateTime()),
                    newTrade.getName(),
                    newTrade.getSide(),
                    newTrade.getQuantity(),
                    newTrade.getTradedUnitPrice(),
                    dateTimeFormatter.format(newTrade.getInputDateTime())));
            writer.close();
        } catch (IOException e) {
            System.out.println("error");
        }

    }
}
