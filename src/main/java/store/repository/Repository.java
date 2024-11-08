package store.repository;

import java.util.List;

public interface Repository<T, ID> {

    List<T> saveAll(Iterable<T> entities);

}
