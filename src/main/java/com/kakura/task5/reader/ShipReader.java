package com.kakura.task5.reader;

import com.kakura.task5.exception.CustomException;

import java.util.List;

public interface ShipReader {
    List<String> read(String path) throws CustomException;
}
