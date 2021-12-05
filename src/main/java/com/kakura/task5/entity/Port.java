package com.kakura.task5.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static final Logger logger = LogManager.getLogger();

    private static final int NUMBER_OF_PIERS = 10;

    private static Port instance;
    private static AtomicBoolean isExist = new AtomicBoolean(false);
    private static Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private Deque<Pier> piers = new ArrayDeque<>();
    private AtomicInteger amountOfContainers = new AtomicInteger();

    private Port() {
        for (int i = 0; i < NUMBER_OF_PIERS; i++) {
            piers.add(new Pier());
        }
    }

    public static Port getInstance() {
        if (!isExist.get()) {
            lock.lock();
            try {
                if (!isExist.get()) {
                    instance = new Port();
                    isExist.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Pier getPier() {
        lock.lock();
        Pier pier = null;
        try {
            while (piers.isEmpty()) {
                condition.await();
            }
            pier = piers.poll();
            logger.info(Thread.currentThread().getName() + " gets pier " + pier.getPierId());
        } catch (InterruptedException e) {
            logger.error("Interrupted exception on " + Thread.currentThread().getName());
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
        return pier;
    }

    public void releasePier(Pier pier) {
        lock.lock();
        try {
            piers.add(pier);
            logger.info(Thread.currentThread().getName() + " releases pier " + pier.getPierId());
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void addContainers(int amount) {
        amountOfContainers.getAndAdd(amount);
    }

    public void removeContainers(int amount) {
        amountOfContainers.getAndAdd(amount * (-1));
    }

}
