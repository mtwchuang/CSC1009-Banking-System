package Model.Transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;

public class M_BalanaceChangeTest {
    //test on setExecutedOnAtm to isExecutedOnAtm, outcome is true = true
    @Test
    public void testExecutedOnAtm(){
        M_BalanceChange bc = new M_BalanceChange(false);
        bc.setExecutedOnAtm(true);
        assertEquals(true, bc.isExecutedOnAtm());
    }

    //test on setExecutedOnPurchase to isExecutedOnPurchase, outcome is true = true
    @Test
    public void testExecutedOnPurchase(){
        M_BalanceChange bc = new M_BalanceChange(false);
        bc.setExecutedOnPurchase(true);
        assertEquals(true, bc.isExecutedOnPurchase());
    }

    //test on setCreatedby to getCreatedby, "Test123" = "Tester123"
    @Test
    public void testGetterSetterCreatedBy(){
        M_BalanceChange bc = new M_BalanceChange(false);
        bc.setCreatedBy("Test123");
        assertEquals("Test123",bc.getCreatedBy());
    }

    //test on setCreatedAt to getCreatedAt, 999999 = 999999
    @Test
    public void testGetterSetterCreatedAt(){
        M_BalanceChange bc = new M_BalanceChange(false);
        bc.setCreatedAt(999999);
        assertEquals(999999, bc.getCreatedAt());
    }

    //test on setUpdatedBy to getUpdatedBy, "Test123" = "Tester123"
    @Test
    public void testGetterSetterUpdatedBy()
    {
        M_BalanceChange bc = new M_BalanceChange(false);
        bc.setUpdatedBy("Test123");
        assertEquals("Test123", bc.getUpdatedBy());
    } 

    //test on setUpdatedAt to getUpdatedAt, 999999 = 999999
    @Test
    public void testGetterSetterUpdatedAt(){
        M_BalanceChange bc = new M_BalanceChange(false);
        bc.setUpdatedAt(999999);
        assertEquals(999999, bc.getUpdatedAt());
    }

    //test on setTransactionID to getTransactionID, "Test123" = "Test123"
    @Test
    public void testGetterSetterTransactionID(){
        M_BalanceChange bc = new M_BalanceChange(false);
        bc.setTransactionID("Test123");
        assertEquals("Test123", bc.getTransactionID());
    }

    //test on setTransactionType to getTransactionType, (short) 888 = (short) 888
    @Test
    public void testGetterSetterTransactionType(){
        M_BalanceChange bc = new M_BalanceChange(false);
        bc.setTransactionType((short) 888);
        assertEquals((short) 888, bc.getTransactionType());
    }

    //test on setTransactionAmount to getTransactionAmount, (double) 88.88 = (double) 88.88
    @Test
    public void testGetterSetterTransactionAmount(){
        M_BalanceChange bc = new M_BalanceChange(false);
        bc.setTransactionAmount((double) 88.88);
        assertEquals((double) 88.88, bc.getTransactionAmount());
    }

    //test on setTransactionID to getTransactionID, "Transfer Successful" = "Transfer Successful"
    @Test
    public void testGetterSetterTransactionDescription(){
        M_BalanceChange bc = new M_BalanceChange(false);
        bc.setTransactionID("Transfer Successful");
        assertEquals("Transfer Successful", bc.getTransactionID());
    }

    //test on setTransactionSrcBankAccID to getTransactionSrcBankAccID, "Test123" = "Test123"
    @Test
    public void testGetterSetterTransactionSrcBankAccID(){
        M_BalanceChange bc = new M_BalanceChange(false);
        bc.setTransactionSrcBankAccID("Test123");
        assertEquals("Test123", bc.getTransactionSrcBankAccID());
    }

    //test on setTransactionBankAccInitialAmount to getTransactionBankAccInitialAmount, (double) 88.88 = (double) 88.88
    @Test
    public void testGetterSetterTransactionBankAccInitialAmount(){
        M_BalanceChange bc = new M_BalanceChange(false);
        bc.setTransactionBankAccInitialAmount((double) 88.88);
        assertEquals((double) 88.88, bc.getTransactionBankAccInitialAmount());
    }

    //test on setTransactionBankAccFinalAmount to getTransactionBankAccFinalAmount, (double) 88.88 = (double) 88.88
    @Test
    public void testGetterSetterTransactionBankAccFinalAmount(){
        M_BalanceChange bc = new M_BalanceChange(false);
        bc.setTransactionBankAccFinalAmount((double) 88.88);
        assertEquals((double) 88.88, bc.getTransactionBankAccFinalAmount());
    }

}
