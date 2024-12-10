package practice1;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) {
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setMaximumFractionDigits(2);
        BigDecimal tradeUnitPrice = new BigDecimal(decimalFormat.format(100));
        System.out.println(tradeUnitPrice);

    }
}
