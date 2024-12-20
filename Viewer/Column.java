package practice1.Viewer;

/**
 * カラムモデル
 */
public class Column {
    String columnName;
    int width;
    boolean isNumeric;

    /**
     * コンストラクタ
     */
    public Column(String columnName, int width, boolean isNumeric) {
        this.columnName = columnName;
        this.width = width;
        this.isNumeric = isNumeric;
    }
}
