package Model.Transaction;
/*	Change logs
	Added setter methods for both executedOnATM and executedOnPurchase
*/
public class M_BalanceChange extends M_Transaction implements M_IBalanceChange
{
	// additional attributes for M_BalanceChange
	private boolean executedOnAtm = false;
	private String AtmID;
	private boolean executedOnPurchase = false;

	public M_BalanceChange(boolean creatingNew){
		super(creatingNew);
	}
	// balance check getters
	public boolean isExecutedOnAtm() {
		return executedOnAtm;
	}
	public String getAtmID() {
		return AtmID;
	}
	public boolean isExecutedOnPurchase() {
		return executedOnPurchase;
	}
	// balance check setters
	public void setExecutedOnAtm(boolean executedOnAtm) {
		this.executedOnAtm = executedOnAtm;
	}
	public void setExecutedOnPurchase(boolean executedOnPurchase) {
		this.executedOnPurchase = executedOnPurchase;
	}
	public void setAtmID(String atmID) {
		this.AtmID = atmID;
		this.executedOnAtm = true;
		updated();
	}
}