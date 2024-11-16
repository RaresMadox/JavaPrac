package UnitTest;

public class BankAccount {
    private double balance;
    private String accountHolderName;

    public BankAccount(String accountHolderName, double initialBalance) {
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        balance -= amount;
    }

    public void transfer(BankAccount targetAccount, double amount) {
        if (targetAccount == null) {
            throw new IllegalArgumentException("Target account cannot be null");
        }
        this.withdraw(amount);
        targetAccount.deposit(amount);
    }


}
