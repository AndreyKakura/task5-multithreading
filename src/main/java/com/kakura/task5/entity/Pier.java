package com.kakura.task5.entity;

import com.kakura.task5.util.PierIdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Pier {
    private static final Logger logger = LogManager.getLogger();

    private static final int MAX_LOADING_TIME = 100;

    private long pierId;

    public Pier() {
        pierId = PierIdGenerator.generateId();
    }

    public long getPierId() {
        return pierId;
    }

    public void loadShip() {
        Port port = Port.getInstance();
        port.removeContainers(Ship.getShipCapacity());
        try {
            logger.info(Thread.currentThread().getName() + " loading");
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(MAX_LOADING_TIME));
        } catch (InterruptedException e) {
            logger.error("Interrupted exception on " + Thread.currentThread().getName());
            Thread.currentThread().interrupt();
        }
    }

    public void unloadShip() {
        Port port = Port.getInstance();
        port.addContainers(Ship.getShipCapacity());
        try {
            logger.info(Thread.currentThread().getName() + " unloading");
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(MAX_LOADING_TIME));
        } catch (InterruptedException e) {
            logger.error("Interrupted exception on " + Thread.currentThread().getName());
            Thread.currentThread().interrupt();
        }
    }
}
