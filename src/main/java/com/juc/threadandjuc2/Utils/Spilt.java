package com.juc.threadandjuc2.Utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liu
 * @create 2023-09-30-11:19
 */
public class Spilt {
    // 使用并行流处理
    public static <T> List<List<T>> partition(final List<T> list, final int size) {
        Integer limit = (list.size() + size - 1) / size;
        List<List<T>> splitList = Stream.iterate(0, n -> n + 1).limit(limit).parallel()
                .map(a -> list.stream().skip(a * size).limit(size).parallel().collect(Collectors.toList()))
                .collect(Collectors.toList());
        return splitList;
    }
}
