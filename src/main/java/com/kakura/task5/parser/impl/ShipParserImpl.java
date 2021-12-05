package com.kakura.task5.parser.impl;

import com.kakura.task5.entity.Ship;
import com.kakura.task5.parser.ShipParser;

import java.util.ArrayList;
import java.util.List;

public class ShipParserImpl implements ShipParser {
    @Override
    public List<Ship> parse(List<String> stringList) {
        List<Ship> ships = new ArrayList<>();
        for (String string : stringList) {
            ships.add(new Ship(Ship.Purpose.valueOf(string)));
        }
        return ships;
    }
}
