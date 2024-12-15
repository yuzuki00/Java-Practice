package practice1.Viewer;

import practice1.Controller.MarketManager;
import practice1.Model.Position;
import practice1.Model.Stock;
import practice1.Model.Trade;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

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

    public void displayPositionData(List<Position> positions, List<Stock> stocks) {
        if (positions.isEmpty()) {
            System.out.println("データが存在しません");
        } else {
            //ポジションデータを銘柄コードでソート
            positions.sort(Comparator.comparing(Position::getTicker));

            String format = "| %6s | %-20s | %10s | %25s | %25s | %15s | %25s | \n";
            System.out.println("|" + "=".repeat(146) + "|");
            System.out.printf(format,
                    "Ticker",
                    "Product Name",
                    "Quantity",
                    "Average Unit Price",
                    "Realized Profit and Loss",
                    "Valuation",
                    "Unrealized Profit and Loss");
            System.out.println("|" + "=".repeat(146) + "|");

            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

            for (Position position:positions) {
                String name= "";
                for (Stock stock: stocks) {
                    if (stock.getTicker().equals(position.getTicker())) {
                        name = stock.getName();
                        if (name.length()>20) {
                            name = name.substring(0,17) + "...";
                        }
                        break;
                    }
                }

                String unrealizedProfitAndLoss;
                String valuation;
                if (position.getValuation() == null) {
                    valuation = "N/A";
                    unrealizedProfitAndLoss = "N/A";
                } else {
                    valuation = decimalFormat.format(position.getValuation());
                    unrealizedProfitAndLoss = decimalFormat.format(position.getUnrealizeProfitAndLoss());
                }

                System.out.printf(format,
                        " " + position.getTicker()+ " ",
                        name,
                        NumberFormat.getNumberInstance().format(position.getQuantity()),
                        decimalFormat.format(position.getAverageUnitPrice()),
                        decimalFormat.format(position.getRealizeProfitAndLoss()),
                        valuation,
                        unrealizedProfitAndLoss);
            }
            System.out.println("|" + "=".repeat(146) + "|");
        }
    }
}



