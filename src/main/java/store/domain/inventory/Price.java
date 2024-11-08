package store.domain.inventory;

import java.util.Objects;

public class Price {

    private final long value;

    private Price(final long value) {
        validateLessThanMinimumPrice(value);
        this.value = value;
    }

    private void validateLessThanMinimumPrice(final long value) {
        if (value < 1L) {
            throw new IllegalStateException("상품 가격 정보를 불러오는 과정에서 시스템 예외가 발생했습니다. 프로그램을 다시 실행해 주세요.");
        }
    }

    public static Price from(final long value) {
        return new Price(value);
    }

    public long value() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Price price)) {
            return false;
        }
        return value == price.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "price=" + value;
    }

}
