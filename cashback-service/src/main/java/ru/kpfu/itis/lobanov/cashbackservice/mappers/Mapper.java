package ru.kpfu.itis.lobanov.cashbackservice.mappers;

import java.util.List;

public interface Mapper<T, D> {
    D toResponse(T entity);

    List<D> toListResponse(List<T> set);
}
