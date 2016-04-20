package com.KNS.contigs;

import java.util.List;

/**
 * Created by kodoo on 14.02.2016.
 */
public class Lisp {

    public static Integer head(List<Integer> list) {
        return list.get(0);
    }

    public static List<Integer> tail(List<Integer> list) {
        return list.subList(1, list.size());
    }
}
