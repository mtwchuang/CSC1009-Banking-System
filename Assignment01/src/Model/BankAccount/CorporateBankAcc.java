package Model.BankAccount;

import java.util.List;
import java.util.UUID;

public class CorporateBankAcc extends BankAccount{
    private List<UUID> bankAccSubHolderIDs;
    private List<UUID> bankAccTransactOnlyIDs;
    private List<UUID> bankAccDepositOnlyIDs;

    public CorporateBankAcc(UUID bankAccHolderID){
        super(bankAccHolderID);
    }

    public List<UUID> getBankAccTransactOnlyIDs(){
        return bankAccTransactOnlyIDs;
    }
    public List<UUID> getBankAccSubHolderIDs(){
        return bankAccSubHolderIDs;
    }
    public List<UUID> getBankAccDepositOnlyIDs(){
        return bankAccDepositOnlyIDs;
    }

    public void setBankAccSubHolderID(List<UUID> backAccSubHolderIDs){
        this.bankAccSubHolderIDs = backAccSubHolderIDs;
        updated();
    }
    public void setBankAccTransactOnlyIDs(List<UUID> bankAccTransactOnlyIDs){
        this.bankAccTransactOnlyIDs = bankAccTransactOnlyIDs;
        updated();
    }
    public void setBankAccDepositOnlyIDs(List<UUID> bankAccDepositOnlyIDs){
        this.bankAccDepositOnlyIDs = bankAccDepositOnlyIDs;
        updated();
    }
}