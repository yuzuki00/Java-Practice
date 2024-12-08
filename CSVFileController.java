package practice1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class CSVFileController {
    public void addStockToCSV(Stock newStock) {
        MarketManager marketManager = new MarketManager();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("StockData.csv", true));
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
}
