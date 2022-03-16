package Model.Transaction;

public class M_BalanceChange extends M_Transaction implements M_IBalanceChange
{
	private boolean executedOnAtm = false;
	private String AtmID;

	private boolean executedOnPurchase = false;

	public M_BalanceChange(boolean creatingNew){
		super(creatingNew);
	}

	public boolean isExecutedOnAtm() {
		return executedOnAtm;
	}
	public String getAtmID() {
		return AtmID;
	}

	public boolean isExecutedOnPurchase() {
		return executedOnPurchase;
	}

	public void setAtmID(String atmID) {
		this.AtmID = atmID;
		this.executedOnAtm = true;
		updated();
	}
}