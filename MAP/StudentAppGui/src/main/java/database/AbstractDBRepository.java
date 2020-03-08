package database;

import domain.Entity;
import domain.validators.Validator;
import repository.CrudRepository;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public  abstract class  AbstractDBRepository<ID,E extends Entity<ID>> implements CrudRepository<ID,E>  {
    private Map<ID,E> entities;
    private Connection dbConnection;
    private Validator<E> validator;

    public AbstractDBRepository(Connection dbConnection, Validator<E> validator) {
        this.dbConnection = dbConnection;
        this.validator = validator;
        entities=new HashMap<>();
    }

    public abstract void populateEntities(Map<ID, E> entities) ;
    public abstract void saveEntityToDatabase(E entity);
    public abstract void deleteEntityFromDatabase(ID id);
    public abstract void updateEntityInDatabase(E entity);

    @Override
    public E findOne(ID id) {
        if (id==null) throw new IllegalArgumentException("Id null");
        for(Entity entity:findAll()){
            if(entity.getId().equals(id))
                return (E) entity;
        }
        return null;
    }

    @Override
    public Iterable<E> findAll() {
        entities.clear();
        populateEntities(entities);
        return entities.values();
    }

    @Override
    public E save(E entity) {
        if(entity == null) throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        if(findOne(entity.getId())!=null) return entity;
        saveEntityToDatabase(entity);
        return null;
    }

    @Override
    public E delete(ID id) {
        if(id==null)throw new IllegalArgumentException("Id null");
        E entity=findOne(id);
        if(entity==null) return null;
        deleteEntityFromDatabase(id);
        return entity;
    }

    @Override
    public E update(E entity) {
        if(entity == null) throw new IllegalArgumentException("entity must be not null");
        if(findOne(entity.getId())==null) return entity;
        validator.validate(entity);
        updateEntityInDatabase(entity);
        return null;
    }

    public Connection getDbConnection() {
        return dbConnection;
    }
}
