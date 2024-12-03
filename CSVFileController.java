package practice1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVFileController {
    public void addStockToCSV(Stock newStock) {
        try {
            BufferedWriter wr = new BufferedWriter(new FileWriter("src/simplex/StockData.csv",true));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
