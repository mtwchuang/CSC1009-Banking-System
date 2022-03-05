package Model.UserAccount;

import java.util.List;

public interface M_IUserAccount {
    public String getCreatedBy();
    public long getCreatedAt();
    public String getUpdatedBy();
    public long getUpdatedAt();
    
    public String getUserID();
    public short getUserType();
    public String getUserName();
    public String getUserPassword();
    public String getUserFirstName();
    public String getUserLastName();
    public String getUserAddress();
    public long getUserPhoneNumber();
    public List<Long> getUserBankAccounts();
    
    public void setUserAccType(short userAccType);
    public void setUserName(String userName);
    public void setUserPassword(String userPassword);
    public void setUserFirstName(String userFirstName);
    public void setUserLastName(String userLastName);
    public void setUserAddress(String userAddress);
    public void setUserPhoneNumber(long userPhoneNumber);
    public void setUserBankAccounts(List<Long> userBankAccounts);

    public void updated();
}