package Model.BankAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

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
