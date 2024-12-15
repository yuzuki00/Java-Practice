package practice1.Model;

public class Stock {
    private final String ticker;
    private final String name;
    private final Market market;
    private final long sharesIssued;

    public Stock(String ticker, String name, Market market, long sharesIssued) {
        this.ticker = ticker;
        this.name = name;
        this.market = market;
        this.sharesIssued = sharesIssued;
    }

    public String getTicker() {
        return ticker;
    }

    public String getName() {
        return name;
    }

    public Market getMarket() {
        return market;
    }

    public long getSharesIssued() {
        return sharesIssued;
    }
}
