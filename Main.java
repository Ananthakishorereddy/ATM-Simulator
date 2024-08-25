import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static ATM atm;


    public static void main(String[] args) {
        try{
            String dbUrl = "jdbc:mysql://localhost:3306/atm_db";
            String dbUser = "root";
            String dbPass = "Kishore@123";

            atm=new ATM(dbUrl,dbUser,dbPass);

            while(true){
                System.out.println("\n WELCOME TO ATM MACHINE");
                System.out.println(" 1. Create Account");
                System.out.println(" 2. Login");
                System.out.println(" 3. Exit");
                System.out.println(" Enter your choice");

                int choice = sc.nextInt();
                sc.nextLine();

                switch(choice){
                    case 1:
                        createAccount();
                        break;
                    case 2:
                        login();
                        break;
                    case 3:
                        System.out.println("Thank you for using ATM Machine");
                        return;
                    default:
                        System.out.println("Invalid choice");
                }

            }


        }catch(Exception e){
            e.printStackTrace();
        }



    }

    public static void createAccount(){
        System.out.print("Enter the UserName  :");
        String userName = sc.nextLine();
        System.out.print("Enter the Password   :");
        String password = sc.nextLine();
        System.out.print("Enter the initial deposit amount :  ");
        double intialDeposit = sc.nextDouble();

        Account account=new Account(userName,password,intialDeposit);
        try{
            atm.addAccount(account);
            System.out.println("Account created Successfully for "+userName+" .");

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public static void login(){
        System.out.print("Enter the UserName  :");
        String userName = sc.nextLine();
        System.out.print("Enter the Password  :");
        String password = sc.nextLine();

        try{
            Account account=atm.authenticate(userName,password);
            if(account!=null){
                while (true){
                    System.out.println("\n WELCOME ,"+userName);
                    System.out.println("1. Deposit Money");
                    System.out.println("2. Withdraw Money");
                    System.out.println("3. view Transactions");
                    System.out.println("4. Calculate Interest");
                    System.out.println("5. Check Balance");
                    System.out.println("6. Logout");
                    System.out.println("Enter your choice");
                    int choice = sc.nextInt();

                    switch(choice){
                        case 1:
                            depositMoney(account);
                            break;
                        case 2:
                            withdrawMoney(account);
                            break;
                        case 3:
                            atm.viewTransactions(account);
                            break;
                        case 4:
                            calculateInterest(account);
                            break;
                        case 5:
                            System.out.println("Current balance : "+account.getBalance());
                            break;
                        case 6:
                            System.out.println("Logged out successfully");
                            return;
                        default:
                            System.out.println("Invalid choice");
                    }
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }



    public static void depositMoney(Account account){
        System.out.print("Enter the amount to deposit: ");
        double depositAmount = sc.nextDouble();
        account.deposit(depositAmount);

        try{
            atm.updateAccount(account);
            atm.recordTransaction(account,"Deposited :"+depositAmount);
            System.out.println("Deposited "+depositAmount+" successfully");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    private static void withdrawMoney(Account account){
        System.out.print("Enter the amount to withdraw: ");
        double withdrawAmount = sc.nextDouble();
        if(withdrawAmount>account.getBalance()){
            System.out.println("Insufficient Balance");
        }else{
            account.withdraw(withdrawAmount);
            try{
                atm.updateAccount(account);
                atm.recordTransaction(account,"withdraw :"+withdrawAmount);
                System.out.println("Withdrawn "+withdrawAmount+" successfully");

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }



    private static void calculateInterest(Account account){
        System.out.print("Enter the number of years for calculate interest: ");
        int years = sc.nextInt();
        double interest= account.calculateInterest(years);
        System.out.printf("Interest for %d years is :$%.2f\n",years,interest);
    }
}