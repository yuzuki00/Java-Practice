package practice1;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TradeListController {
    public Trade addNewTrade(List<Stock> stocks) {
        Scanner scanner = new Scanner(System.in);
        Validator validator = new Validator();
        boolean step = true;

        LocalDateTime confirmedTradeDateTime;
        while (true) {
            try {
                System.out.print("取引日時(yyyy/MM/dd HH:mm> ");
                LocalDateTime tradeDateTime = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
                if (validator.validTradeDateTime(tradeDateTime)) {
                    confirmedTradeDateTime = tradeDateTime;
                    break;
                }
            } catch (Exception e) {
                System.out.println("書式エラー");
            }
        }

        String confirmedName;
        String confirmedTicker;
        while (step) {
            System.out.print("銘柄コード> ");
            String inputTicker = scanner.nextLine();
            for (Stock stock : stocks) {
                if (inputTicker.equals(stock.getTicker())) {
                    confirmedName = stock.getName();
                    confirmedTicker = inputTicker;
                    step = false;
                    break;
                }
            }
            System.out.println("入力された銘柄コードが存在していません");
        }

        String side;
        while (true) {
            System.out.print("売買区分> ");
            side = scanner.nextLine().toUpperCase();
            if (side.equals("BUY") || side.equals("SELL")) {
                break;
            } else {
                System.out.println("売買区分はBUYもしくはSELLを入力して下さい");
            }
        }

        int confirmedQuantity;
        while (true) {
            System.out.print("数量> ");
            String quantity = scanner.nextLine();
            if (validator.validQuantity(quantity)) {
                confirmedQuantity = Integer.parseInt(quantity);
                break;
            }
        }

        BigDecimal tradeUnitPrice;
        while (true){
            break;
        }

        return null;
    }
}
