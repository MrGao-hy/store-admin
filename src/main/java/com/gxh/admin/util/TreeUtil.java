package com.gxh.admin.util;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TreeUtil {

    public static <T> List<T> buildTree(
            List<T> list,
            Function<T, String> idGetter,
            Function<T, String> parentIdGetter,
            BiConsumer<T, List<T>> childrenSetter) {

        List<T> roots = list.stream()
                .filter(item -> parentIdGetter.apply(item) == null
                        || "0".equals(parentIdGetter.apply(item)))
                .collect(Collectors.toList());

        for (T root : roots) {
            buildChildren(root, list, idGetter, parentIdGetter, childrenSetter);
        }

        return roots;
    }

    private static <T> void buildChildren(
            T parent,
            List<T> list,
            Function<T, String> idGetter,
            Function<T, String> parentIdGetter,
            BiConsumer<T, List<T>> childrenSetter) {

        List<T> children = list.stream()
                .filter(item -> Objects.equals(parentIdGetter.apply(item), idGetter.apply(parent)))
                .collect(Collectors.toList());

        childrenSetter.accept(parent, children);

        for (T child : children) {
            buildChildren(child, list, idGetter, parentIdGetter, childrenSetter);
        }
    }
}