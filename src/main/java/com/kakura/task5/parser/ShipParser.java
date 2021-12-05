package com.kakura.task5.parser;

import com.kakura.task5.entity.Ship;

import java.util.List;

public interface ShipParser {
    List<Ship> parse(List<String> stringList);
}
