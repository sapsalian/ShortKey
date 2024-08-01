package com.shotty.shotty.global.util;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class PatchUtil {
    public static <S, T> void applyPatch(T original, S patch) {
        Field[] patchFields = patch.getClass().getDeclaredFields();
        Map<String, Field> OriginalFieldsMap = getFieldMap(original.getClass());

        for (Field patchField : patchFields) {
            if (!OriginalFieldsMap.containsKey(patchField.getName())) {
                continue;
            }

            patchField.setAccessible(true);
            Object patchValue = ReflectionUtils.getField(patchField, patch);
            patchField.setAccessible(false);

            if (patchValue == null) {
                continue;
            }

            Field originalField = OriginalFieldsMap.get(patchField.getName());

            originalField.setAccessible(true);
            ReflectionUtils.setField(originalField, original, patchValue);
            originalField.setAccessible(false);
        }
    }

    private static Map<String, Field> getFieldMap(Class<?> clazz) {
        Map<String, Field> fieldMap = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            fieldMap.put(field.getName(), field);
        }

        return fieldMap;
    }
}