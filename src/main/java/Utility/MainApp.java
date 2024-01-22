package Utility;

import Model.BankTransactionDTO;

import java.util.ArrayList;
import java.util.Scanner;

public class MainApp {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        operations();
    }

    private static void operations(){
        boolean flag1 = true;
        while (flag1) {
            System.out.println("-----------------------------------------------------------");
            System.out.println("Select Your Choice ");
            System.out.println(" 1 : Create Account ");
            System.out.println(" 2 : Perform Transactions ");
            System.out.println(" 3 : Exit ");

            System.out.println("Enter Your Choice : ");
            int choice = sc.nextInt();
            System.out.println("-----------------------------------------------------------");

            switch (choice) {
                case 1:
                    createBankAccount();
                    break;
                case 2:
                    performTransactions();
                    break;
                case 3:
                    System.out.println("Thank You For Visiting !");
                    flag1 = false;
                    break;
                default:
                    System.out.println("Invalid Choice !");
            }
        }

    }

    private static void performTransactions(){
        boolean flag1 = true;
        while (flag1) {
            System.out.println("-----------------------------------------------------------");
            System.out.println("Select Your Choice ");
            System.out.println(" 1 : Deposit Amount  ");
            System.out.println(" 2 : Withdraw Amount  ");
            System.out.println(" 3 : Display Account Statements  ");
            System.out.println(" 4 : Display Balance   ");
            System.out.println(" 5 : Transfer Amount  ");
            System.out.println(" 6 : Exit  ");

            System.out.println("Enter Your Choice : ");
            int choice = sc.nextInt();
            System.out.println("-----------------------------------------------------------");

            switch (choice) {
                case 1:
                    depositAmount();
                    break;
                case 2:
                    withdrawAmount();
                    break;
                case 3:
                    displayAccountStatements();
                    break;
                case 4:
                    checkBalence();
                    break;
                case 5:
                    transferAmount();
                    break;
                case 6:
                    System.out.println("Thank You For Visiting !");
                    flag1 = false;

                    break;
                default:
                    System.out.println("Invalid Choice !");
            }
        }
    }

    private static void createBankAccount(){
        System.out.println("Enter Account Holder Name : ");
        sc.nextLine();
        String accountHolderName = sc.nextLine();
        System.out.println("Enter Account Number : ");
        int accountNumber = sc.nextInt();

        System.out.println("Enter Deposit Amount :");
        double depositAmount = sc.nextDouble();

        System.out.println("Enter Your Password :");
        sc.nextLine();
        String accountPassword = sc.nextLine();


        if (depositAmount < 5000) {
            System.out.println("Minimum Amount should be 5000 or Greater ");
            return;
        }

        BankTransactionDTO bto = new BankTransactionDTO();

        bto.setAccountNumber(accountNumber);
        bto.setAccountHolderName(accountHolderName);
        bto.setDepositAmount(depositAmount);
        bto.setTotalBalance(depositAmount);



        BankTransactions bt = new BankTransactionDAO();
        int result = bt.createAccount(bto);

        if (result > 0){
            System.out.println("Account Created  Successfully ");
        }else {
            System.out.println("Something Went Wrong !");
        }
    }

    private static void depositAmount(){

        System.out.println("Enter Account Number : ");
        int accountNumber = sc.nextInt();

        System.out.println("Enter Deposit Amount :");

        double depositAmount = sc.nextDouble();

        System.out.println("Enter Your Password :");
        sc.nextLine();
        String accountPassword = sc.nextLine();

        if (depositAmount>0) {
            BankTransactionDTO bto = new BankTransactionDTO();
            bto.setDepositAmount(depositAmount);
            bto.setAccountNumber(accountNumber);
            bto.setAccountPassword(accountPassword);
            BankTransactions bt = new BankTransactionDAO();

            int result = bt.depositAmount(bto);

            if (result > 0) {
                System.out.println(depositAmount + " Deposited successfully ");
            } else {
                System.out.println("Invalid  Credentials!");
            }
        }
        else {
            System.out.println("Invalid Amount !");
        }
    }

    private static void withdrawAmount(){
        System.out.println("Enter Account Number : ");
        int accountNumber = sc.nextInt();

        System.out.println("Enter Withdraw Amount :");

        double withdrawAmount = sc.nextDouble();

        System.out.println("Enter Your Password :");
        sc.nextLine();
        String accountPassword = sc.nextLine();

        BankTransactionDTO bt = new  BankTransactionDTO();

        bt.setAccountNumber(accountNumber);
        bt.setWithdrawAmount(withdrawAmount);
        bt.setAccountPassword(accountPassword);

        BankTransactions b = new BankTransactionDAO();

        int result = b.withdrawAmount(bt);
        if (result > 0){
            System.out.println(withdrawAmount +  " Withdraw Successfully  ");
        }else {
            System.out.println("Invalid Credentials !");
        }
    }

    private static void checkBalence(){
        System.out.println("Enter Account Number : ");
        int accountNumber = sc.nextInt();



        System.out.println("Enter Your Password :");
        sc.nextLine();
        String accountPassword = sc.nextLine();

        BankTransactionDTO bt = new  BankTransactionDTO();

        bt.setAccountNumber(accountNumber);

        bt.setAccountPassword(accountPassword);

        BankTransactions b = new BankTransactionDAO();

        ArrayList<BankTransactionDTO > list = b.checkBalance(bt);
        for (BankTransactionDTO bto : list){
            bto.setTotalBalance(bto.getTotalBalance());
            bto.setAccountHolderName(bto.getAccountHolderName());

        }

        if (!list.isEmpty()) {
            System.out.println("Account Details : ");
            System.out.println(bt.getAccountNumber() + "\t" + bt.getAccountHolderName() + "\t" + bt.getTotalBalance());

        }
        else {
            System.out.println("Invalid Credentials !");
        }

    }

    private static void displayAccountStatements(){
        System.out.println("Enter Account Number : ");
        int accountNumber = sc.nextInt();



        System.out.println("Enter Your Password :");
        sc.nextLine();
        String password = sc.nextLine();


        BankTransactionDTO bt = new  BankTransactionDTO();

        bt.setAccountNumber(accountNumber);

        bt.setAccountPassword(password);

        BankTransactions b = new BankTransactionDAO();
        ArrayList<BankTransactionDTO > list = b.checkBalance(bt);
        for (BankTransactionDTO bto : list){
            bto.setTotalBalance(bto.getTotalBalance());
            bto.setAccountHolderName(bto.getAccountHolderName());

        }

        if (!list.isEmpty()) {
            System.out.println("Account Details : ");
            System.out.println("Account Number \t Account Holder \t Total Balance ");
            System.out.println(bt.getAccountNumber() + "\t" + bt.getAccountHolderName() + "\t" + bt.getTotalBalance());



            ArrayList<BankTransactionDTO> accountStatements = b.displayAccountStatements(bt);


            if (!accountStatements.isEmpty()){
                System.out.println("TransactionId   Deposit Amount   Withdraw Amount  From Account  To Account  Total Balance ");
                for(BankTransactionDTO bto : accountStatements){
                    System.out.println(bto.getTransactionId()+"\t\t\t\t" + bto.getDepositAmount()+"\t\t\t\t"+ bto.getWithdrawAmount()+"\t\t\t\t" + bto.getToAccountNumber()+"\t\t\t\t"+bto.getFromAccountNumber()+"\t\t\t\t"+ bto.getTotalBalance());
                }
            }
            else {
                System.out.println("Something Went Wrong !");
            }
        }
        else {
            System.out.println("Invalid Credentials !");
        }
    }

    private static void transferAmount(){
        System.out.println("Enter Your Credentials : ");
        System.out.println("Enter Your Account Number : ");
        int accountNumber = sc.nextInt();
        System.out.println("Enter Your Password : ");
        sc.nextLine();
        String accountPassword = sc.nextLine();

        System.out.println("Enter Amount to be Transfer : ");
        double amount = sc.nextDouble();

        System.out.println("Enter Beneficiary’s Account Number : ");
        int beneficiaryAccountNumber = sc.nextInt();

        BankTransactionDTO bt = new BankTransactionDTO();
        bt.setAccountNumber(accountNumber);
        bt.setToAccountNumber(beneficiaryAccountNumber);
        bt.setFromAccountNumber(accountNumber);
        bt.setAccountPassword(accountPassword);
        bt.setWithdrawAmount(amount);

        BankTransactions bto = new BankTransactionDAO();

        boolean result = bto.transferAmount(bt);

        if (result){
            System.out.println("Transaction Completed Successfully ");
        }else {
            System.out.println("Something Went Wrong !");
        }


    }
}
