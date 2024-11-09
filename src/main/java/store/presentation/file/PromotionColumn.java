package store.presentation.file;

import java.util.Arrays;
import java.util.List;

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

    public static void validateColumnNameLine(final List<String> columnNameLine) {
        List<String> promotionColumns = Arrays.stream(PromotionColumn.values())
                .map(promotionColumn -> promotionColumn.name)
                .toList();
        if (isDifferent(promotionColumns, columnNameLine)) {
            throw new IllegalStateException("데이터 파일에 누락된 컬럼이 존재합니다.");
        }
    }

    private static boolean isDifferent(final List<String> promotionColumn, final List<String> columnNameLine) {
        return !promotionColumn.equals(columnNameLine);
    }

    public static void validateTupleColumnCount(final int tupleColumnCount) {
        if (PromotionColumn.values().length != tupleColumnCount) {
            throw new IllegalStateException("데이터 파일에 누락된 정보가 존재합니다.");
        }
    }

    public static int index(final String columnName) {
        PromotionColumn promotionColumn = Arrays.stream(PromotionColumn.values())
                .filter(column -> column.name.equals(columnName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 컬럼입니다."));
        return promotionColumn.index;
    }

}
