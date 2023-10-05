package Model.Transaction._UnitTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;

import Model.Transaction.M_BalanceTransfer;

public class M_BalanceTransferTest {
    //test on setCreatedby to getCreatedby, "Test123" = "Tester123"
    @Test
    public void testGetterSetterCreatedBy(){
        M_BalanceTransfer bt = new M_BalanceTransfer(false);
        bt.setCreatedBy("Test123");
        assertEquals("Test123",bt.getCreatedBy());
    }

    //test on setCreatedAt to getCreatedAt, 999999 = 999999
    @Test
    public void testGetterSetterCreatedAt(){
        M_BalanceTransfer bt = new M_BalanceTransfer(false);
        bt.setCreatedAt(999999);
        assertEquals(999999, bt.getCreatedAt());
    }

    //test on setUpdatedBy to getUpdatedBy, "Test123" = "Tester123"
    @Test
    public void testGetterSetterUpdatedBy()
    {
        M_BalanceTransfer bt = new M_BalanceTransfer(false);
        bt.setUpdatedBy("Test123");
        assertEquals("Test123", bt.getUpdatedBy());
    } 

    //test on setUpdatedAt to getUpdatedAt, 999999 = 999999
    @Test
    public void testGetterSetterUpdatedAt(){
        M_BalanceTransfer bt = new M_BalanceTransfer(false);
        bt.setUpdatedAt(999999);
        assertEquals(999999, bt.getUpdatedAt());
    }

    //test on setTransactionID to getTransactionID, "Test123" = "Test123"
    @Test
    public void testGetterSetterTransactionID(){
        M_BalanceTransfer bt = new M_BalanceTransfer(false);
        bt.setTransactionID("Test123");
        assertEquals("Test123", bt.getTransactionID());
    }

    //test on setTransactionType to getTransactionType, (short) 888 = (short) 888
    @Test
    public void testGetterSetterTransactionType(){
        M_BalanceTransfer bt = new M_BalanceTransfer(false);
        bt.setTransactionType((short) 888);
        assertEquals((short) 888, bt.getTransactionType());
    }

    //test on setTransactionAmount to getTransactionAmount, (double) 88.88 = (double) 88.88
    @Test
    public void testGetterSetterTransactionAmount(){
        M_BalanceTransfer bt = new M_BalanceTransfer(false);
        bt.setTransactionAmount((double) 88.88);
        assertEquals((double) 88.88, bt.getTransactionAmount());
    }

    //test on setTransactionID to getTransactionID, "Transfer Successful" = "Transfer Successful"
    @Test
    public void testGetterSetterTransactionDescription(){
        M_BalanceTransfer bt = new M_BalanceTransfer(false);
        bt.setTransactionID("Transfer Successful");
        assertEquals("Transfer Successful", bt.getTransactionID());
    }

    //test on setTransactionSrcBankAccID to getTransactionSrcBankAccID, "Test123" = "Test123"
    @Test
    public void testGetterSetterTransactionSrcBankAccID(){
        M_BalanceTransfer bt = new M_BalanceTransfer(false);
        bt.setTransactionSrcBankAccID("Test123");
        assertEquals("Test123", bt.getTransactionSrcBankAccID());
    }

    //test on setTransactionBankAccInitialAmount to getTransactionBankAccInitialAmount, (double) 88.88 = (double) 88.88
    @Test
    public void testGetterSetterTransactionBankAccInitialAmount(){
        M_BalanceTransfer bt = new M_BalanceTransfer(false);
        bt.setTransactionBankAccInitialAmount((double) 88.88);
        assertEquals((double) 88.88, bt.getTransactionBankAccInitialAmount());
    }

    //test on setTransactionBankAccFinalAmount to getTransactionBankAccFinalAmount, (double) 88.88 = (double) 88.88
    @Test
    public void testGetterSetterTransactionBankAccFinalAmount(){
        M_BalanceTransfer bt = new M_BalanceTransfer(false);
        bt.setTransactionBankAccFinalAmount((double) 88.88);
        assertEquals((double) 88.88, bt.getTransactionBankAccFinalAmount());
    }


}
