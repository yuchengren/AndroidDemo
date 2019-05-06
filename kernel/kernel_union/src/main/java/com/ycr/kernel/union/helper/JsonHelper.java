package com.ycr.kernel.union.helper;

import android.content.Context;

import com.ycr.kernel.json.parse.IJsonElement;
import com.ycr.kernel.json.parse.IJsonParser;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

/**
 * created by yuchengren on 2019/4/22
 */
public class JsonHelper{

    private static IJsonParser jsonParser = UnionContainer.INSTANCE.getJsonParser();

    public static String toJson(@Nullable Object any) {
        return jsonParser.toJson(any);
    }

    public static <T> T fromJson(@Nullable String json, @Nullable Type type) {
        return jsonParser.fromJson(json,type);
    }

    public static <T> T fromJson(@Nullable IJsonElement json, @Nullable Type type) {
        return jsonParser.fromJson(json,type);
    }

    public static IJsonElement parseJsonToElement(@Nullable String json) {
        return jsonParser.parseJsonToElement(json);
    }
}
