package ru.kpfu.itis.lobanov.utils;

import ru.kpfu.itis.lobanov.data.entities.User;

public final class CurrentUserContext {
    private static User user;

    public static User getCurrentUser() {
        return user;
    }

    public static void setCurrentUser(User user) {
        CurrentUserContext.user = user;
    }

    private CurrentUserContext() {}
}
