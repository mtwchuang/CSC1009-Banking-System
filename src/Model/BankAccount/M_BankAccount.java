package Model.BankAccount;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import ModelView.MV_Global;

/* Model:
M_BankAccount [Parent]
	00: createdBy - String
	01: createdAt - long
	02: updatedBy - String
	03: updatedAt - long
	04: bankAccID - String
	05: bankAccHolderID - String
	06: bankAccType - short
		Case 00: Normal bank acc
		Case 01: Joint bank acc
		Case 02: Corperate bank acc
	07: bankAccDescription - String
	08: bankAccStatus - short
		Case 00: Normal
		Case 01: Closed
	09: bankAccBalance - double
	10: bankAccTransactionLimit - double
	11: bankAccMinBalance - double
*/

public class M_BankAccount implements M_IBankAccount{
	private String createdBy;
	private long createdAt;
	private String updatedBy;
	private long updatedAt;

	private String bankAccID;
	private String bankAccHolderID;
	private short bankAccType;
	private String bankAccDescription;

	private short bankAccStatus;
	private double bankAccBalance;
	private double bankAccTransactionLimit;
	private double bankAccMinBalance;

	public M_BankAccount(){
	}
	public M_BankAccount(String bankAccHolderID){
		LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
        ZonedDateTime utcTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
		
		this.createdBy = MV_Global.sessionUserAcc.getUserID();
		this.createdAt = utcTime.toInstant().toEpochMilli();
		this.updatedBy = MV_Global.sessionUserAcc.getUserID();
		this.updatedAt = utcTime.toInstant().toEpochMilli();

		this.bankAccID = UUID.randomUUID().toString();
		this.bankAccHolderID = bankAccHolderID;
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

	public String getBankAccID(){
		return bankAccID;
	}
	public String getBankAccHolderID(){
		return bankAccHolderID;
	}
	public short getBankAccType(){
		return bankAccType;
	}
	public String getBankAccDescription(){
		return bankAccDescription;
	}
	public short getBankAccStatus(){
		return bankAccStatus;
	}
	public double getBankAccBalance(){
		return bankAccBalance;
	}
	public double getBankAccTransactionLimit(){
		return bankAccTransactionLimit;
	}
	public double getBankAccMinBalance(){
		return bankAccMinBalance;
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

	public void setBankAccID(String bankAccID){
		this.bankAccID = bankAccID;
	}
	public void setBankAccHolderID(String bankAccHolderID){
		this.bankAccHolderID = bankAccHolderID;
	}
	public void setBankAccType(short bankAccType){
		this.bankAccType = bankAccType;
	}
	public void setBankAccDescription(String bankAccDescription){
		this.bankAccDescription = bankAccDescription;
	}
	public void setBankAccStatus(short bankAccStatus){
		this.bankAccStatus = bankAccStatus;
	}
	public void setBankAccBalance(double bankAccBalance){
		this.bankAccBalance = bankAccBalance;
	}
	public void setBankAccTransactionLimit(double bankAccTransactionLimit){
		this.bankAccTransactionLimit = bankAccTransactionLimit;
	}
	public void setBankAccMinBalance(double bankAccMinBalance){
		this.bankAccMinBalance = bankAccMinBalance;
	}

	public void updated(){
		LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
        ZonedDateTime utcTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));

		this.updatedBy = MV_Global.sessionUserAcc.getUserID();
		this.updatedAt = utcTime.toInstant().toEpochMilli();
	}
}