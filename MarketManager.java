package practice1;

public class MarketManager {
    public Market parseStringToMarket(String market) throws IllegalStateException {
        switch (market) {
            case "P", "PRIME" -> {
                return Market.PRIME;
            }
            case "S", "STANDARD" -> {
                return Market.STANDARD;
            }
            case "G", "GROWTH" -> {
                return Market.GROWTH;
            }
            default -> throw new IllegalStateException();
        }
    }

    public String parseMarketToString(Market market) {
        return switch (market) {
            case Market.PRIME -> "P";
            case Market.STANDARD -> "S";
            case Market.GROWTH -> "G";
        };
    }

    public String parseMarket(Market market) {
        return switch (market) {
            case Market.PRIME -> "Prime";
            case Market.STANDARD -> "Standard";
            case Market.GROWTH -> "Growth";
        };
    }
}
