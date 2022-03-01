package Model.UserAccount;

import java.util.List;
import java.util.UUID;

import ModelView.Global;

public class UserAccount implements IUserAccount{
    private UUID createdBy;
    private long createdAt;
    private UUID updatedBy;
    private long updatedAt;

    private UUID userID;
    private short userType;
    private String userName;
    private String userPassword;
    private String userFirstName;
    private String userLastName;
    private String userAddress;
    private long userPhoneNumber;

    private List<Long> userBankAccounts;

    public UserAccount(){
        this.createdBy = Global.sessionUserID;
        this.createdAt = System.currentTimeMillis();
        this.updatedBy = Global.sessionUserID;
        this.updatedAt = System.currentTimeMillis();

        this.userID = UUID.randomUUID();
    }

    public UUID getCreatedBy(){
        return createdBy;
    }
    public long getCreatedAt(){
        return createdAt;
    }
    public UUID getUpdatedBy(){
        return updatedBy;
    }
    public long getUpdatedAt(){
        return updatedAt;
    }

    public UUID getUserID(){
        return userID;
    }
    public short getUserType(){
        return userType;
    }
    public String getUserName(){
        return userName;
    }
    public String getUserPassword(){
        return userPassword;
    }
    public String getUserFirstName(){
        return userFirstName;
    }
    public String getUserLastName(){
        return userLastName;
    }
    public String getUserAddress(){
        return userAddress;
    }
    public long getUserPhoneNumber(){
        return userPhoneNumber;
    }
    public List<Long> getUserBankAccounts(){
        return this.userBankAccounts;
    }

    public void setUserAccType(short userType){
        this.userType = userType;
        updated();
    }
    public void setUserName(String userName){
        this.userName = userName;
        updated();
    }
    public void setUserPassword(String userPassword){
        this.userPassword = userPassword;
        updated();
    }
    public void setUserFirstName(String userFirstName){
        this.userFirstName = userFirstName;
        updated();
    }
    public void setUserLastName(String userLastName){
        this.userLastName = userLastName;
        updated();
    }
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
        updated();
    }
    public void setUserPhoneNumber(long userPhoneNumber){
        this.userPhoneNumber = userPhoneNumber;
        updated();
    }
    public void setUserAccID(UUID userID){
        this.userID = userID;
        updated();
    }
    public void setUserBankAccounts(List<Long> userBankAccounts){
        this.userBankAccounts = userBankAccounts;
        updated();
    }

    protected void updated(){
        this.updatedBy = Global.sessionUserID;
        this.updatedAt = System.currentTimeMillis();
    }
}