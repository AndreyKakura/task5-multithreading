package com.kakura.task5.main;

import com.kakura.task5.entity.Ship;
import com.kakura.task5.exception.CustomException;
import com.kakura.task5.parser.ShipParser;
import com.kakura.task5.parser.impl.ShipParserImpl;
import com.kakura.task5.reader.ShipReader;
import com.kakura.task5.reader.impl.ShipReaderImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws CustomException {
        ShipReader reader = new ShipReaderImpl();
        ShipParser parser = new ShipParserImpl();
        List<String> stringList = reader.read("src/main/resources/data/ships.txt");
        List<Ship> ships = parser.parse(stringList);

        ExecutorService executorService = Executors.newFixedThreadPool(ships.size());
        ships.forEach(executorService::execute);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            logger.error("Interrupted Exception");
        }
        executorService.shutdown();
    }
}
