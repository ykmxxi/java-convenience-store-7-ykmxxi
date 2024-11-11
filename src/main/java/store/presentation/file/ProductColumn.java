package store.presentation.file;

import java.util.Arrays;

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

    public static int index(final String columnName) {
        ProductColumn productColumn = Arrays.stream(ProductColumn.values())
                .filter(column -> column.name.equals(columnName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 컬럼입니다."));
        return productColumn.index;
    }

}
