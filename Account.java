public class Account {
    private int id;
    private String username;
    private String password;
    private double balance;
    private double interestRate;

    //con for existing acc
    public Account(int id, String username, String password, double balance, double interestRate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.interestRate = interestRate;
    }

    //con for new acc
    public Account(String username, String password, double initialDeposit) {
        this.username = username;
        this.password = password;
        this.balance = initialDeposit;
        this.interestRate = 0.5;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void deposit(double amount) {
        balance += amount;
    }
    public void withdraw(double amount) {
        balance -= amount;
    }
    public double calculateInterest(int years) {
        return balance *Math.pow(1+interestRate, years)-balance;
    }
}
