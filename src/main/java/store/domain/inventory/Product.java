package store.domain.inventory;

import java.util.Objects;

public class Product {

    private final Name name;
    private final Price price;

    private Product(final Name name, final Price price) {
        this.name = name;
        this.price = price;
    }

    public static Product storage(final String nameValue, final long priceValue) {
        return new Product(Name.from(nameValue), Price.from(priceValue));
    }

    public String name() {
        return name.value();
    }

    public long price() {
        return price.value();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product product)) {
            return false;
        }
        return Objects.equals(name, product.name) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    @Override
    public String toString() {
        return "Product[" + name + "," + price + ']';
    }

}
