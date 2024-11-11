package store.presentation.file;

import java.util.Arrays;

public enum PromotionColumn {

    NAME("name", 0),
    BUY("buy", 1),
    GET("get", 2),
    START_DATE("start_date", 3),
    END_DATE("end_date", 4);

    private final String name;
    private final int index;

    PromotionColumn(final String name, final int index) {
        this.name = name;
        this.index = index;
    }

    public static int index(final String columnName) {
        PromotionColumn promotionColumn = Arrays.stream(PromotionColumn.values())
                .filter(column -> column.name.equals(columnName))
                .findFirst()
                .get();
        return promotionColumn.index;
    }

}
