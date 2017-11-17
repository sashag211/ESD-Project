package Controller;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//@author F2-Pinheiro
public class JDBCBean implements Serializable {

    //Property Variable's
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private String driver = null;
    private String url = null;
    private String userName = null;
    private String password = null;
    private String sqlStatement = null;

    //Constructor
    public JDBCBean() {

    }

    //Getter's and Setter's
    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSqlStatement(String sqlStatement) {
        this.sqlStatement = sqlStatement;
    }

    //Methods
    //Start
    public String startJDBC(String driver, String url, String userName, String password) {

        //Success/Failure message
        String result = "JDBC Started...\nConnection Successful.\n";

        //Set driver, url, userName and password if not already
        if (this.driver == null) {
            setDriver(driver);
        }
        if (this.url == null) {
            setUrl(url);
        }
        if (this.userName == null) {
            setUserName(userName);
        }
        if (this.password == null) {
            setPassword(password);
        }

        try {
            //Set driver
            Class.forName(driver);

            //Make connection
            connection = DriverManager.getConnection(url, userName, password);

        } catch (SQLException e) {
            result = "JDBC Not Started...\n" + e.toString();
        } catch (ClassNotFoundException e) {
            result = "JDBC Not Started...\n" + e.toString();
        }

        System.out.println(result);
        return result;
    }

    //Stop
    public String stopJDBC() {

        //Success/Failure message
        String result = "JDBC Stopped...\nConnection Closed.\n";

        //Close connection
        try {
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            result = "JDBC Not Closed...\n" + e.toString();
        }

        System.out.println(result);
        return result;
    }

    //Execute SQL Update
    public void executeSQLUpdate(String sqlStatement) {

        //Set SQL statement
        if (this.sqlStatement == null) {
            setSqlStatement(sqlStatement);
        }
        System.out.println("Executing SQL Statement:");
        System.out.println(sqlStatement + "\n");
        //Execute SQL query
        try {
            //Create statment
            statement = connection.createStatement();
            //Execute update
            statement.executeUpdate(sqlStatement);
            System.out.println("SQL Update Successful.\n");

        } catch (SQLException e) {
            System.out.println("SQL Statement Not Executed...\n" + e.toString() + "\n");
        }
    }

    //Execute SQL Query
    public ResultSet executeSQLQuery(String sqlStatement) {

        //Set SQL statement
        if (this.sqlStatement == null) {
            setSqlStatement(sqlStatement);
        }
        System.out.println("Executing SQL Statement:");
        System.out.println(sqlStatement + "\n");
        //Execute SQL query
        try {
            //Create statment
            statement = connection.createStatement();
            //Execute query
            resultSet = statement.executeQuery(sqlStatement);
            System.out.println("SQL Query Successful.\n");

        } catch (SQLException e) {
            System.out.println("SQL Statement Not Executed...\n" + e.toString() + "\n");
        }

        return resultSet;
    }

    //Copy resultSet to an ArrayList
    public ArrayList resultsToArrayList() throws SQLException {

        ArrayList<ArrayList<Object>> rows = new ArrayList<ArrayList<Object>>();
        ArrayList<Object> columns;
        int numOfColumns = resultSet.getMetaData().getColumnCount();

        while (resultSet.next()) {
            columns = new ArrayList<Object>();
            for (int i = 1; i <= numOfColumns; i++) {
                columns.add(resultSet.getObject(i));
            }
            rows.add(columns);
        }
        return rows;
    }

    
    //Returns results of SQL Query for the given parameter
    public ArrayList sqlQueryToArrayList(String sqlStatement) throws SQLException {

        resultSet = executeSQLQuery(sqlStatement);

        ArrayList<ArrayList<Object>> rows = new ArrayList<ArrayList<Object>>();
        ArrayList<Object> columns;
        int numOfColumns = resultSet.getMetaData().getColumnCount();

        while (resultSet.next()) {
            columns = new ArrayList<Object>();
            for (int i = 1; i <= numOfColumns; i++) {
                columns.add(resultSet.getObject(i));
            }
            rows.add(columns);
        }
        return rows;
    }
}//End JDBCBean