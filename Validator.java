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

    public boolean validMarket(String inputMarket) {
        String uppercaseMarket = inputMarket.toUpperCase();
        String[] validMarket = {"PRIME", "STANDARD", "GROWTH"};

        for (String s: validMarket) {
            if (uppercaseMarket.equals(s)) {
                return true;
            }
        }
        System.out.println("上場市場を正しく入力してください");
        return false;

    }

    public boolean validSharesIssued (String input) {
        long inputSharesIssued = 0;
        try {
            inputSharesIssued = Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("整数を入力してください");
            return false;
        }

        if (inputSharesIssued < 0) {
            System.out.println("1以上の数値を入力してください");
        }

        if (!(inputSharesIssued < Math.pow(10,13)) ) {
            System.out.println("12桁以下の数値を入力してください");
            return false;
        }

        return true;
    }
}
