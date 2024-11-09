package store.presentation.file;

import java.util.Arrays;
import java.util.List;

public enum ProductColumn {

    NAME("name", 0),
    PRICE("price", 1),
    QUANTITY("quantity", 2),
    PROMOTION("promotion", 3);

    private final String name;
    private final int index;

    ProductColumn(final String name, final int index) {
        this.name = name;
        this.index = index;
    }

    public static void validateColumnNameLine(final List<String> columnNameLine) {
        List<String> productColumns = Arrays.stream(ProductColumn.values())
                .map(productColumn -> productColumn.name)
                .toList();
        if (isDifferent(productColumns, columnNameLine)) {
            throw new IllegalStateException("데이터 파일에 누락된 컬럼이 존재합니다.");
        }
    }

    private static boolean isDifferent(final List<String> productColumns, final List<String> columnNameLine) {
        return !productColumns.equals(columnNameLine);
    }

    public static void validateTupleColumnCount(final int tupleColumnCount) {
        if (ProductColumn.values().length != tupleColumnCount) {
            throw new IllegalStateException("데이터 파일에 누락된 정보가 존재합니다.");
        }
    }

    public static int index(final String columnName) {
        ProductColumn productColumn = Arrays.stream(ProductColumn.values())
                .filter(column -> column.name.equals(columnName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 컬럼입니다."));
        return productColumn.index;
    }

}
