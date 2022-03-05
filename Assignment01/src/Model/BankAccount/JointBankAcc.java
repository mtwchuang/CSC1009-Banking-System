package Model.BankAccount;

public class JointBankAcc extends BankAccount{
    private String bankAccSubHolderID;

    public JointBankAcc(String bankAccHolderID){
        super(bankAccHolderID);
    }

    public String getBankAccSubHolderID(){
        return bankAccSubHolderID;
    }

    public void setBankAccSubHolderID(String bankAccSubHolderID){
        this.bankAccSubHolderID = bankAccSubHolderID;
        updated();
    }
}