package practice1;

import java.util.Scanner;

public class Application {
    public String selectMenu() {
        Viewer viewer = new Viewer();
        Scanner scanner = new Scanner(System.in);

        viewer.displayMenu();
        System.out.print("入力してください> ");
        return scanner.nextLine();
    }
}
