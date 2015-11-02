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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    
    // <editor-fold desc="UnitsAssign">
    public ObservableList<Employee> getEmployees(String query, HashMap<String, ObservableList> specificationTypes) {
        //String name, function, available, department, town, team, appointedTo;
        ArrayList<String> values = new ArrayList<>();
        int level;
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        
        if (!openConnection()) {
            return null;
        }
        
        ResultSet result = null;
        Statement statement = null;
        
        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(query);
            
            while (rs.next()) {
                values.clear();
                for (Map.Entry<String, ObservableList> entry : specificationTypes.entrySet()){
                    values.add(rs.getString(entry.getKey()));
                }
                // ophalen ongeval 
                // employe maken, let op attributen
                
                //Employee e = new Employee(name, function, available, department, town, level, team, appointedTo);
                //employees.add(e);
            }
        } catch (Exception ex) {
            System.out.println("Something went wrong");
            //System.out.println(ex.getErrorCode() + " -- " + ex.getMessage());
        }
        finally{
            closeConnection();
        }

        return employees;
    }
    
    public HashMap<String, ObservableList> getSpeciafications(HashMap<String, ObservableList> specifications) {
        String query = "DESCRIBE vwemployees";
        String type;
        String spec;
        
        if (!openConnection()) {
            return null;
        }
        
        ResultSet result = null;
        Statement statement = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(query);

            while (result.next()) {
                type = result.getString("Type"); // can be int(11), varchar(255) or datetime
                spec = result.getString("Field"); 
                if(!"datetime".equals(type) && !"Name".equals(spec) && !"BadgeNR".equals(spec) && !"Title".equals(spec) && !"Description".equals(spec) && !"Helpline".equals(spec)){ 
                    specifications.put(spec, FXCollections.observableArrayList());
                }else{
                    specifications.put(spec, null);
                }
            }
            
        } catch (Exception e) {
            System.out.println(e);
            specifications = null;
        } finally {
            try {
                result.close();
                statement.close();
                closeConnection();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        
        return specifications;
    }
    
    public HashMap<String, ObservableList> getSpeciaficationsValues(String query, HashMap<String, ObservableList> specificationValues) {       
        ObservableList<String> valuesSpec;
        String spec;
        
        if (!openConnection()) {
            return null;
        }
        
        ResultSet result = null;
        Statement statement = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(query);

            while (result.next()) {
                for (Map.Entry<String, ObservableList> entry : specificationValues.entrySet()){
                    if(entry.getValue() != null){
                        spec = entry.getKey();
                        valuesSpec = entry.getValue();
                        String value = result.getString(spec);
                        if(!valuesSpec.contains(value)){
                            valuesSpec.add(value);
                        }
                    }
                }
            } 
        } catch (Exception e) {
            System.out.println(e);
            specificationValues = null;
        } finally {
            try {
                result.close();
                statement.close();
                closeConnection();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        
        return specificationValues;
    }
    
    // </editor-fold>

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
            cs = conn.prepareCall("{call spInjectReport(?,?,?,0,?)}");
            cs.setString(1, repo.getDescription());
            cs.setString(2, repo.getLocation());
            cs.setInt(3, helplineid);
            cs.setString(4, repo.getTitle());
            cs.execute();
            succes = true;
            cs.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        finally{
            closeConnection();
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
                closeConnection();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}
