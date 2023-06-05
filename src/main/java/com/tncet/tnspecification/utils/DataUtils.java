package com.tncet.tnspecification.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.alibaba.fastjson.JSONObject;

public class DataUtils {
    public static void copyProperties(Object source, Object target, String[] properties) {
        for (String name : properties) {
            try {
                Field sourceField = source.getClass().getDeclaredField(name);
                Field targetField = target.getClass().getDeclaredField(name);
                sourceField.setAccessible(true);
                targetField.setAccessible(true);
                targetField.set(target, sourceField.get(source));
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    public static void copyProperties(Object source, Object target) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
        }
        String[] emptyNamesArray = new String[emptyNames.size()];
        emptyNames.toArray(emptyNamesArray);
        BeanUtils.copyProperties(source, target, emptyNamesArray);
    }

    // json对象转为map
    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> JSONObjectToMap(JSONObject j) {
        Map<String, T> map = new HashMap<>();
        try {
            for (String key : j.keySet()) {
                Object value = j.get(key);
                if (value != null) {
                    map.put(key, (T) value);
                }
            }
            return map;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    public static Map<String, Double> JSONObjectToDoubleMap(JSONObject j) {
        Map<String, Double> map = new HashMap<>();
        try {
            for (String key : j.keySet()) {
                Object value = j.get(key);
                if (value != null) {
                    map.put(key, Double.valueOf(value.toString()));
                }
            }
            return map;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    public static <T> JSONObject MapToJSONObject(Map<String, T> m) {
        JSONObject jsonObject = new JSONObject();
        for (String key : m.keySet()) {
            Object value = m.get(key);
            if (value != null) {
                jsonObject.put(key, value);
            }
        }
        return jsonObject;
    }
}
