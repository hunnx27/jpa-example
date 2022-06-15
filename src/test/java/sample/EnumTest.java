package sample;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import sample.enums.TestEnum;
import sample.enums.TestEnum2;

public class EnumTest {
    public enum InnerEnum{
        SAY, HELLO, TWO
    }
    @RequiredArgsConstructor
    @Getter
    public enum InnerEnum2{
        ERROR500("500","Server Error"), ERROR404("404", "NOT FOUND");

        private final String code;
        private final String message;
        @Override
        public String toString() {
            return String.format("[code] : '%s' | [message] : '%s'", code, message);
        }
    }
    public enum InnerEnum3{
        BAD_REQUEST(400, HttpStatus.BAD_REQUEST.getReasonPhrase()), FORBIDDEN(403, HttpStatus.FORBIDDEN.getReasonPhrase());

        private final int code;
        private final String message;

        InnerEnum3(int code, String message) {
            this.code = code;
            this.message = message;
        }
        public int getCode() {
            return code;
        }
        public String getMessage() {
            return message;
        }
    }
    @Test
    void hello(){
        System.out.println(TestEnum.ONE);//return enum
        System.out.println(TestEnum.TWO);
        System.out.println(TestEnum.TWO.getHi());
        System.out.println(TestEnum.TWO.getMessage());
        System.out.println(TestEnum.ONE.getHi());
        System.out.println(TestEnum.ONE.getMessage());
        System.out.println(TestEnum2.ONE);
        System.out.println(InnerEnum.HELLO);
        System.out.println(InnerEnum2.ERROR404);
        System.out.println(InnerEnum2.ERROR404.getCode());
        System.out.println(InnerEnum2.ERROR404.getMessage());
        System.out.println(InnerEnum3.FORBIDDEN);
        System.out.println(InnerEnum3.FORBIDDEN.getCode());
        System.out.println(InnerEnum3.FORBIDDEN.getMessage());
        System.out.println(HttpStatus.BAD_REQUEST);

    }

}
