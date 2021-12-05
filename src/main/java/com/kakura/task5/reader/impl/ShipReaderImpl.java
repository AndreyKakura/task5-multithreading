package com.kakura.task5.reader.impl;

import com.kakura.task5.exception.CustomException;
import com.kakura.task5.reader.ShipReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShipReaderImpl implements ShipReader {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public List<String> read(String path) throws CustomException {
        List<String> stringList;

        try(BufferedReader reader = new BufferedReader(new FileReader(path));
            Stream<String> stream = reader.lines()) {
                stringList = stream.collect(Collectors.toList());
                return stringList;
        } catch (FileNotFoundException e) {
                logger.error("File not found");
                throw new CustomException("File not found", e);
        } catch (IOException e) {
            logger.error("IOException");
            throw new CustomException("IOException", e);
        }

    }
}
