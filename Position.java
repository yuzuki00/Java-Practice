package practice1;

import java.math.BigDecimal;

public class Position {
    int quantity;
    BigDecimal averageUnitPrice;
    BigDecimal getUnrealizeProfitAndLoss;
    BigDecimal valuation;
    BigDecimal unrealizeProfitAndLoss;

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getAverageUnitPrice() {
        return averageUnitPrice;
    }

    public BigDecimal getGetUnrealizeProfitAndLoss() {
        return getUnrealizeProfitAndLoss;
    }

    public BigDecimal getValuation() {
        return valuation;
    }

    public BigDecimal getUnrealizeProfitAndLoss() {
        return unrealizeProfitAndLoss;
    }

    public Position(int quantity, BigDecimal averageUnitPrice, BigDecimal getUnrealizeProfitAndLoss, BigDecimal valuation, BigDecimal unrealizeProfitAndLoss) {
        this.quantity = quantity;
        this.averageUnitPrice = averageUnitPrice;
        this.getUnrealizeProfitAndLoss = getUnrealizeProfitAndLoss;
        this.valuation = valuation;
        this.unrealizeProfitAndLoss = unrealizeProfitAndLoss;
    }
}
