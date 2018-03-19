package database;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class UseDB {

	//Help function for connecting to an external database.
	private static Connection connectDB() {
		System.out.println("Connecting to database..." + "\n");
		
		Connection conn = null;
		try {
		    conn =
		       DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no/andhauk_trainingdiary","andhauk_db","database");

		} catch (SQLException ex) {
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		return conn;
	}
	
	
	// Delete a row in the database, arguments are table name (string) and id (integer)
		public static boolean deleteRow(String table, Integer id) {
			Connection conn = connectDB();
			boolean result_status = false;
			
			try {
				System.out.println("Deleting row with id: " + id + "..");
				Statement stmt = conn.createStatement();
				
				stmt.executeUpdate("DELETE FROM " + table + " WHERE " + table + "id like " + id);
				System.out.println("Row deleted!");
				try { conn.close(); } catch (SQLException e) {/* ignore */}
				result_status = true;	
			}
			catch (SQLException ex){
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());		  
			}
			
			try { conn.close(); } catch (SQLException e) {/* ignore */}
		    System.out.println("Process finished, connection closed");
			return result_status;
		}
		
		public static boolean addRow(String table, Object...objects) {
			Connection conn = connectDB();
			boolean result_status = false;
			
			try {
				System.out.println("Inserting row into " + table + "...");
				Statement stmt = conn.createStatement();
				
				ResultSet rs = stmt.executeQuery("SELECT * FROM " + table); // Need rs to get column count
			    java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			    int colNum = rsmd.getColumnCount();
			    String cols = "";
			    
			    for (int i = 1; i < colNum; i++) {
					cols += rsmd.getColumnName(i) + ", ";
				}
			    cols += rsmd.getColumnName(colNum); // MySQL tables are one-indexed
			    
			    String values = "";
			    for (Object val : objects) {
			    	if (val instanceof String) {
			    		values += "'" + val + "', ";
			    	}
			    	else if (val instanceof Integer) {
			    		values += val.toString() + ", ";
			    	}
			    }
			    values = values.substring(0, values.length() - 2);
			    
				String query = "INSERT INTO " + table + " (" + cols + ") VALUES (" + values + ")";
				stmt.executeUpdate(query);
				System.out.println("Row added!");
				result_status = true;
			}
			catch (SQLException ex){
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
			}
			
			try { conn.close(); } catch (SQLException e) {/* ignore */}
		    System.out.println("Process finished, connection closed");
			return result_status;
		}
		
		//Get table from database
		public static ArrayList<ArrayList<String>> getTable(String query) {
			
			Connection conn = connectDB();
			
			if (conn == null) {
				System.out.println("Can not connect to database");
				System.exit(0);
			}
			
			Statement stmt = null;
			ResultSet rs = null;
			ArrayList<ArrayList<String>> table = null;
			
			System.out.println("Running query..");
			
			try {
			    stmt = conn.createStatement();
			    rs = stmt.executeQuery(query);
			    java.sql.ResultSetMetaData rsmd = rs.getMetaData();

			    int colNum = rsmd.getColumnCount();
			    table = new ArrayList<ArrayList<String>>();
			    
		        while(rs.next()) {
		        	ArrayList<String> col = new ArrayList<String>();
		        	for (int i = 1; i < colNum+1; i++) {
						col.add(rs.getString(i));
					}
		        	table.add(col);
			    }

			}
			catch (SQLException ex){
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
			}
			
		try { conn.close(); } catch (SQLException e) {/* ignore */}
		
	    System.out.println("Process finished, connection closed");
		return table;
		}
		public static ArrayList<ArrayList<String>> getIDByName(String table, String...names) {
			
			if (names.length > 2 || names.length == 0) {
				System.out.println("Invalid. Wrong number of arguments.");
				return null;
			}
			try {
				String query;
				if (names.length == 1) {
					query = "SELECT " + table + "id FROM " + table + " WHERE " + table + "." 
							+ names[0].split("=")[0] + " = '" + names[0].split("=")[1] + "'";
					System.out.println(query);
				} else {
					query = "SELECT " + table + "id FROM " + table + " WHERE " + table + "." 
							+ names[0].split("=")[0] + " = '" + names[0].split("=")[1] + "' AND " 
							+ names[1].split("=")[0] + " = '" + names[1].split("=")[1] + "'";
				}
				return getTable(query);
			} catch (Exception e) {
				System.out.println("ID not found in getIDByName");
				return null;
			}
		}
		
		//################### Methods
		// Get last trainingID
		public int getLastID(String table) {
			ArrayList<ArrayList<String>> arrlist = getTable("SELECT ID FROM " + table);
			return Integer.parseInt(arrlist.get(arrlist.size() - 1).get(arrlist.size() - 1));
		}
		
		//Get last n trainings from trainings
		public ArrayList<ArrayList<String>> getLastNTrainings(int n){
			int lastID = getLastID("Workout");
			ArrayList<ArrayList<String>> table = getTable("SELECT * FROM Workout WHERE Workout.ID > " +
			Integer.toString(lastID - n));
			return table;
		}
		
		//Get exercises in time intervall
		public ArrayList<ArrayList<String>> getExerciseBetween(Date startdate, Date finishdate, String startTime, String endTime) {
			return getTable("SELECT ExerciseName, Performance FROM Workout join ExerciseInWorkout on Workout.ID = ExerciseInWorkout.WorkoutID"
					+ "WHERE Workout.ExerciseDate >= " + startdate + " and Workout.ExerciseDate <= " + finishdate +
					"and Workout.ExerciseTime >= " + startTime + "and Workout.ExerciseTime <= " + endTime);
		}
		
		
		public ArrayList<ArrayList<String>> getExercisesInSameExerciseGroup(String group){
			return getTable("SELECT Name FROM Exercise");
		}
		
		public static void main(String[] args) {
			
		}
		
	
}
