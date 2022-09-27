import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        // print array
        int myarray [] = {2,3,4,5,6};
        int result = 0;
        for (int i = 0; i < myarray.length; i++) {
            System.out.println(myarray[i]);
            result += myarray[i];
        }
        System.out.println(result);

        // input from users
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Enter name: ");

        String userName = myScanner.nextLine();
        System.out.println("username: " + userName);

        System.out.println("Enter age: ");
        int age = myScanner.nextInt();
        if (age > 18) {
            System.out.println("you can vote");
        }else {
            System.out.println("you can not vote");
        }
    }
}