package com.example.speedrunpracticeprogram;
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
            String createTricknamesTable = "CREATE TABLE IF NOT EXISTS TRICKNAMES " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "name VARCHAR(3000) NOT NULL) ";
            Statement statementTrickTableCreate = connection.createStatement();
            statementTrickTableCreate.executeUpdate(createTricknamesTable);
            statementTrickTableCreate.close();
            String createAllEntriesTable = "CREATE TABLE IF NOT EXISTS ALLENTRIES " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "trickID INTEGER NOT NULL)";

            Statement statementCreateAllEntries = connection.createStatement();
            statementCreateAllEntries.executeUpdate(createAllEntriesTable);
            statementCreateAllEntries.close();

        } catch (ClassNotFoundException | SQLException e){
            System.out.println(e);
            //throw new IllegalStateException("Tables already exist!");
        }
    }
    private void addTrickToTrickNamesDatabase(String trickName){
        try {
            Statement statement = connection.createStatement();
            String addTrickToTrickNames = String.format("""
                    insert into TRICKNAMES (name)
                        values ("%s");
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
        String createNewTrickTableSQLFormat = String.format("""
                    CREATE TABLE "%s"
                    (AllEntriesID INTEGER NOT NULL,
                    Success BOOLEAN NOT NULL,
                    SessionID INTEGER NOT NULL,
                    Streak INTEGER NOT NULL,
                    FOREIGN KEY (AllEntriesID) REFERENCES ALLENTRIES(id) ON DELETE CASCADE,
                    PRIMARY KEY ( AllEntriesID ));
                    """, trickName);
        Statement create = connection.createStatement();
        create.executeUpdate(createNewTrickTableSQLFormat);
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
    public AttemptEntry inputButtonPressResultIntoTable(String trickName, boolean success){
        int trickID = getTrickIDFromTrickName(trickName);
        int allEntriesID = addEntryToAllEntriesTable(trickID);
        addResultToTrickTable(trickName, success, allEntriesID);
        return getLastAttempt(trickName);
    }
    private int getTrickIDFromTrickName(String name){
        try {
            isConnectedChecker();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM TRICKNAMES where name = ?");
            statement.setString(1, name);
            ResultSet answer = statement.executeQuery();
            if(!answer.next()){
                throw new IllegalStateException("Trick does not exist in the Tricks Table");
            }
            int ID = answer.getInt("id");
            statement.close();
            return ID;
        }
        catch(SQLException e){
            throw new IllegalArgumentException();
        }
    }
    //adds the trick attempt entry to the all entries table
    private int addEntryToAllEntriesTable(int trickID){
        try {
            isConnectedChecker();
            Statement statement = connection.createStatement();
            String addTrickToTrickNames = String.format("""
                    insert into ALLENTRIES (trickID)
                        values ("%d");
                    """, trickID);
            statement.executeUpdate(addTrickToTrickNames);
            statement.close();
            String getAllEntriesID = "SELECT MAX(id) FROM ALLENTRIES";
            Statement statement1 = connection.createStatement();
            ResultSet resultSet = statement1.executeQuery(getAllEntriesID);
            int idOfEntry = resultSet.getInt("MAX(id)");
            statement1.close();
            return idOfEntry;
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    //adds success to the individual table for that trick
    private void addResultToTrickTable(String trickName, boolean success, int allEntriesID){
        try {
            isConnectedChecker();
            Statement statement = connection.createStatement();
            int streak = 0;
            if(success && getLastAttempt(trickName)!=null){
                streak = getLastAttempt(trickName).getStreak()+1;
            }
            else if(success && getLastAttempt(trickName) == null){
                streak = 1;
            }
            String insertAttemptIntoTrickTable = String.format("""
                    insert into "%s" (AllEntriesID, Success, SessionID, Streak)
                        values (%d, %b, %d, %d);
                    """, trickName, allEntriesID, success, 0, streak);
            statement.executeUpdate(insertAttemptIntoTrickTable);
            statement.close();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public double getAllTimeSuccessRate(String trickName){
        int totalAttempts = getTotalEntriesInTable(trickName);
        double totalSuccesses = getTotalSuccessesInTable(trickName);
        return totalSuccesses/totalAttempts;
    }
    private int getTotalEntriesInTable(String trickName){
        try{
            isConnectedChecker();
            Statement statement = connection.createStatement();
            String countEntries = String.format("""
                    SELECT COUNT(*) FROM "%s";
                    """, trickName);
            ResultSet resultSet = statement.executeQuery(countEntries);
            int total = resultSet.getInt("COUNT(*)");
            statement.close();
            return total;
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    private int getTotalSuccessesInTable(String trickName){
        try{
            isConnectedChecker();
            Statement statement = connection.createStatement();
            String countEntries = String.format("""
                    SELECT COUNT(*) FROM "%s" WHERE Success = 1;
                    """, trickName);
            ResultSet resultSet = statement.executeQuery(countEntries);
            int numSuccesses = resultSet.getInt("COUNT(*)");
            statement.close();
            return numSuccesses;
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    private AttemptEntry getLastAttempt(String trickName){
        try {
            isConnectedChecker();
            Statement statement = connection.createStatement();
            String getLastAttempt = String.format("""
            SELECT * FROM "%s" ORDER BY AllEntriesID DESC LIMIT 1;
            """, trickName);
            ResultSet resultSet = statement.executeQuery(getLastAttempt);
            AttemptEntry toReturn = new AttemptEntry(resultSet.getInt("AllEntriesID"),
                    resultSet.getBoolean("Success"),
                    resultSet.getInt("SessionID"),
                    resultSet.getInt("Streak"));
            statement.close();
            return toReturn;
        }
        catch (SQLException e){
            return null;
        }
    }
    public ArrayList<Trick> getAllTricks(){
        ArrayList<String> names = (ArrayList<String>) getNamesOfTricks();
        ArrayList<Trick> trickList = new ArrayList<Trick>();
        for (String name: names) {
            trickList.add(new Trick(name, getTotalEntriesInTable(name), 0));
        }
        return trickList;
    }
    public void deleteTrick(Trick trick){
        try {
            isConnectedChecker();
            int trickID = getTrickIDFromTrickName(trick.getTrickName());
            dropTrickTable(trick);
            deleteAttemptsFromAllEntries(trickID);
            deleteTrickFromTrickNames(trickID);
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void dropTrickTable(Trick trick) throws SQLException {
        String dropTable = String.format("""
                DROP TABLE "%s";
                """, trick.getTrickName());
        Statement statement = connection.createStatement();
        statement.executeUpdate(dropTable);
    }

    private void deleteAttemptsFromAllEntries(int trickID) throws SQLException{
        String deleteAttemptsFromAllEntries = String.format("""
            DELETE FROM ALLENTRIES WHERE trickID = %d;
            """, trickID);
        Statement statement = connection.createStatement();
        statement.executeUpdate(deleteAttemptsFromAllEntries);
    }

    private void deleteTrickFromTrickNames(int trickID) throws SQLException {
        isConnectedChecker();
        String deleteTrickFromTrickNames = String.format("""
                    DELETE FROM TRICKNAMES WHERE id = %d;
                    """, trickID);
        Statement statement = connection.createStatement();
        statement.executeUpdate(deleteTrickFromTrickNames);
    }
}