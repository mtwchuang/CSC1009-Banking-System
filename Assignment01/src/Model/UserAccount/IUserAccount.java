package Model.UserAccount;

import java.util.List;
import java.util.UUID;

public interface IUserAccount {
    public UUID getCreatedBy();
    public long getCreatedAt();
    public UUID getUpdatedBy();
    public long getUpdatedAt();
    public UUID getUserID();
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
    public void setUserAccID(UUID userAccID);
    public void setUserBankAccounts(List<Long> userBankAccounts);
}