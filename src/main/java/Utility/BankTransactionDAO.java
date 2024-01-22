package Utility;

import ConnectionHelper.ConnectionHelper;
import Model.BankTransactionDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BankTransactionDAO implements BankTransactions{
    private static Connection connection = ConnectionHelper.createConnection();

    private static String createAccountQuery = "insert into bank_transactions values (?,?,?,?,?,?,?,?,?)";

    private static String checkBlanceQuery = "select account_holder ,  total_balance , password from bank_transactions where account_number = ? and password = ? order by transaction_id desc limit 1 ";

    private static String accountStatementsQuery = "select * from bank_transactions where account_number = ? and password = ? order by transaction_id ";

    private static String transferAmountQuery = "select  account_holder ,  total_balance , password  from bank_transactions where account_number = ?";
    @Override
    public int createAccount(BankTransactionDTO bto) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(createAccountQuery);
            preparedStatement.setInt(1,0);
            preparedStatement.setInt(2,bto.getAccountNumber());
            preparedStatement.setString(3,bto.getAccountHolderName());
            preparedStatement.setDouble(4,bto.getDepositAmount());
            preparedStatement.setDouble(5,bto.getWithdrawAmount());
            preparedStatement.setInt(6,bto.getToAccountNumber());
            preparedStatement.setInt(7,bto.getFromAccountNumber());
            preparedStatement.setDouble(8,bto.getTotalBalance());
            preparedStatement.setString(9,bto.getAccountPassword());

            int count = preparedStatement.executeUpdate();
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int depositAmount(BankTransactionDTO bt) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkBlanceQuery);
            preparedStatement.setInt(1, bt.getAccountNumber());
            preparedStatement.setString(2, bt.getAccountPassword());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                bt.setAccountHolderName(resultSet.getString(1));
               bt.setTotalBalance(bt.getDepositAmount()+resultSet.getDouble(2));

                int count = createAccount(bt);
                return count;
            }

            return 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int withdrawAmount(BankTransactionDTO bt) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkBlanceQuery);
            preparedStatement.setInt(1, bt.getAccountNumber());
            preparedStatement.setString(2,bt.getAccountPassword());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                if (resultSet.getDouble(2) >= bt.getWithdrawAmount()) {
                    bt.setAccountHolderName(resultSet.getString(1));
                    bt.setTotalBalance(resultSet.getDouble(2)-bt.getWithdrawAmount());

                    int count = createAccount(bt);
                    return count;
                }else{
                    System.out.println("Insufficient Balance ");
                }
            }



            return 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<BankTransactionDTO> checkBalance(BankTransactionDTO bt) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkBlanceQuery);

            preparedStatement.setInt(1, bt.getAccountNumber());
            preparedStatement.setString(2,bt.getAccountPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<BankTransactionDTO> accountBalance = new ArrayList<>();
            if (resultSet.next()){
                if (resultSet.getString(3).equals(bt.getAccountPassword())) {
                    bt.setAccountHolderName(resultSet.getString(1));
                    bt.setAccountPassword(resultSet.getString(3));
                    bt.setTotalBalance(resultSet.getDouble(2));

                    accountBalance.add(bt);
                }

            }
            return accountBalance;



//            return 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<BankTransactionDTO> displayAccountStatements(BankTransactionDTO bt) {
        try {


            PreparedStatement preparedStatement = connection.prepareStatement(accountStatementsQuery);
            preparedStatement.setInt(1,bt.getAccountNumber());
            preparedStatement.setString(2, bt.getAccountPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<BankTransactionDTO> accountStatements = new ArrayList<>();
            while (resultSet.next()){
                    bt = new BankTransactionDTO();
                    bt.setTransactionId(resultSet.getInt(1));
                    bt.setAccountHolderName(resultSet.getString(3));
                    bt.setDepositAmount(resultSet.getDouble(4));
                    bt.setWithdrawAmount(resultSet.getDouble(5));
                    bt.setAccountHolderName(resultSet.getString(3));
                    bt.setToAccountNumber(resultSet.getInt(6));
                    bt.setFromAccountNumber(resultSet.getInt(7));
                    bt.setTotalBalance(resultSet.getDouble(8));

                    accountStatements.add(bt);



            }
            return accountStatements;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean transferAmount(BankTransactionDTO bt) {
        int count1 = withdrawAmount(bt);
        bt.setAccountNumber(bt.getToAccountNumber());
        bt.setDepositAmount(bt.getWithdrawAmount());
        bt.setToAccountNumber(0);

        bt.setWithdrawAmount(0);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(transferAmountQuery);
            preparedStatement.setInt(1, bt.getAccountNumber());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                bt.setAccountHolderName(resultSet.getString(1));
                bt.setTotalBalance(resultSet.getDouble(2));
                bt.setAccountPassword(resultSet.getString(3));
               int   count2 = depositAmount(bt);

               if (count1 > 0 && count2 > 0){
                   return true;
               }
            }

            return false;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




    }


}
