public class MyRunnable implements Runnable{
    public void run() {
        int count = 0;
        while (count < 100) {
            System.out.print("hello number " + count++ + " from thread number " + Thread.currentThread().getId());
            if (count % 10 == 9) {
                System.out.println();
            }
        }
    }
}
