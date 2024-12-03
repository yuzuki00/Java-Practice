package practice1;

public class Stock {
    private String ticker;
    private String name;
    private Market market;
    private long sharesIssued;

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
