package persistence;

public interface IRepository<ID,E> {
    int size();
    void save(E entity);
    E findOne(ID id);
    Iterable<E> finadAll();
}
