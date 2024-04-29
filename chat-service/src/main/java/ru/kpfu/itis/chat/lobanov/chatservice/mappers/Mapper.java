package ru.kpfu.itis.chat.lobanov.chatservice.mappers;

import java.util.List;

public interface Mapper<T, D> {
    D toResponse(T entity);

    List<D> toListResponse(List<T> set);
}
