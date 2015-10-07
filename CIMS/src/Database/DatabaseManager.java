package Database;

import cims.Employee;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import cims.Helpline;
import cims.Report;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

/**
 * *
 *
 * Class used for a connection to communicate with the database
 */
public class DatabaseManager {

    private Connection conn = null;

    public DatabaseManager() {

    }

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

    /**
     * *
     * Closes the connection
     *
     * @return succesrate
     */
    private boolean closeConnection() {
        try {
            conn.close();
            return true;
        } catch (Exception e) {
            return false;
        }
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

    public ArrayList<Employee> getUnits(String query) {
        //String name, String emergency, String function, String available, String department, String regio, String level, String team

        //boolean first = true;
        String name;
        String function;
        String available;
        String department;
        String town;
        int level;
        String team;
        String appointedTo;

        ArrayList<Employee> employees = new ArrayList<>();
        try {
            Statement stat = conn.createStatement();
//            String query = "SELECT * FROM vwPersoneelMelding WHERE "; //klopt de naam van de view?
//            for (Map.Entry<String, String> entry : hm.entrySet()) //why the fuck not??
//            {
//                if (first == true)
//                {
//                    query += entry.getKey().toString() + " = " + entry.getValue().toString();
//                    first = false;
//                }
//                else
//                {
//                    query += "AND " + entry.getKey().toString() + " = " + entry.getValue().toString();
//                }
//            }
//            
//            query += ";";
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                //add to employees
                name = rs.getString("name");
                function = rs.getString("function");
                available = rs.getString("available");
                department = rs.getString("department");
                town = rs.getString("town");
                level = rs.getInt("level");
                team = rs.getString("team");
                appointedTo = rs.getString("appointedTo");
                Employee e = new Employee(name, function, available, department, town, level, team, appointedTo);
                employees.add(e);
            }
        } catch (Exception ex) {
            System.out.println("Something went wrong");
            //System.out.println(ex.getErrorCode() + " -- " + ex.getMessage());
        }

        return employees;
    }

    public HashMap<String, TreeSet> getSpeciafications() {
        //haal alle speciaficaties op
        return null;
    }

    /**
     * saves the report with the given items.
     *
     * @param repo report to add
     * @param helplineid helplineid
     * @return succes
     */
    public boolean saveReport(Report repo,int helplineid) {
        boolean succes = false;
        if (!openConnection()) {
            return succes;
        }
        try {
            CallableStatement cs = null;
            cs = conn.prepareCall("{call spInjectReport(?,?,?,0)}");
            cs.setString(1, repo.getDescription());
            cs.setString(2, repo.getLocation());
            cs.setInt(3, helplineid);
            cs.execute();
            succes = true;
            cs.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return succes;
    }

    public ArrayList getHelpLines() {
        if (!openConnection()) {
            return null;
        }
        ResultSet result = null;
        Statement statement = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery("Select helplineID,name from Helpline");
            ArrayList lines = new ArrayList<Helpline>();
            while (result.next()) {
                lines.add(new Helpline(result.getInt("helplineID"), result.getString("name")));
            }
            return lines;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        } finally {
            try {
                result.close();
                statement.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}
