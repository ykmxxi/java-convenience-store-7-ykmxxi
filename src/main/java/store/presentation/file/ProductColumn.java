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
                .get();
        return productColumn.index;
    }

}
