package ru.kpfu.itis.chat.lobanov.chatservice.utils;

public class ChatServiceConstants {
    public static final String APPLICATION_BROKER_PREFIX = "/app";
    public static final String USER_DESTINATION_BROKER_PREFIX = "/user";
    public static final String DESTINATION_BROKER_PREFIX = "/user";
    public static final String MESSAGE_DESTINATION = "/queue/messages";
    public static final String BROKER_ENDPOINT_PATH = "/ws";
    public static final String ORIGIN_PATTERN = "*";
    public static final String ALLOWED_HEADERS_PATTERN = "*";
    public static final String OPTIONS_METHOD_NAME = "OPTIONS";
    public static final String HEAD_METHOD_NAME = "HEAD";
    public static final String GET_METHOD_NAME = "GET";
    public static final String PUT_METHOD_NAME = "PUT";
    public static final String POST_METHOD_NAME = "POST";
    public static final String DELETE_METHOD_NAME = "DELETE";
    public static final String PATCH_METHOD_NAME = "PATCH";
    public static final String CORS_CONFIGURATION_PATTERN = "/**";
    public static final String SENDER_ID_KEY = "senderId";
    public static final String RECIPIENT_ID_KEY = "recipientId";
    public static final String STATUS_KEY = "status";
    public static final String CHAT_ID_TEMPLATE = "%s_%s";
}
