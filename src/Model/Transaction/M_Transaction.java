package Model.Transaction;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;
import ModelView.MV_Global;
/*	Changes logs
	Removed fields and relevant methods for executedBy and executedAt
	Added set methods for fields related to audit checking, transactionID and 
	transactionExecuted
*/
public abstract class M_Transaction implements M_ITransaction
{
	// fields for audit checking
	private String createdBy;
	private long createdAt;
	private String updatedBy;
	private long updatedAt;
	// fields for general transaction
	private String transactionID;
	private short transactionType;
	private double transactionAmount;
	private String transactionDescription;
	private boolean transactionOverseas;
	private String transactionSrcBankAccID;
	private double transactionBankAccInitialAmount;
	private double transactionBankAccFinalAmount;

	public M_Transaction(boolean creatingNew)
	{
		// asks if this is a new transaction that is being created
		if(creatingNew)
		{
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			
			this.createdBy = MV_Global.sessionUserAcc.getUserID();
			this.createdAt = cal.getTimeInMillis();
			this.updatedBy = MV_Global.sessionUserAcc.getUserID();
			this.updatedAt = cal.getTimeInMillis();
	
			this.transactionID = UUID.randomUUID().toString();
	
			this.transactionSrcBankAccID = MV_Global.sessionBankAcc.getBankAccID();
		}
	}

	//getters methods for audits
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
	// getter methods for transaction details
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

	// setter methods for audits
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
	// setter methods for transaction details
	public void setTransactionID(String transactionID)
	{
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

	public void updated(){
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		this.updatedBy = MV_Global.sessionUserAcc.getUserID();
		this.updatedAt = cal.getTimeInMillis();
	}
}