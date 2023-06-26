package HW12;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class FizzBuzz {
    private int n;
    private int currentNumber;
    private Lock lock;
    private Condition fizzCondition;
    private Condition buzzCondition;
    private Condition fizzBuzzCondition;
    private Condition numberCondition;

    public FizzBuzz(int n) {
        this.n = n;
        this.currentNumber = 1;
        this.lock = new ReentrantLock();
        this.fizzCondition = lock.newCondition();
        this.buzzCondition = lock.newCondition();
        this.fizzBuzzCondition = lock.newCondition();
        this.numberCondition = lock.newCondition();
    }

    public void fizz() throws InterruptedException {
        while (true) {
            lock.lock();
            try {
                fizzCondition.await();
                if (currentNumber > n) {
                    return;
                }
                if (currentNumber % 3 == 0 && currentNumber % 5 != 0) {
                    System.out.println("fizz");
                    currentNumber++;
                    buzzCondition.signal();
                    fizzBuzzCondition.signal();
                    numberCondition.signal();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public void buzz() throws InterruptedException {
        while (true) {
            lock.lock();
            try {
                buzzCondition.await();
                if (currentNumber > n) {
                    return;
                }
                if (currentNumber % 5 == 0 && currentNumber % 3 != 0) {
                    System.out.println("buzz");
                    currentNumber++;
                    fizzCondition.signal();
                    fizzBuzzCondition.signal();
                    numberCondition.signal();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public void fizzBuzz() throws InterruptedException {
        while (true) {
            lock.lock();
            try {
                fizzBuzzCondition.await();
                if (currentNumber > n) {
                    return;
                }
                if (currentNumber % 3 == 0 && currentNumber % 5 == 0) {
                    System.out.println("fizzbuzz");
                    currentNumber++;
                    numberCondition.signal();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public void number() throws InterruptedException {
        while (true) {
            lock.lock();
            try {
                numberCondition.await();
                if (currentNumber > n) {
                    return;
                }
                if (currentNumber % 3 != 0 && currentNumber % 5 != 0) {
                    System.out.println(currentNumber);
                    currentNumber++;
                    fizzCondition.signal();
                    buzzCondition.signal();
                    fizzBuzzCondition.signal();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        int n = 15;
        FizzBuzz fizzBuzz = new FizzBuzz(n);

        Thread threadA = new Thread(() -> {
            try {
                fizzBuzz.fizz();
            } catch (InterruptedException e) {
                e.getMessage();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                fizzBuzz.buzz();
            } catch (InterruptedException e) {
                e.getMessage();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                fizzBuzz.fizzBuzz();
            } catch (InterruptedException e) {
                e.getMessage();
            }
        });

        Thread threadD = new Thread(() -> {
            try {
                fizzBuzz.number();
            } catch (InterruptedException e) {
                e.getMessage();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        try {
            threadA.join();
            threadB.join();
            threadC.join();
            threadD.join();
        } catch (InterruptedException e) {
            e.getMessage();
        }
    }
}