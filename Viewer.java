package practice1;

import java.util.List;

public class Viewer {
    public void displayMenu() {
        System.out.println("""
                操作するメニューを選んでください。
                1. 銘柄マスタ一覧表示
                2. 銘柄マスタ新規登録
                9. アプリケーションを終了する""");
    }

    public void displayStockList(List<Stock> stocks) {
        MarketManager marketManager = new MarketManager();
        if (stocks.isEmpty()) {
            System.out.println("銘柄データが存在しません");
        }

        String format = "| %-8s | %-20s | %-10s | %15s |\n";
        System.out.println("|" + "=".repeat(64) + "|");
        System.out.printf(format, " Ticker ", "Product Name", "Market", "Shares Issued");
        System.out.println("|" + "=".repeat(64) + "|");

        for (Stock stock : stocks) {
            String stockName = stock.getName();
            if (stockName.length() > 20) {
                stockName = stockName.substring(0, 17) + "...";
            }
            System.out.printf(format,
                    " ".repeat(2) + stock.getTicker() + " ".repeat(2),
                    stockName,
                    marketManager.parseMarket(stock.getMarket()),
                    String.format("%,d", stock.getSharesIssued()));
        }
        System.out.println("|" + "=".repeat(64) + "|");
    }
}



