package practice1.Controller;

import practice1.Model.MarketPrice;
import practice1.Model.Position;
import practice1.Model.Stock;
import practice1.Model.Trade;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PositionController {
    /**
     * 保有数量を計算する。
     *
     * @param trades　これまでの取引データ
     * @param stocks　株式銘柄マスタデータ
     * @return 銘柄コードと保有数量のMap
     */
    public Map<String, Integer> calculateOwnPosition(List<Trade> trades, List<Stock> stocks) {
        //保有ポジションの計算
        Map<String, Integer> ownPositionData = new HashMap<>();
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
            ownPositionData.put(ownTicker, ownPositionData.getOrDefault(ownTicker, 0) + ownQuantity);
        }

        return ownPositionData;
    }

    //銘柄コードと平均取得単価の組み合わせのMapを返すメソッド
    public Map<String, BigDecimal> calculateAverageUnitPrice(List<Trade> trades, List<Stock> stocks) {

        //銘柄コードごとに取引データをグルーピング
        Map<String, List<Trade>> groupedTradeByTicker = groupingTradeByTicker(trades, stocks);

        Map<String, BigDecimal> averageUnitPriceData = new HashMap<>();

        for (String ticker : groupedTradeByTicker.keySet()) {
            List<Trade> tickerWithTradeData = groupedTradeByTicker.get(ticker);

            int totalQuantity = 0;
            BigDecimal totalTradeUnitPrice = BigDecimal.ZERO;

            for (Trade trade : tickerWithTradeData) {
                if (trade.getSide().equals("BUY")) {
                    totalQuantity += trade.getQuantity();
                    totalTradeUnitPrice = totalTradeUnitPrice.add(trade.getTradedUnitPrice().multiply(new BigDecimal(trade.getQuantity())));
                }
            }

            BigDecimal averageTradeUnitPrice = totalTradeUnitPrice.
                    divide(new BigDecimal(totalQuantity), 2, RoundingMode.HALF_UP);
            averageUnitPriceData.put(ticker, averageTradeUnitPrice);
        }
        return averageUnitPriceData;
    }

    //損益額を計算し、銘柄コードと組み合わせて出力するメソッド
    public Map<String, BigDecimal> calculateRealizedProfitAndLoss(List<Trade> trades, List<Stock> stocks) {
        Map<String, List<Trade>> groupedTradeByTicker = groupingTradeByTicker(trades, stocks);
        Map<String, BigDecimal> tickerAndRealizedProfitAndLoss = new HashMap<>();

        for (String ticker : groupedTradeByTicker.keySet()) {
            List<Trade> tickerWithTradeData = groupedTradeByTicker.get(ticker);

            for (Trade trade : tickerWithTradeData) {
                int quantity = trade.getSide().equals("SELL") ? trade.getQuantity() : -trade.getQuantity();
                BigDecimal realizedProfitAndLoss = trade.getTradedUnitPrice().multiply(new BigDecimal(quantity));

                tickerAndRealizedProfitAndLoss.put(ticker,
                        tickerAndRealizedProfitAndLoss.getOrDefault(ticker, BigDecimal.ZERO).add(realizedProfitAndLoss).setScale(2, RoundingMode.HALF_UP));
            }
        }
        return tickerAndRealizedProfitAndLoss;
    }

    //取得価額計算メソッド
    public Map<String, BigDecimal> calculateAcquisitionCost(List<Trade> trades, List<Stock> stocks) {
        Map<String, Integer> ownPositionMap = calculateOwnPosition(trades, stocks);
        Map<String, BigDecimal> averageUnitPriceMap = calculateAverageUnitPrice(trades, stocks);
        Map<String, BigDecimal> acquisitionCostMap = new HashMap<>();

        for (String ticker : ownPositionMap.keySet()) {
            BigDecimal ownPosition = new BigDecimal(ownPositionMap.get(ticker));
            BigDecimal acquisitionCost = ownPosition.multiply(averageUnitPriceMap.get(ticker));
            acquisitionCostMap.put(ticker, acquisitionCost);
        }

        return acquisitionCostMap;
    }

    //評価額計算メソッド
    public Map<String, BigDecimal> calculateValuation(List<Trade> trades, List<Stock> stocks) {
        MarketPrice marketPriceController = new MarketPrice();
        Map<String, BigDecimal> valuationData = new HashMap<>();
        Map<String, Integer> ownPostionMap = calculateOwnPosition(trades, stocks);
        Map<String, BigDecimal> marketPriceData = marketPriceController.readMarketPriceFromCSV();

        for (String ticker : ownPostionMap.keySet()) {
            if (marketPriceData.containsKey(ticker)) {
                BigDecimal ownPosition = new BigDecimal(ownPostionMap.get(ticker));
                BigDecimal valuation = ownPosition.multiply(marketPriceData.get(ticker)).setScale(2, RoundingMode.HALF_UP);
                valuationData.put(ticker, valuation);
            } else {
                valuationData.put(ticker, null);
            }
        }
        return valuationData;
    }

    //評価損益計算メソッド
    public Map<String, BigDecimal> calculateUnrealizedProfitAndLoss(List<Trade> trades, List<Stock> stocks) {
        Map<String, BigDecimal> unrealizeProfitAndLossData = new HashMap<>();
        Map<String, BigDecimal> acquisitionCostData = calculateAcquisitionCost(trades, stocks);
        Map<String, BigDecimal> valuationData = calculateValuation(trades, stocks);

        for (String ticker : acquisitionCostData.keySet()) {
            BigDecimal valuation = valuationData.get(ticker);
            if (valuation== null) {
                unrealizeProfitAndLossData.put(ticker, null);
            } else {
                BigDecimal acquisitionCost = acquisitionCostData.get(ticker);
                BigDecimal unrealizedProfitAndLoss = valuationData.get(ticker).subtract(acquisitionCost).setScale(2, RoundingMode.HALF_UP);
                unrealizeProfitAndLossData.put(ticker, unrealizedProfitAndLoss);
            }
        }

        return unrealizeProfitAndLossData;
    }

    //List<Position>型のデータを返すメソッド
    public List<Position> positionList(List<Trade> trades, List<Stock> stocks) {
        Map<String, Integer> ownPositionData = calculateOwnPosition(trades, stocks);
        Map<String, BigDecimal> averageUnitPriceData = calculateAverageUnitPrice(trades, stocks);
        Map<String, BigDecimal> realizeProfitAndLoss = calculateRealizedProfitAndLoss(trades, stocks);
        Map<String, BigDecimal> valuationData = calculateValuation(trades, stocks);
        Map<String, BigDecimal> unrealizedProfitAndLossData = calculateUnrealizedProfitAndLoss(trades, stocks);

        List<Position> positions = new ArrayList<>();
        for (String ticker : ownPositionData.keySet()) {
            Position position = new Position(ticker,
                    ownPositionData.get(ticker),
                    averageUnitPriceData.get(ticker),
                    realizeProfitAndLoss.get(ticker),
                    valuationData.get(ticker),
                    unrealizedProfitAndLossData.get(ticker));
            positions.add(position);
        }
        return positions;
    }

    //銘柄コードごとに取引データをグルーピングするprivateメソッド
    private Map<String, List<Trade>> groupingTradeByTicker(List<Trade> trades, List<Stock> stocks) {
        Map<String, List<Trade>> groupedTradeByTicker = new HashMap<>();
        for (Trade trade : trades) {
            String ticker = linkTickerFromName(trade.getName(), stocks);
            groupedTradeByTicker.computeIfAbsent(ticker, k -> new ArrayList<>()).add(trade);
        }
        return groupedTradeByTicker;
    }

    //銘柄名と銘柄コードを紐づけるメソッド
    private String linkTickerFromName(String name, List<Stock> stocks) {
        String ticker;
        for (Stock stock : stocks) {
            if (name.equals(stock.getName())) {
                ticker = stock.getTicker();
                return ticker;
            }
        }
        return "null";
    }
}
