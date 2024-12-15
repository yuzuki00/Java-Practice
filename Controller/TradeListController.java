package practice1.Controller;

import practice1.Model.Stock;
import practice1.Model.Trade;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TradeListController {
    public Trade addNewTrade(List<Stock> stocks, List<Trade> existTrades, Map<String, Integer> existPositions) {
        Scanner scanner = new Scanner(System.in);
        Validator validator = new Validator();


        String confirmedName;
        String confirmedTicker;
        label:
        while (true) {
            System.out.print("銘柄コード> ");
            String inputTicker = scanner.nextLine();
            for (Stock stock : stocks) {
                if (inputTicker.equals(stock.getTicker())) {
                    confirmedName = stock.getName();
                    confirmedTicker = inputTicker;
                    break label;
                }
            }
            System.out.println("入力された銘柄コードが存在していません");
        }

        String confirmedSide;
        while (true) {
            System.out.print("売買区分> ");
            String inputSide = scanner.nextLine().toUpperCase();
            if (validator.validSide(confirmedTicker,inputSide,existPositions)) {
                confirmedSide = inputSide;
                break;
            }
        }

        int confirmedQuantity;
        while (true) {
            System.out.print("数量> ");
            String quantity = scanner.nextLine();
            if (validator.validQuantity(confirmedTicker, quantity, confirmedSide, existPositions)) {
                confirmedQuantity = Integer.parseInt(quantity);
                break;
            }
        }

        BigDecimal tradeUnitPrice;
        while (true) {
            System.out.print("取引単価> ");
            String inputTradeUnitPrice = scanner.nextLine();
            if (validator.validTradeUnitPrice(inputTradeUnitPrice)) {
                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                decimalFormat.setMinimumFractionDigits(2);
                decimalFormat.setMaximumFractionDigits(2);
                tradeUnitPrice = new BigDecimal(decimalFormat.format(Double.parseDouble(inputTradeUnitPrice)));
                break;
            }
        }

        LocalDateTime confirmedTradeDateTime;
        while (true) {
            try {
                System.out.print("取引日時(yyyy/MM/dd HH:mm)> ");
                LocalDateTime tradeDateTime = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
                if (validator.validTradeDateTime(tradeDateTime,confirmedName,existTrades)) {
                    confirmedTradeDateTime = tradeDateTime;
                    break;
                }
            } catch (Exception e) {
                System.out.println("書式エラー");
            }
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime inputDateTime = LocalDateTime.now();

        Trade newTrade = new Trade(confirmedTradeDateTime, confirmedName, confirmedSide, confirmedQuantity, tradeUnitPrice, inputDateTime);

        return newTrade;
    }

    public List<Trade> readTradeFromCSV() {
        List<Trade> trades = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("DataFile/TradeData.csv"));
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] values = line.split(",");

                if (values.length != 6) {
                    System.out.println("不正な行がありました。1行スキップします");
                    continue;
                }

                try {
                    BigDecimal tradeUnitPrice = new BigDecimal(values[4]);
                    Trade trade = new Trade(LocalDateTime.parse(values[0], DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")),
                            values[1],
                            values[2],
                            Integer.parseInt(values[3]),
                            tradeUnitPrice,
                            LocalDateTime.parse(values[5], DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))
                    );
                    trades.add(trade);
                } catch (NumberFormatException e) {
                    System.out.println("数値フォーマットエラーがありました");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("ファイルが見つかりません");
        } catch (IOException e) {
            System.out.println("error");
        }
        return trades;
    }
}
