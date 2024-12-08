package practice1;


import java.util.List;

public class TradeMain {
    public static void main(String[] args) {
        Application application = new Application();
        Viewer viewer = new Viewer();
        StockListController stockListController = new StockListController();
        CSVFileController csvFileController = new CSVFileController();
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
                default -> System.out.println("\"" + selectedMenu + "\"に対応するメニューは存在しません。\n");
            }
        }
    }
}
