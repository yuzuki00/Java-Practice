package practice1;

public class MarketManager {
    public Market parseStringToMarket(String market) throws IllegalStateException {
        switch (market) {
            case "P","PRIME" -> {
                return Market.PRIME;
            }
            case "S","STANDARD" -> {
                return Market.STANDARD;
            }
            case "G","GROWTH" -> {
                return Market.GROWTH;
            }
            default -> throw new IllegalStateException();
        }
    }
}
