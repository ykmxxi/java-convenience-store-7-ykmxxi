package store.repository;

import java.util.List;

public interface Repository<T, ID> {

    T find(ID id);

    List<T> findAll();

    List<T> saveAll(Iterable<T> entities);

}
