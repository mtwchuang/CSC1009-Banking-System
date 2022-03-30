package Model.BankAccount._UnitTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import Model.BankAccount.M_JointBankAcc;

// Unit testing for methods in M_JointBankAcc
public class M_JointBankAccTest 
{
    @Test
    public void testGetterSetterSubHolderID()
    {
        M_JointBankAcc jbc = new M_JointBankAcc();
        jbc.setBankAccHolderID("thisisatest"); 
        assertEquals("thisisatest", jbc.getBankAccHolderID());
    }
}
