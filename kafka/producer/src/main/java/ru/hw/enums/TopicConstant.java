package ru.hw.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TopicConstant {
    PARTITION("партиция") {
        public int count() {
            return 1;
        }
    },
    REPLICA("реплика") {
        public int count() {
            return 1;
        }
    };

    private final String description;

    public abstract int count();

}
