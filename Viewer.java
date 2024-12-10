package practice1;

import com.sun.security.jgss.GSSUtil;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class Viewer {
    public void displayMenu() {
        System.out.println("""
                操作するメニューを選んでください。
                1. 銘柄マスタ一覧表示
                2. 銘柄マスタ新規登録
                3. 取引新規入力
                4．取引一覧表示
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

    public void displayTradeList(List<Trade> trades) {
        if (trades.isEmpty()) {
            System.out.println("データが存在しません");
        } else {
            String format = "| %-18s | %-20s | %-5s | %15s | %18s | %-18s | \n";
            System.out.println("|" + "=".repeat(111) + "|");
            System.out.printf(format, "Trade DateTime", "Product Name", "Side", "Quantity", "Trade Unit Price", "Input DateTime");
            System.out.println("|" + "=".repeat(111) + "|");

            for (Trade trade : trades) {
                String tradeDateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm").format(trade.getTradedDateTime());
                String name = trade.getName();
                if (name.length() > 20) {
                    name = name.substring(0, 17) + "...";
                }
                String inputDateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm").format(trade.getInputDateTime());

                System.out.printf(format,
                        tradeDateTime,
                        name,
                        trade.getSide(),
                        trade.getQuantity(),
                        trade.getTradedUnitPrice(),
                        inputDateTime);
            }
            System.out.println("|" + "=".repeat(111) + "|");
        }
    }
}



