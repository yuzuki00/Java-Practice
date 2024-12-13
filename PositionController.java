package practice1;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PositionController {
    public Map<String, Integer> managePosition(List<Trade> trades, List<Stock> stocks) {
        //保有ポジションの計算
        Map<String, Integer> positions = new HashMap<>();
        for (Trade trade : trades) {
            int ownQuantity = trade.getSide().equals("BUY") ? trade.getQuantity() : -trade.getQuantity();
            String ownTicker = null;
            //銘柄名と銘柄コードの対応
            for (Stock stock : stocks) {
                if (trade.getName().equals(stock.getName())) {
                    ownTicker = stock.getTicker();
                    break;
                }
            }
            positions.put(ownTicker, positions.getOrDefault(ownTicker, 0) + ownQuantity);
        }

        return positions;
    }

    //銘柄コードと平均取得単価の組み合わせのMapを返すメソッド
    public Map<String, BigDecimal> calculateAverageUnitPrice(List<Trade> trades, List<Stock> stocks) {

        //銘柄コードごとに取引データをグルーピング
        Map<String, List<Trade>> sortedTradeByTicker = groupingTradeByTicker(trades, stocks);

        Map<String, BigDecimal> tickerAndAveragePrice = new HashMap<>();

        for (String ticker: sortedTradeByTicker.keySet()) {
            List<Trade> tickerWithTradeData = sortedTradeByTicker.get(ticker);

            int totalQuantity = 0;
            BigDecimal totalTradeUnitPrice = BigDecimal.ZERO;

            for (Trade trade:tickerWithTradeData) {
                if (trade.getSide().equals("BUY")) {
                    totalQuantity += trade.getQuantity();
                    totalTradeUnitPrice = totalTradeUnitPrice.add(trade.getTradedUnitPrice().multiply(new BigDecimal(trade.getQuantity())));
                }
            }

            BigDecimal averageTradeUnitPrice = totalTradeUnitPrice.divide(
                    new BigDecimal(totalQuantity),2,RoundingMode.HALF_UP);
            tickerAndAveragePrice.put(ticker,averageTradeUnitPrice);
        }
        return tickerAndAveragePrice;
    }

    //損益額を計算し、銘柄コードと組み合わせて出力するメソッド
    public Map<String, BigDecimal> calculateRealizedProfitAndLoss(List<Trade> trades, List<Stock> stocks) {
        Map<String, List<Trade>> groupedTradeByTicker = groupingTradeByTicker(trades, stocks);
        Map<String, BigDecimal> tickerAndRealizedProfitAndLoss = new HashMap<>();

        for (String ticker:groupedTradeByTicker.keySet()) {
            List<Trade> tickerWithTradeData = groupedTradeByTicker.get(ticker);

            for (Trade trade:tickerWithTradeData) {
                int quantity = trade.getSide().equals("BUY") ? trade.getQuantity():-trade.getQuantity();
                BigDecimal realizedProfitAndLoss = trade.getTradedUnitPrice().multiply(new BigDecimal(quantity));

                tickerAndRealizedProfitAndLoss.put(ticker,tickerAndRealizedProfitAndLoss.getOrDefault(ticker, BigDecimal.ZERO).add(realizedProfitAndLoss));
            }
        }
        return tickerAndRealizedProfitAndLoss;
    }

    //銘柄コードごとに取引データをグルーピングするprivateメソッド
    private Map<String, List<Trade>> groupingTradeByTicker (List<Trade> trades, List<Stock> stocks) {
        Map<String, List<Trade>> sortedTradeByTicker = new HashMap<>();
        for (Trade trade : trades) {
            String ticker = linkTickerAndName(trade.getName(), stocks);
            sortedTradeByTicker.computeIfAbsent(ticker, k -> new ArrayList<>()).add(trade);
        }
        return sortedTradeByTicker;
    }

    //銘柄名と銘柄コードを紐づけるメソッド
    private String linkTickerAndName(String name, List<Stock> stocks) {
        String ticker;
        for (Stock stock : stocks) {
            if (name.equals(stock.getTicker())) {
                ticker = stock.getTicker();
                return ticker;
            }
        }
        return "null";
    }
}
