package data.interfaces;

import java.util.Map;

public interface CRUD<T> {

    void create(T t);

    Map<String, T> read();

    void update(T t);

    void delete(T t);
}
