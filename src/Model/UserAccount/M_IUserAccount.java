package Model.UserAccount;

public interface M_IUserAccount {
	public String getCreatedBy();
	public long getCreatedAt();
	public String getUpdatedBy();
	public long getUpdatedAt();
	
	public String getUserID();
	public short getUserType();
	public String getUserName();
	public byte[] getUserPassword();
	public String getUserFirstName();
	public String getUserLastName();
	public String getUserAddress();
	public long getUserPhoneNumber();
	public String[] getUserBankAccounts();
	
	public void setUserAccType(short userAccType);
	public void setUserName(String userName);
	public void setUserPassword(byte[] userPassword);
	public void setUserFirstName(String userFirstName);
	public void setUserLastName(String userLastName);
	public void setUserAddress(String userAddress);
	public void setUserPhoneNumber(long userPhoneNumber);
	public void setUserBankAccounts(String[] userBankAccounts);

	public void updated();
}