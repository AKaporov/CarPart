package ru.hw.enums;

import lombok.RequiredArgsConstructor;

/**
 * @author Artem
 * Константы для всех topic-ов
 * <p>
 * пример использования Enum в качестве Constant (по примеру из книги Чистый код Роберт Мартин)
 */
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
