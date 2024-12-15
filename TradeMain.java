package practice1;


import practice1.Controller.*;

public class TradeMain {
    public static void main(String[] args) {
        Application application = new Application();

        System.out.println("株式取引管理システムを開始します。");
        while (true) {
            String selectedMenu = application.selectMenu();
            if (selectedMenu.equals(("9"))) {
                System.out.println("アプリケーションを終了します。");
                break;
            }

            switch (selectedMenu) {
                case "1" -> {
                    application.displayStock();
                    System.out.println();
                }
                case "2" -> {
                    application.registerNewStock();
                    System.out.println();
                }
                case "3" -> {
                    application.registerNewTrade();
                    System.out.println();
                }
                case "4" -> {
                    application.displayTrade();
                    System.out.println();
                }
                case "5" -> {
                    application.displayPosition();
                    System.out.println();
                }
                default -> System.out.println("\"" + selectedMenu + "\"に対応するメニューは存在しません。\n");
            }
        }
    }
}
