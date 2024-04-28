package ru.kpfu.itis.lobanov.data.mappers;

import java.util.List;

public interface Mapper<T, D> {
    D toResponse(T entity);

    List<D> toListResponse(List<T> set);
}
