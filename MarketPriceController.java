package practice1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MarketPriceController {
    public Map<String, BigDecimal> readMarketPriceFromCSV() {
        Map<String, BigDecimal> marketPriceData = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("MarketPriceData.csv"));
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] values = line.split(",");

                if (values.length != 2) {
                    System.out.println("husei");
                    continue;
                }

                String ticker = values[0];
                BigDecimal marketPrice = new BigDecimal(values[1]);
                marketPriceData.put(ticker, marketPrice);
            }
        }catch (FileNotFoundException e) {
            System.out.println("ファイルが見つかりません");
        } catch (IOException e) {
            System.out.println("エラー");
        }
        return marketPriceData;
    }
}
