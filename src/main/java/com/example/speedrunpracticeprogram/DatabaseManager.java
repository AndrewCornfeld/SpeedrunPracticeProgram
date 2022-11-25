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
    public void createNewTrickTable(String trickName) throws SQLException {
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
//    private void deleteTables(){
//            String databaseName = "SpeedrunPracticeProgram.sqlite3";
//            String databaseUrl = "jdbc:sqlite:" + databaseName;
//
//            try{
//                isConnectedChecker();
//                Class.forName("org.sqlite.JDBC");
//                Connection connection = DriverManager.getConnection(databaseUrl);
//                Statement statement = connection.createStatement();
//                connection.setAutoCommit(false);
//
//                //deletes Stop Table
//                String deleteStops = "DROP TABLE STOPS";
//                statement.executeUpdate(deleteStops);
//
//                //deletes Busline Table
//                String deleteBusLine = "DROP TABLE BUSLINES";
//                statement.executeUpdate(deleteBusLine);
//
//                //deletes Route Table
//                String deleteRoute = "DROP TABLE ROUTES";
//                statement.executeUpdate(deleteRoute);
//
//                connection.setAutoCommit(true);
//
//            }
//            catch (ClassNotFoundException | SQLException e){
//                throw new IllegalStateException("Tables that do not exist cannot be deleted!");
//            }
//
//        }
    }

