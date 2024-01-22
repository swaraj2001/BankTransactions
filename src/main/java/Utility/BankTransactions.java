package Utility;

import Model.BankTransactionDTO;

import java.util.ArrayList;

public interface BankTransactions {
    public int createAccount(BankTransactionDTO bto);
    public int depositAmount(BankTransactionDTO bt);
    public int withdrawAmount(BankTransactionDTO bt);

    public ArrayList<BankTransactionDTO> checkBalance(BankTransactionDTO bt);
    public ArrayList<BankTransactionDTO> displayAccountStatements(BankTransactionDTO bt);

    public boolean transferAmount(BankTransactionDTO bt);

}
