package practice1;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Viewer {
    public void displayMenu() {
        System.out.println("""
                操作するメニューを選んでください。
                1. 銘柄マスタ一覧表示
                2. 銘柄マスタ新規登録
                3. 取引新規入力
                4．取引一覧表示
                5. 保有ポジション表示
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
            trades.sort(Comparator.
                    comparing(Trade::getTradedDateTime).reversed().
                    thenComparing(Trade::getName));

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
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                String inputDateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm").format(trade.getInputDateTime());

                System.out.printf(format,
                        tradeDateTime,
                        name,
                        trade.getSide(),
                        NumberFormat.getNumberInstance().format(trade.getQuantity()),
                        decimalFormat.format(trade.getTradedUnitPrice()),
                        inputDateTime);
            }
            System.out.println("|" + "=".repeat(111) + "|");
        }
    }

    public void displayPositionList(Map<String, Integer> positions, List<Stock> stocks) {
        //銘柄コードでソート
        List<String>  sortedTickers;
        sortedTickers = positions.keySet().stream().sorted().toList();

        System.out.printf("%-10s %-20s %10s%n", "銘柄コード", "銘柄名", "保有数量");
        System.out.println("------------------------------------------------------");

        // 保有ポジションを出力
        for (String ticker : sortedTickers) {
            String stockName = null;
            //銘柄コードに応じた銘柄名の取得
            for (Stock stock :stocks)
                if (stock.getTicker().equals(ticker)) {
                    stockName = stock.getName();
                    if (stockName.length()>20) {
                        stockName = stockName.substring(0,17) + "...";
                    }
                    break;
                }

            //保有数量の取得
            int quantity = positions.get(ticker);
            String formattedQuantity = NumberFormat.getNumberInstance().format(quantity);

            System.out.printf("%-10s %-20s %10s%n", ticker, stockName, formattedQuantity);
        }
    }

    public void displayPositionData(List<Position> positions) {
        if (positions.isEmpty()) {
            System.out.println("データが存在しません");
        } else {
            //ポジションデータを銘柄コードでソート
            positions.sort(Comparator.comparing(Position::getTicker));

            String format = "| %-18s | %-20s | %-5s | %15s | %18s | %-18s | \n";
            System.out.println("|" + "=".repeat(111) + "|");
            System.out.printf(format, "Trade DateTime", "Product Name", "Side", "Quantity", "Trade Unit Price", "Input DateTime");
            System.out.println("|" + "=".repeat(111) + "|");

            for (Position position:positions) {
                System.out.printf(format,
                        position.getTicker(),
                        position.getQuantity(),
                        position.getAverageUnitPrice(),
                        position.getRealizeProfitAndLoss(),
                        position.getValuation(),
                        position.getUnrealizeProfitAndLoss());
            }
            System.out.println("|" + "=".repeat(111) + "|");
        }
    }
}



