public class Answer {
    public static int number;
    private int answer = 0;
    private final int maxValue = 40000;
    private final int numThreads = 10;
    public void badSum() throws InterruptedException {
        for(int i = 0; i < numThreads; i++ ){
            int finalI = i;
            Thread singleThread = new Thread(() -> {
                for(int num = finalI *maxValue/numThreads; num <= Math.min((finalI +1)*maxValue/numThreads, maxValue); num++){
                    answer += num;
                }
                int min = Math.min((finalI + 1) * maxValue / numThreads, maxValue);
                System.out.println("thread id " + Thread.currentThread().getId() + "start " + finalI *maxValue/numThreads + " final " + min);
            });
            singleThread.start();
            singleThread.join();
            System.out.println(answer);
        }
    }
}
