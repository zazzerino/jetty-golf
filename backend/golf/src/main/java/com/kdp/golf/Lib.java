package com.kdp.golf;

import java.util.List;
import java.util.Optional;

public class Lib
{
    /**
     * Returns an Optional from List::indexOf.
     */
    public static <T> Optional<Integer> findIndex(List<T> list, T elem)
    {
        int index = list.indexOf(elem);
        return index == -1
                ? Optional.empty()
                : Optional.of(index);
    }
}
