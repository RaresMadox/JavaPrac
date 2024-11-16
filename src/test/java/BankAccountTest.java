import UnitTest.BankAccount;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {

    BankAccount account = new BankAccount("Rares",100);

    @Test
    public void testBalance(){
        assertEquals(100,account.getBalance()," Balance is 100");
    }

    @Test
    public void testDeposit(){
        account.deposit(100);
        assertEquals(200,account.getBalance(),"Deposit +100");
    }

    @Test
    public void testWithdraw(){
        account.withdraw(50);
        assertEquals(50,account.getBalance(),"Withdraw +50");
    }

    @Test
    public void testTransfer()
    {
        BankAccount accountToTransfer = new BankAccount("Rares2",100);
        account.transfer(accountToTransfer,25);
        assertAll(()->assertEquals(75,account.getBalance(),"Account from"),
        ()->assertEquals(125,accountToTransfer.getBalance(),"Transfer Account"));
    }

    @Test()
    public void testIllegalWithdraw()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,()->account.withdraw(-20));
        assertEquals("Withdrawal amount must be positive",exception.getMessage());
    }

    @Test()
    public void testGreatWithdraw()
    {
        Exception exception = assertThrows(IllegalArgumentException.class,()->account.withdraw(300));
        assertEquals("Insufficient funds",exception.getMessage());
    }

    @Test
    public void testNullTransfer() {
        BankAccount accountToTransfer = null;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> account.transfer(accountToTransfer, 25));
        assertEquals("Target account cannot be null", exception.getMessage());
    }

    @Test
    public void testConcurrentDepositsAndWithdrawals() {
        BankAccount account = new BankAccount("Rares", 1000);
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Simulate concurrent deposits and withdrawals
        for (int i = 0; i < 5; i++) {
            executor.submit(() -> account.deposit(100));
            executor.submit(() -> account.withdraw(50));
        }

        executor.shutdown();

        // Wait for all tasks to complete
        while (!executor.isTerminated()) {}

        // Expected balance: Initial 1000 + (5 * 100 deposits) - (5 * 50 withdrawals) = 1250
        assertEquals(1250, account.getBalance(), "Balance should be 1250 after concurrent operations");
    }

    @Test
    public void testAccountHolderNameIntegrity() {
        BankAccount account = new BankAccount("Rares", 1000);

        // Check that the account holder's name is as initialized
        assertEquals("Rares", account.getAccountHolderName(), "Account holder's name should match");

        // Try to modify accountHolderName (Uncommenting below will fail compilation if BankAccount is correctly implemented)
        // account.accountHolderName = "New Name"; // Error: cannot assign a value to final variable 'accountHolderName'
    }
}
