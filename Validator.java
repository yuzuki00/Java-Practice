package practice1;

import java.util.List;

public class Validator {
    public boolean validateTicker(List<Stock> stocks, String inputTicker) {
        for (Stock stock : stocks) {
            String existTicker = stock.getTicker();
            if (inputTicker.equals(existTicker)) {
                System.out.println("銘柄コードが重複しています");
                return false;
            }
        }

        if (inputTicker.length() != 4) {
            System.out.println("銘柄コードは4桁で入力してください");
            return false;
        }

        if (!inputTicker.matches("[0-9][ACDF-HJ-NPR-UW-Y0-9][0-9][ACDF-HJ-NPR-UW-Y0-9]")) {
            System.out.println("銘柄コードの入力規則に反しています");
            return false;
        }



        return true;
    }
}
