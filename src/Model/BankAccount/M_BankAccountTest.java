package Model.BankAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;

// unit testing for getter and setter methods in M_BankAccount
public class M_BankAccountTest 
{
    //test on setCreatedby to getCreatedby, "Test123" = "Tester123"
    @Test
    public void testGetterSetterCreatedBy()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setCreatedBy("Tester123");
        assertEquals("Tester123", ba.getCreatedBy());
    }
    //test on setCreatedAt to getCreatedAt, 999999 = 999999
    @Test
    public void testGetterSetterCreatedAt()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setCreatedAt(999999);
        assertEquals(999999, ba.getCreatedAt());
    }
    //test on setUpdatedAt to getUpdatedAt, 999999 = 999999
    @Test
    public void testGetterSetterUpdatedAt()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setUpdatedAt(999999);
        assertEquals(999999, ba.getUpdatedAt());
    }
     //test on setUpdatedBy to getUpdatedBy, "Test123" = "Tester123"
    @Test
    public void testGetterSetterUpdatedBy()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setUpdatedBy("Tester123");
        assertEquals("Tester123", ba.getUpdatedBy());
    }
    //test on setBankAccID to getBankAccID, "Tester123" = "Tester123"
    @Test
    public void testGetterSetterBankAccID()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setBankAccID("Tester123");
        assertEquals("Tester123", ba.getBankAccID());
    }
    //test on setBankAccHolderID to getBankAccHolderID, "Tester123" = "Tester123"
    @Test
    public void testGetterSetterBankAccHolderID()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setBankAccHolderID("Tester123");
        assertEquals("Tester123", ba.getBankAccHolderID());        
    }
    //test on setBankAccType to getBankAccType, (short) 777 = (short) 777
    @Test
    public void testGetterSetterBankAccType()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setBankAccType((short) 777);
        assertEquals((short) 777, ba.getBankAccType());
    }
    //test on setBankAccDescription to getBankAccDescription, "Tester123" = "Tester123"
    @Test
    public void testGetterSetterBankAccDescription()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setBankAccDescription("Tester123");
        assertEquals("Tester123", ba.getBankAccDescription());               
    }
    //test on setBankAccStatus to getBankAccStatus, (short) 777 = (short) 777
    @Test
    public void testGetterSetterBankAccStatus()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setBankAccStatus((short) 777);
        assertEquals((short) 777, ba.getBankAccStatus());        
    }
    //test on setBankAccBalance to getBankAccBalance, 25.0 = 25.0
    @Test
    public void testGetterSetterBankAccBalance()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setBankAccBalance(25.0);
        assertEquals(25.0, ba.getBankAccBalance());
    }
    //test on setBankAccTransactionLimit to getBankAccTransactionLimit, 1000.0 to 1000.0
    @Test
    public void testGetterSetterBankAccTransactionLimits()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setBankAccTransactionLimit(1000.0);
        assertEquals(1000.0, ba.getBankAccTransactionLimit());
    }
    //test on setBankAccMinBalance to getBankAccMinBalance, 1000.0 = 1000.0
    @Test
    public void testGetterSetterBankAccMinBalance()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setBankAccMinBalance(1000.0);
        assertEquals(1000.0, ba.getBankAccMinBalance());        
    }
}
