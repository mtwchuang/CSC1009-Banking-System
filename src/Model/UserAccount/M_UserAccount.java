package Model.UserAccount;

import java.util.UUID;

import ModelView.MV_Global;

public class M_UserAccount implements M_IUserAccount{
    private String createdBy;
    private long createdAt;
    private String updatedBy;
    private long updatedAt;

    private String userID;
    private short userType;
    private String userName;
    private byte[] userPassword;
    private String userFirstName;
    private String userLastName;
    private String userAddress;
    private long userPhoneNumber;

    private String[] userBankAccounts;

    public M_UserAccount(){
    }
    public M_UserAccount(boolean creatingNew){
        if(creatingNew){
            this.createdBy = MV_Global.sessionUserAcc.getUserID();
            this.createdAt = System.currentTimeMillis();
            this.updatedBy = MV_Global.sessionUserAcc.getUserID();
            this.updatedAt = System.currentTimeMillis();
    
            this.userID = UUID.randomUUID().toString();
        }
    }

    public String getCreatedBy(){
        return createdBy;
    }
    public long getCreatedAt(){
        return createdAt;
    }
    public String getUpdatedBy(){
        return updatedBy;
    }
    public long getUpdatedAt(){
        return updatedAt;
    }

    public String getUserID(){
        return userID;
    }
    public short getUserType(){
        return userType;
    }
    public String getUserName(){
        return userName;
    }
    public byte[] getUserPassword(){
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
    
    public String[] getUserBankAccounts(){
        return this.userBankAccounts;
    }

    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }
    public void setCreatedAt(long createdAt){
        this.createdAt = createdAt;
    }
    public void setUpdatedBy(String updatedBy){
        this.updatedBy = updatedBy;
    }
    public void setUpdatedAt(long updatedAt){
        this.updatedAt = updatedAt;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }
    public void setUserAccType(short userType){
        this.userType = userType;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setUserPassword(byte[] userPassword){
        this.userPassword = userPassword;
    }
    public void setUserFirstName(String userFirstName){
        this.userFirstName = userFirstName;
    }
    public void setUserLastName(String userLastName){
        this.userLastName = userLastName;
    }
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
    public void setUserPhoneNumber(long userPhoneNumber){
        this.userPhoneNumber = userPhoneNumber;
    }
    public void setUserBankAccounts(String[] userBankAccounts){
        this.userBankAccounts = userBankAccounts;
    }

    public void updated(){
        this.updatedBy = MV_Global.sessionUserAcc.getUserID();
        this.updatedAt = System.currentTimeMillis();
    }
}