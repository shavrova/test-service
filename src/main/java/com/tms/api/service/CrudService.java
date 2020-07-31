package com.tms.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudService<T, String> {

    T create(T dto);

    T update(T dto);

    T getById(String id);

    Page<T> findPage(Pageable page);

    void deleteById(String id);

}