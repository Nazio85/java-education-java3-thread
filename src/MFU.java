public class MFU {
    private static final Object monitorPrtint = new Object();
    private static final Object monitorScanner = new Object();
    private static int countPrint = 0;
    private static int countScanner = 0;

    static void print(){
        synchronized (monitorPrtint){
            System.out.println("Отпечатано " + ++countPrint);
        }
    }

    static void scaner(){
        synchronized (monitorScanner){
            System.out.println("Отсканировано " + ++countScanner);
        }
    }
}
