package sample.enums;

import lombok.Getter;

@Getter
public enum TestEnum {
    ONE(1, "one message"), TWO(2, "two message"), THREE(3, "three message")
    ;

    TestEnum(int hi, String message) {
        this.hi = hi;
        this.message = message;
    }

    private final int hi;
    private final String message;
}
