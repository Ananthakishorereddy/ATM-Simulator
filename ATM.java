import java.sql.*;

public class ATM {
    private Connection connection;

    public ATM(String dburl, String dbuser, String dbpass)throws SQLException {
        connection= DriverManager.getConnection(dburl, dbuser, dbpass);
    }

    //Add a new acc to the db

    public void addAccount(Account account)throws SQLException{
        String query="INSERT INTO accounts(username,password,balance,interest_rate) VALUES(?,?,?,?)";
        try(PreparedStatement ps=connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.setDouble(3, account.getBalance());
            ps.setDouble(4, account.getInterestRate());
            ps.executeUpdate();

            ResultSet generatedKeys=ps.getGeneratedKeys();
            if(generatedKeys.next()){
                account.setId(generatedKeys.getInt(1));
            }
        }
    }


    //Authenticate user
    public Account authenticate(String username, String password)throws SQLException{
        String query="SELECT * FROM accounts WHERE username=? AND password=?";
        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                int id=rs.getInt("id");
                double balance=rs.getDouble("balance");
                double interestRate=rs.getDouble("interest_rate");
                return new Account(id,username,password,balance,interestRate);

            }else{
                System.out.println("No such account");
                return null;
            }
        }
    }


    public void updateAccount(Account account)throws SQLException{
        String query="UPDATE accounts SET balance=? WHERE id=?";
        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setDouble(1, account.getBalance());
            ps.setInt(2, account.getId());
            ps.executeUpdate();
        }
    }


    public void recordTransaction(Account account,String transaction)throws SQLException{
        String query="INSERT INTO transactions(account_id,transaction) VALUES(?,?)";
        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setInt(1, account.getId());
            ps.setString(2, transaction);
            ps.executeUpdate();
        }
    }


    public void viewTransactions(Account account)throws SQLException{
        String query="SELECT * FROM transactions WHERE account_id=?";
        try(PreparedStatement ps=connection.prepareStatement(query)){
            ps.setInt(1, account.getId());
            ResultSet rs=ps.executeQuery();

            System.out.println("Transaction History for  "+account.getUsername() +" :");
            while(rs.next()){
                System.out.println(rs.getString("transaction"));
            }
        }
    }
}
