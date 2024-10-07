package data.interfaces;

import java.util.List;

public interface CRUD<T> {

    void create(T t);

    List<T> read();

    void update(T t);

    void delete(T t);
}
