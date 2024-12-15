package practice1.Model;

import java.math.BigDecimal;

public class Position {
    private final String ticker;
    private final int quantity;
    private final BigDecimal averageUnitPrice;
    private final BigDecimal realizeProfitAndLoss;
    private final BigDecimal valuation;
    private final BigDecimal unrealizeProfitAndLoss;

    public Position(String ticker, int quantity, BigDecimal averageUnitPrice, BigDecimal realizeProfitAndLoss, BigDecimal valuation, BigDecimal unrealizeProfitAndLoss) {
        this.ticker = ticker;
        this.quantity = quantity;
        this.averageUnitPrice = averageUnitPrice;
        this.realizeProfitAndLoss = realizeProfitAndLoss;
        this.valuation = valuation;
        this.unrealizeProfitAndLoss = unrealizeProfitAndLoss;
    }

    public String getTicker() {
        return ticker;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getAverageUnitPrice() {
        return averageUnitPrice;
    }

    public BigDecimal getRealizeProfitAndLoss() {
        return realizeProfitAndLoss;
    }

    public BigDecimal getValuation() {
        return valuation;
    }

    public BigDecimal getUnrealizeProfitAndLoss() {
        return unrealizeProfitAndLoss;
    }
}
