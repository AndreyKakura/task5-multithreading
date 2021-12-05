package com.kakura.task5.entity;

import com.kakura.task5.util.ShipIdGenerator;

public class Ship extends Thread {

    private static final int SHIP_CAPACITY = 100;
    private long shipId;
    private ShipState shipState;
    private Purpose purpose;

    public enum ShipState {
        NEW, PROCESSING, COMPLETED
    }

    public enum Purpose {
        LOADING, UNLOADING
    }

    public Ship(Purpose purpose) {
        shipId = ShipIdGenerator.generateId();
        shipState = ShipState.NEW;
        this.purpose = purpose;
    }

    @Override
    public void run() {
        this.shipState = ShipState.PROCESSING;

        Port port = Port.getInstance();
        Pier pier = port.getPier();

        try {
            switch (purpose) {
                case LOADING -> pier.loadShip();
                case UNLOADING -> pier.unloadShip();
            }
        } finally {
            port.releasePier(pier);
        }
        this.shipState = ShipState.COMPLETED;
    }

    public static int getShipCapacity() {
        return SHIP_CAPACITY;
    }

    public long getShipId() {
        return shipId;
    }

    public ShipState getShipState() {
        return shipState;
    }

    public Purpose getPurpose() {
        return purpose;
    }
}
