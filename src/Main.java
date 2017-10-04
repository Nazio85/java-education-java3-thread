import java.io.*;

public class Main {
    private static final Object monitor = new Object();
    private static char nextLetter = 'A';
    private static InputStreamReader reader;
    private static BufferedWriter writer;


    public static void main(String[] args) throws IOException {

        taskOne();
        taskTwo();
        taskThree();
    }

    private static void taskOne() {
//        1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз,
//        порядок должен быть именно ABСABСABС. Используйте wait/notify/notifyAll.

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                synchronized (monitor) {
                    while (nextLetter != 'A') {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print('A');
                    nextLetter = 'B';
                    monitor.notifyAll();
                }

            }

        }).start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                synchronized (monitor) {
                    while (nextLetter != 'B') {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print('B');
                    nextLetter = 'C';
                    monitor.notifyAll();
                }
            }

        }).start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                synchronized (monitor) {
                    while (nextLetter != 'C') {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print('C');
                    nextLetter = 'A';
                    monitor.notifyAll();
                }
            }

        }).start();
    }

    private static void taskTwo() throws IOException {
        //2. Написать совсем небольшой метод, в котором 3 потока построчно пишут
        // данные в файл (штук по 10 записей, с периодом в 20 мс)

        reader = new InputStreamReader(new FileInputStream("src/test.txt"));
        writer = new BufferedWriter(new FileWriter("src/file.txt", true));

        new Thread(() -> {
            printString(reader);
        }).start();

        new Thread(() -> {
            printString(reader);

        }).start();

        new Thread(() -> {
            printString(reader);
        }).start();
    }

    private static void printString(InputStreamReader reader) {
        try {
            synchronized (monitor) {
                int tmp = -1;
                int count = 0;
                while ((tmp = reader.read()) != -1) {
                    writer.write(tmp);

                    writer.flush();
                    count++;
                    if (count % 10 == 0) {
                        monitor.wait(20);
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void taskThree() {
        //3. Написать класс МФУ, на котором возможны одновременная печать и сканирование документов,
        // при этом нельзя одновременно печатать два документа или сканировать (при печати в консоль
        // выводится сообщения "отпечатано 1, 2, 3,... страницы", при сканировании тоже самое только
        // "отсканировано...", вывод в консоль все также с периодом в 50 мс.)

        new Thread(() -> {
            MFU.print();
            MFU.scaner();
        }).start();
        new Thread(() -> {
            MFU.print();
            MFU.scaner();
        }).start();
        new Thread(() -> {
            MFU.print();
            MFU.scaner();
        }).start();

    }

}

