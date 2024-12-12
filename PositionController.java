package practice1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PositionController {
    public Map<String, Integer> managePosition(List<Trade> trades ,List<Stock> stocks){
        //保有ポジションの計算
        Map<String, Integer> positions = new HashMap<>();
        for (Trade trade : trades) {
            int ownQuantity = trade.getSide().equals("BUY") ? trade.getQuantity(): -trade.getQuantity();
            String ownTicker = null;
            //銘柄名と銘柄コードの対応
            for (Stock stock:stocks) {
                if (trade.getName().equals(stock.getName())) {
                    ownTicker = stock.getTicker();
                    break;
                }
             }
            positions.put(ownTicker, positions.getOrDefault(ownTicker,0) + ownQuantity);
        }

        return positions;
    }
}
