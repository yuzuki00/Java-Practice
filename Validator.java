package practice1;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Validator {
    public boolean validName(String inputName) {
        if (!inputName.matches(".[a-zA-Z0-9 .()]{1,}")) {
            System.out.println("入力規則に反しています");
            return false;
        }
        return true;
    }

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

        for (String s : validMarket) {
            if (uppercaseMarket.equals(s)) {
                return true;
            }
        }
        System.out.println("上場市場を正しく入力してください");
        return false;

    }

    public boolean validSharesIssued(String input) {
        long inputSharesIssued;
        try {
            inputSharesIssued = Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("整数を入力してください");
            return false;
        }

        if (inputSharesIssued < 0) {
            System.out.println("1以上の数値を入力してください");
        }

        if (!(inputSharesIssued < Math.pow(10, 13))) {
            System.out.println("12桁以下の数値を入力してください");
            return false;
        }

        return true;
    }

    public boolean validQuantity(String inputQuantity) {
        int quantity;
        try {
            quantity = Integer.parseInt(inputQuantity);
        } catch (NumberFormatException e) {
            System.out.println("数値を入力してください");
            return false;
        }

        if (quantity <= 0) {
            System.out.println("1以上の数値を入力してください");
            return false;
        }

        if (!(quantity % 100 == 0)) {
            System.out.println("取引数量は100株単位で入力してください");
            return false;
        }
        return true;
    }

    public boolean validTradeDateTime(LocalDateTime tradeDateTime) {
        if (tradeDateTime.isAfter(LocalDateTime.now())) {
            System.out.println("過去の日時を入力してください");
            return false;
        }

        DayOfWeek dayOfWeek = tradeDateTime.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            System.out.println("取引日時は平日である必要があります");
            return false;
        }

        LocalTime tradeTime = tradeDateTime.toLocalTime();
        if (tradeTime.isBefore(LocalTime.of(9, 0)) || tradeTime.isAfter(LocalTime.of(15, 30))) {
            System.out.println("取引日時は9:00~15:30の間である必要があります");
            return false;
        }

        return true;
    }

    public boolean validTradeUnitPrice(String tradeUnitPrice) {
        try {
            BigDecimal validTradeUnitPrice = new BigDecimal(tradeUnitPrice);
            String[] input = tradeUnitPrice.split("\\.");

            if (input.length != 1 && input[1].length() > 2) {
                System.out.println("小数点2桁以内で入力してください");
                return false;
            }

            if (validTradeUnitPrice.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("正の数値を入力してください");
                return false;
            } else if (validTradeUnitPrice.compareTo(BigDecimal.ZERO) == 0) {
                System.out.println("0以外の数値を入力してください");
                return false;
            }
        } catch (Exception e) {
            System.out.println("数値を入力してください");
            return false;
        }


        return true;
    }
}
