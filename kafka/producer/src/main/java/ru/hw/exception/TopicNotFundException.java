package ru.hw.exception;

public class TopicNotFundException extends RuntimeException{

    public TopicNotFundException(String message) {
        super(message);
    }
}
