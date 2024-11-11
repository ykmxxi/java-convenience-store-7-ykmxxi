package store.domain.product;

import java.util.Objects;

public class Name {

    private final String value;

    private Name(final String value) {
        validateNullAndBlank(value);
        this.value = value;
    }

    private void validateNullAndBlank(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("잘못된 값을 입력했습니다.");
        }
    }

    public static Name from(final String value) {
        return new Name(value);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Name otherName)) {
            return false;
        }
        return Objects.equals(value, otherName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "name=" + value;
    }

}
