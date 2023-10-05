package Model.Transaction;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import ModelView.Global.MV_Global;

/* Model:
M_Transaction [Abstract Parent]
	00: createdBy - String
	01: createdAt - long
	02: updatedBy - String
	03: updatedAt - long
	04: transactionID - String
		Case 00: Withdrawals
		Case 01: Deposits
		Case 02: Charges/Purchases
		Case 03: Transfer Sending
		Case 04: Transfer Receiving
	05: transactionType - short
	06: transactionAmount - double
	07: transactionDescription - String
	08: transactionOverseas - boolean
	09: transactionBankAccID - String
	10: transactionBankAccInitialAmount - double
	11: transactionBankAccFinalAmount - double
*/

public abstract class M_Transaction implements M_ITransaction
{
	//Fields for meta-data
	private String createdBy;
	private long createdAt;
	private String updatedBy;
	private long updatedAt;
	//Fields for general data
	private String transactionID;
	private short transactionType;
	private double transactionAmount;
	private String transactionDescription;
	private boolean transactionOverseas;
	private String transactionSrcBankAccID;
	private double transactionBankAccInitialAmount;
	private double transactionBankAccFinalAmount;

	//Constructor
	public M_Transaction(boolean creatingNew)
	{
		// asks if this is a new transaction that is being created
		if(creatingNew){
			LocalDateTime now = LocalDateTime.now();
        	ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
        	ZonedDateTime utcTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
			
			this.createdBy = MV_Global.sessionUserAcc.getUserID();
			this.createdAt = utcTime.toInstant().toEpochMilli();
			this.updatedBy = MV_Global.sessionUserAcc.getUserID();
			this.updatedAt = utcTime.toInstant().toEpochMilli();
	
			this.transactionID = UUID.randomUUID().toString();

			this.transactionSrcBankAccID = MV_Global.sessionBankAcc.getBankAccID();
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
	public String getTransactionID(){
		return transactionID;
	}
	public short getTransactionType(){
		return transactionType;
	}
	public double getTransactionAmount(){
		return transactionAmount;
	}
	public String getTransactionDescription(){
		return transactionDescription;
	}
	public boolean isTransactionOverseas(){
		return transactionOverseas;
	}
	public String getTransactionSrcBankAccID(){
		return transactionSrcBankAccID;
	}
	public double getTransactionBankAccInitialAmount(){
		return transactionBankAccInitialAmount;
	}
	public double getTransactionBankAccFinalAmount(){
		return transactionBankAccFinalAmount;
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
	public void setTransactionID(String transactionID){
		this.transactionID = transactionID;
	}
	public void setTransactionType(short transactionType){
		this.transactionType = transactionType;
	}
	public void setTransactionAmount(double transactionAmount){
		this.transactionAmount = transactionAmount;
	}
	public void setTransactionDescription(String transactionDescription){
		this.transactionDescription = transactionDescription;
	}
	public void setTransactionOverseas(boolean transactionOverseas){
		this.transactionOverseas = transactionOverseas;
	}
	public void setTransactionSrcBankAccID(String transactionSrcBankAccID){
		this.transactionSrcBankAccID = transactionSrcBankAccID;
	}
	public void setTransactionBankAccInitialAmount(double transactionBankAccInitialAmount){
		this.transactionBankAccInitialAmount = transactionBankAccInitialAmount;
	}
	public void setTransactionBankAccFinalAmount(double transactionBankAccFinalAmount){
		this.transactionBankAccFinalAmount = transactionBankAccFinalAmount;
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