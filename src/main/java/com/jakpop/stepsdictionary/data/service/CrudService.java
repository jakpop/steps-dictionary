package com.jakpop.stepsdictionary.data.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public abstract class CrudService<T, ID> {
    public CrudService() {
    }

    protected abstract MongoRepository<T, ID> getRepository();

    public Optional<T> get(ID id) {
        return this.getRepository().findById(id);
    }

    public T update(T entity) {
        return this.getRepository().insert(entity);
    }

    public void delete(ID id) {
        this.getRepository().deleteById(id);
    }

    public Page<T> list(Pageable pageable) {
        return this.getRepository().findAll(pageable);
    }

    public int count() {
        return (int)this.getRepository().count();
    }
}
