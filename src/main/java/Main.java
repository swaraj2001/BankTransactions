import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Number : ");
        int number = sc.nextInt();

        System.out.println("Enter Your Name :");
        sc.nextLine();
        String name = sc.nextLine();

        System.out.println(number + " " +name);
    }
}
