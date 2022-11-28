package com.turtle.turtlebooks.app.member.entity.level;

import com.turtle.turtlebooks.app.member.exception.NoMatchAuthLevelException;
import lombok.Getter;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;

@Getter
public enum AuthLevel {

    NORMAL(3, "NORMAL") ,
    ADMIN(7, "ADMIN");

    AuthLevel(int code, String value) {
        this.code = code;
        this.value = value;
    }

    private int code;
    private String value;

    public static class Converter implements AttributeConverter<AuthLevel, Integer> {

        // AuthLevel을 code로 변환
        // DB값으로 변환
        public Integer convertToDatabaseColumn(AuthLevel attribute) {
            return attribute.getCode();
        }

        // Entity 값으로 변환
        @Override
        public AuthLevel convertToEntityAttribute(Integer dbData) {
            return EnumSet.allOf(AuthLevel.class).stream()
                    .filter(e -> e.getCode() == dbData)
                    .findAny()
                    .orElseThrow(NoMatchAuthLevelException::new);
        }
    }

}
