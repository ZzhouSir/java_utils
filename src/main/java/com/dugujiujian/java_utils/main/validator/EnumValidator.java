package com.dugujiujian.java_utils.main.validator;

import com.dugujiujian.java_utils.main.annotation.EnumValid;
import com.dugujiujian.java_utils.main.annotation.EnumValue;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: zhouwei
 * @Date: 2022-11-14
 */
@Slf4j
public class EnumValidator implements ConstraintValidator<EnumValid, Object> {
    private List<Object> acceptedValues;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();
        Field[] fields = enumClass.getFields();

        Field fieldVal = null;
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(EnumValue.class)) {
                fieldVal = field;
            }
        }
        Field finalFieldVal = fieldVal;
        acceptedValues = Stream.of(enumClass.getEnumConstants())
                .map(en -> {
                    try {
                        return finalFieldVal.get(en);
                    } catch (IllegalAccessException e) {
                        log.error("Enum Validator init error", e);
                        return null;
                    }
                }).filter(Objects::nonNull).collect(Collectors.toList());
    }


    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (null == value) {
            return true;
        }
        return acceptedValues.contains(value);
    }
}
