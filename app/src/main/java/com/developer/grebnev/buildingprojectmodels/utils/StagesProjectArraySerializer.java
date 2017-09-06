package com.developer.grebnev.buildingprojectmodels.utils;

import com.activeandroid.serializer.TypeSerializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Grebnev on 07.05.2017.
 */

public class StagesProjectArraySerializer extends TypeSerializer {
    @Override
    public Class<?> getDeserializedType() {
        return List.class;
    }

    @Override
    public Class<?> getSerializedType() {
        return String.class;
    }

    @Override
    public Object serialize(Object data) {
        if (data == null) {
            return null;
        }

        String str = "";
        for (String s : (List<String>) data) {
            str = str + s + ",";
        }

        return str;
    }

    @Override
    public Object deserialize(Object data) {
        if (data == null) {
            return null;
        }

        List<String> strings = new ArrayList<>();
        String str = (String) data;
        Collections.addAll(strings, str.split(","));

        return strings;
    }
}
