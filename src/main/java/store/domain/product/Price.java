package store.domain.product;

import java.util.Objects;

public class Price {

    private final long value;

    private Price(final long value) {
        this.value = value;
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
