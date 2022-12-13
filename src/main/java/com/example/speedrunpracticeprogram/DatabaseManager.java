package com.example.speedrunpracticeprogram;
import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
public class DatabaseManager {
    public static final String DATABASE_PATH = "SpeedrunPracticeProgram.sqlite3";
    Connection connection;
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            //connection = new Connection();
            if(connection == null){
                connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_PATH); //null
            }
            else if(connection != null && !connection.isClosed()){
                throw new IllegalStateException("Manager has already been connected"); //not null but still open
            }
            connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_PATH); //not null and closed
        }
        catch (ClassNotFoundException | SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void createStartupTables() {
        String databaseName = "SpeedrunPracticeProgram.sqlite3";
        String databaseUrl = "jdbc:sqlite:" + databaseName;
        try {
            isConnectedChecker();
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection(databaseUrl);
            //https://www.tutorialspoint.com/jdbc/jdbc-create-tables.htm
            String createTricknamesTable = "CREATE TABLE TRICKNAMES " +
                    "(id INTEGER NOT NULL, " +
                    "name VARCHAR(3000) NOT NULL, " +
                    "PRIMARY KEY ( id ))";
            Statement statementTrickTableCreate = connection.createStatement();
            statementTrickTableCreate.executeUpdate(createTricknamesTable);
            statementTrickTableCreate.close();
            String createAllEntriesTable = "CREATE TABLE ALLENTRIES" +
                    "(id INTEGER NOT NULL, " +
                    "trickID INTEGER NOT NULL, " +
                    "PRIMARY KEY ( id ))";

            Statement statementCreateAllEntries = connection.createStatement();
            statementCreateAllEntries.executeUpdate(createAllEntriesTable);
            statementCreateAllEntries.close();

        } catch (ClassNotFoundException | SQLException e){
            throw new IllegalStateException("Tables already exist!");
        }
    }
    private void addTrickToTrickNamesDatabase(String trickName){
        try {
            Statement statement = connection.createStatement();
            String addTrickToTrickNames = String.format("""
                    insert into TRICKNAMES (name)
                        values ("%s);
                    """, trickName);
            statement.executeUpdate(addTrickToTrickNames);
            statement.close();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void addTrickToDatabase(String trickName) throws SQLException {
        createNewTrickTable(trickName);
        addTrickToTrickNamesDatabase(trickName);
    }
    private void createNewTrickTable(String trickName) throws SQLException {
        String createNewTrickTableSQL = "CREATE TABLE " + trickName +
                "(AllEntriesID INTEGER NOT NULL, " +
                "Success BOOLEAN NOT NULL, " +
                "SessionID INTEGER NOT NULL, " +
                "FOREIGN KEY (AllEntriesID) REFERENCES ALLENTRIES(id) ON DELETE CASCADE, "+
                "PRIMARY KEY ( AllEntriesID ))";
        PreparedStatement create = connection.prepareStatement(createNewTrickTableSQL);
            create.executeUpdate();
    }
    public void isConnectedChecker() throws SQLException {
        if(connection == null){
            connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_PATH); //null
        }
        else if(connection.isClosed()){
            throw new IllegalStateException("Manager is not connected!"); //not null but still open
        }
    }
    protected void deleteALLTables(){
        String databaseName = "SpeedrunPracticeProgram.sqlite3";
        String databaseUrl = "jdbc:sqlite:" + databaseName;

        try{
            isConnectedChecker();
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection(databaseUrl);
            Statement statement = connection.createStatement();
            connection.setAutoCommit(false);
                //deletes Stop Table
            String deleteAllEntries = "DROP TABLE ALLENTRIES;";
            statement.executeUpdate(deleteAllEntries);

            //deletes Busline Table
            String deleteBusLine = "DROP TABLE TRICKNAMES";
            statement.executeUpdate(deleteBusLine);
            connection.commit();
            connection.setAutoCommit(true);

        }
            catch (ClassNotFoundException | SQLException e){
                throw new IllegalStateException("Tables that do not exist cannot be deleted!");
            }

    }
    public List<String> getNamesOfTricks(){
        String databaseName = "SpeedrunPracticeProgram.sqlite3";
        String databaseUrl = "jdbc:sqlite:" + databaseName;
        List<String> trickNames = new ArrayList<String>();
       try{
           isConnectedChecker();
           PreparedStatement getTrickNames = connection.prepareStatement("SELECT * FROM TRICKNAMES");
           ResultSet rs = getTrickNames.executeQuery();
           while(rs.next()) {
               trickNames.add(rs.getString("name"));
           }

       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
       return trickNames;
    }
    }

