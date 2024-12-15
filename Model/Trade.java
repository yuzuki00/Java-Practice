package practice1.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Trade {
    private LocalDateTime tradedDateTime;
    private String name;
    private String side; //BUY or SELL
    private int quantity;
    private BigDecimal tradedUnitPrice;
    private LocalDateTime inputDateTime;

    public Trade(LocalDateTime tradedDateTime, String name, String side, int quantity, BigDecimal tradedUnitPrice, LocalDateTime inputDateTime) {
        this.tradedDateTime = tradedDateTime;
        this.name = name;
        this.side = side;
        this.quantity = quantity;
        this.tradedUnitPrice = tradedUnitPrice;
        this.inputDateTime = inputDateTime;
    }

    public LocalDateTime getTradedDateTime() {
        return tradedDateTime;
    }

    public String getName() {
        return name;
    }

    public String getSide() {
        return side;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTradedUnitPrice() {
        return tradedUnitPrice;
    }

    public LocalDateTime getInputDateTime() {
        return inputDateTime;
    }
}
