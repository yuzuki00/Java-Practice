package practice1;


import java.util.List;
import java.util.Map;

public class TradeMain {
    public static void main(String[] args) {
        Application application = new Application();
        Viewer viewer = new Viewer();
        StockListController stockListController = new StockListController();
        TradeListController tradeListController = new TradeListController();
        CSVFileController csvFileController = new CSVFileController();
        PositionManger positionManger = new PositionManger();
        System.out.println("株式取引管理システムを開始します。");
        while (true) {
            String selectedMenu = application.selectMenu();
            if (selectedMenu.equals(("9"))) {
                System.out.println("アプリケーションを終了します。");
                break;
            }

            switch (selectedMenu) {
                case "1" -> {
                    System.out.println("銘柄マスタを表示します");

                    List<Stock> stocks = stockListController.readStocksFromCSV();
                    viewer.displayStockList(stocks);

                    System.out.println();

                }
                case "2" -> {
                    System.out.println("銘柄マスタ新規登録");
                    List<Stock> stocks = stockListController.readStocksFromCSV();
                    Stock newStock = stockListController.addNewStock(stocks);
                    csvFileController.addStockToCSV(newStock);
                    System.out.println();
                }
                case "3" -> {
                    System.out.println("新規取引入力");
                    List<Stock> stocks = stockListController.readStocksFromCSV();
                    Trade newTrade = tradeListController.addNewTrade(stocks);
                    csvFileController.addTradeToCSV(newTrade);
                    System.out.println();
                }
                case "4" -> {
                    System.out.println("取引一覧表示");
                    List<Trade> trades = tradeListController.readTradeFromCSV();
                    viewer.displayTradeList(trades);
                    System.out.println();
                }
                case "5" -> {
                    System.out.println("ポジション");
                    List<Stock> stocks = stockListController.readStocksFromCSV();
                    List<Trade> trades = tradeListController.readTradeFromCSV();
                    Map<String, Integer> positions = positionManger.managePosition(trades, stocks);
                    viewer.displayPositionList(positions,stocks);
                    System.out.println();
                }
                default -> System.out.println("\"" + selectedMenu + "\"に対応するメニューは存在しません。\n");
            }
        }
    }
}
