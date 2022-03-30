package Model.UserAccount;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

import ModelView.MV_Global;

/* Model:
M_UserAccount [StandAlone]
    00: createdBy - String
    01: createdAt - long
    02: updatedBy - String
    03: updatedAt - long
    04: userID - String
    05: userType - short
        Case 01: Normal User
        Case 02: Staff User [Feature not required in solution; not implemented]
        Default: Admin
    06: userName - String
    07: userFirstName - String
    08: userLastName - String
    09: userAddress - String
    10: userPhoneNumber - long
    11: userBankAccounts - String[]
*/

public class M_UserAccount implements M_IUserAccount{
	//Fields for meta-data
    private String createdBy;
    private long createdAt;
    private String updatedBy;
    private long updatedAt;
	//Fields for general data
    private String userID;
    private short userType;
    private String userName;
    private byte[] userPassword;
    private String userFirstName;
    private String userLastName;
    private String userAddress;
    private long userPhoneNumber;
	//Fields for compound data
    private String[] userBankAccounts;

	//Constructor
    public M_UserAccount(){
    }
    public M_UserAccount(boolean creatingNew){
        if(creatingNew){
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

            this.createdBy = MV_Global.sessionUserAcc.getUserID();
            this.createdAt = cal.getTimeInMillis();
            this.updatedBy = MV_Global.sessionUserAcc.getUserID();
            this.updatedAt = cal.getTimeInMillis();
    
            this.userID = UUID.randomUUID().toString();
        }
    }

	//Getters for meta-data fields
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
	//Getters for general data fields
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
	//Getters for compound data fields
    public String[] getUserBankAccounts(){
        return this.userBankAccounts;
    }

	//Setters for meta-data fields
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
	//Setters for general data fields
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
	//Setters for compound data fields
    public void setUserBankAccounts(String[] userBankAccounts){
        this.userBankAccounts = userBankAccounts;
    }

	//Function to meta-data fields
    public void updated(){
        LocalDateTime now = LocalDateTime.now();
		ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
		ZonedDateTime utcTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        
        this.updatedBy = MV_Global.sessionUserAcc.getUserID();
		this.updatedAt = utcTime.toInstant().toEpochMilli();
    }
}