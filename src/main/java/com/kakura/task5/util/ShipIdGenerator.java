package com.kakura.task5.util;

public class ShipIdGenerator {

    private static long counter;

    public static long generateId() {
        return counter++;
    }
}
