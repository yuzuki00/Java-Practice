package practice1.Viewer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TableBuilder {
    private final List<Column> columns = new ArrayList<>();
    private final List<List<Object>> rows = new ArrayList<>();

    public TableBuilder withColumn(String name, int width) {
        return withColumn(name, width, false);
    }

    public TableBuilder withColumn(String name, int width, boolean isNumeric) {
        columns.add(new Column(name, width, isNumeric));
        return this;
    }

    public void addRow(Object... values) {
        rows.add(Arrays.asList(values));
    }

    public void build() {
        StringBuilder separator = new StringBuilder("|");
        for (Column column : columns) {
            separator.append("=".repeat(column.width)).append("|");
        }
        System.out.println(separator);

        StringBuilder header = new StringBuilder("|");
        for (Column column : columns) {
            header.append(formatCell(column.columnName, column.width, column.isNumeric)).append("|");
        }
        System.out.println(header);
        System.out.println(separator);

        for (List<Object> row : rows) {
            StringBuilder rowBuilder = new StringBuilder("|");
            for (int i = 0; i < columns.size(); i++) {
                Column column = columns.get(i);
                rowBuilder.append(formatCell(row.get(i), column.width, column.isNumeric)).append("|");
            }
            System.out.println(rowBuilder);
        }
        System.out.println(separator);
    }

    private String formatCell(Object value, int width, boolean isNumeric) {
        String text = value == null ? "" : value.toString();
        if (text.length() > width) {
            text = text.substring(0, width - 3) + "...";
        }
        return isNumeric ? String.format("%" + width + "s", text) : String.format("%-" + width + "s", text);
    }


}
