package Model.BankAccount;

import java.util.UUID;

public class JointBankAcc extends BankAccount{
    private UUID bankAccSubHolderID;

    public JointBankAcc(UUID bankAccHolderID){
        super(bankAccHolderID);
    }

    public UUID getBankAccSubHolderID(){
        return bankAccSubHolderID;
    }

    public void setBankAccSubHolderID(UUID bankAccSubHolderID){
        this.bankAccSubHolderID = bankAccSubHolderID;
        updated();
    }
}