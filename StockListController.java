package practice1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StockListController {
    public List<Stock> readStocksFromCSV() {
        List<Stock> stocks = new ArrayList<>();
        MarketManager marketManager = new MarketManager();

        try (BufferedReader br = new BufferedReader(new FileReader("StockData.csv"))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] values = line.split(",");
                if (values.length != 4) {
                    System.out.println("不正なフォーマットの行がありました。1行スキップします");
                    continue;
                }

                try {
                    Stock stock = new Stock(values[0],
                            values[1],
                            marketManager.parseStringToMarket(values[2]),
                            Long.parseLong(values[3])
                    );
                    stocks.add(stock);
                } catch (NumberFormatException e) {
                    System.out.println("数値フォーマットエラーがありました");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("ファイルが存在しません");
        } catch (IOException e) {
            System.out.println("エラー");
        }
        return stocks;
    }

    public Stock addNewStock(List<Stock> stocks) {
        Scanner scanner = new Scanner(System.in);
        MarketManager marketManager = new MarketManager();
        Validator validator = new Validator();

        System.out.print("銘柄名> ");
        String name = scanner.nextLine();

        String confirmedTicker;
        while (true) {
            System.out.print("銘柄コード> ");
            String inputTicker = scanner.nextLine();
            if (validator.validateTicker(stocks, inputTicker)) {
                confirmedTicker = inputTicker;
                break;
            }
        }
        System.out.print("銘柄コード> ");
        String ticker = scanner.nextLine();

        System.out.print("上場市場> ");
        Market market = marketManager.parseStringToMarket(scanner.nextLine().toUpperCase());

        System.out.print("発行済み株式数> ");
        long sharesIssued = Long.parseLong(scanner.nextLine());

        return new Stock(name, ticker, market, sharesIssued);

    }
}
