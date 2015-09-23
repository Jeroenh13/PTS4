package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import cims.Unit;
import java.util.List;

/**
 * *
 *
 * Class used for a connection to communicate with the database
 */
public class DatabaseManager {

    private Connection conn = null;

    /**
     * *
     * Opens the database connection
     *
     * @return if the opening of the connection is succesfull
     */
    private boolean openConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://athena01.fhict.local/dbi298273?user=dbi298273&password=AbEc65A52w");
            return true;
        } catch (SQLException e) {
            conn = null;
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
            return false;
        }
    }
    
    /***
     * Closes the connection
     * @return succesrate 
     */
    private boolean closeConnection(){
        try{
        conn.close();
        return true;
        }catch(Exception e){return false;}
    }

    /**
     * *
     * Tests the connection to the database
     *
     * @return succes rate
     * @throws java.sql.SQLException closing the connections
     */
    public boolean testConnection() throws SQLException {
        if (!openConnection()) {
            return false;
        }
        ResultSet result = null;
        Statement statement;
        boolean returnval = false;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery("Select * from Personeel");
            statement.closeOnCompletion();
            result.last();
            if (result.getRow() != 0) {
                returnval = true;
            }
            return returnval;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
            conn = null;
            return false;
        } finally {
            if (result != null) {
                result.close();
            }
            closeConnection();
        }
    }
    
    public List<Unit> getUnits(String name, String emergency, String function, String available, String department, String regio, String level, String team)
    {
        
    }

}
