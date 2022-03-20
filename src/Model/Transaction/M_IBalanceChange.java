package Model.Transaction;
public interface M_IBalanceChange extends M_ITransaction
{
    // getter and setter abstract methods for M_BalanceChange
    public boolean isExecutedOnAtm();
    public String getAtmID();
    public boolean isExecutedOnPurchase();
    //public void setExecutedOnAtm(boolean executedOnAtm);
    public void setExecutedOnPurchase(boolean executedOnPurchase);
    //public void setAtmID(String atmID);

    public void executeOnAtm();
}
