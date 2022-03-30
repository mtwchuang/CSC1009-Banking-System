package Model.BankAccount._UnitTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;

import Model.BankAccount.M_BankAccount;

// unit testing for getter and setter methods in M_BankAccount
public class M_BankAccountTest 
{
    @Test
    // testing method for CreatedBy
    public void testGetterSetterCreatedBy()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setCreatedBy("Tester123");
        assertEquals("Tester123", ba.getCreatedBy());
    }
    @Test
    public void testGetterSetterCreatedAt()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setCreatedAt(999999);
        assertEquals(999999, ba.getCreatedAt());
    }
    @Test
    public void testGetterSetterUpdatedAt()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setUpdatedAt(999999);
        assertEquals(999999, ba.getUpdatedAt());
    }
    @Test
    public void testGetterSetterUpdatedBy()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setUpdatedBy("Tester123");
        assertEquals("Tester123", ba.getUpdatedBy());
    } 
    @Test
    public void testGetterSetterBankAccID()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setBankAccID("Tester123");
        assertEquals("Tester123", ba.getBankAccID());
    }
    @Test
    public void testGetterSetterBankAccHolderID()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setBankAccHolderID("Tester123");
        assertEquals("Tester123", ba.getBankAccHolderID());        
    }
    @Test
    public void testGetterSetterBankAccType()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setBankAccType((short) 777);
        assertEquals((short) 777, ba.getBankAccType());
    }
    @Test
    public void testGetterSetterBankAccDescription()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setBankAccDescription("Tester123");
        assertEquals("Tester123", ba.getBankAccDescription());               
    }
    @Test
    public void testGetterSetterBankAccStatus()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setBankAccStatus((short) 777);
        assertEquals((short) 777, ba.getBankAccStatus());        
    }
    @Test
    public void testGetterSetterBankAccBalance()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setBankAccBalance(25.0);
        assertEquals(25.0, ba.getBankAccBalance());
    }
    @Test
    public void testGetterSetterBankAccTransactionLimits()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setBankAccTransactionLimit(1000.0);
        assertEquals(1000.0, ba.getBankAccTransactionLimit());
    }
    @Test
    public void testGetterSetterBankAccMinBalance()
    {
        M_BankAccount ba = new M_BankAccount();
        ba.setBankAccMinBalance(1000.0);
        assertEquals(1000.0, ba.getBankAccMinBalance());        
    }
}
