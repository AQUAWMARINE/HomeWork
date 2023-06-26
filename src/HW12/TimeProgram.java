package HW12;

class TimeProgram {
    public static void main(String[] args) {
        TimeTracker timeTracker = new TimeTracker();
        MessagePrinter messagePrinter = new MessagePrinter(timeTracker);

        Thread timeTrackerThread = new Thread(timeTracker);
        Thread messagePrinterThread = new Thread(messagePrinter);

        timeTrackerThread.start();
        messagePrinterThread.start();
    }

    static class MessagePrinter implements Runnable {
        private TimeTracker timeTracker;

        public MessagePrinter(TimeTracker timeTracker) {
            this.timeTracker = timeTracker;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.getMessage();
                }

                System.out.println("Прошло 5 секунд");

                synchronized (timeTracker) {
                    timeTracker.notifyPrinter();
                }
            }
        }
    }

    static class TimeTracker implements Runnable {
        private long startTime;

        @Override
        public void run() {
            startTime = System.currentTimeMillis();

            while (true) {
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - startTime;

                System.out.println("Прошло времени: " + elapsedTime + " мс");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.getMessage();
                }
            }
        }

        public synchronized void notifyPrinter() {
            notify();
        }
    }
}

