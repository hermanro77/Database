package database;

import java.util.Scanner;

public class Program {

	private Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		Program program = new Program();
		program.launch();
	}
	
	public void launch() {
		while(true) {
			System.out.println(
					"############################### \n"+
					"Press 1 for registering Apparatus \n"+
					"Press 2 for registering Exercise \n"+
					"Press 3 for registering Training \n"+
					"Press 4 for to view n last trainins \n"+
					"Press 5 to view resultlog for given timeperiod \n"+
					"Press 6 to create exercisegroups \n"+
					"Press 7 to view exercisegroups \n"+
					"Press 8 view average duration of training in weekdays \n"+
					"############################### \n");
			int userChoice = Integer.parseInt(scanner.nextLine());
			if(userChoice == 1) {
				registerApparatus();
			}
			else if(userChoice == 2) {
				registerExercise();;
			}
			else if(userChoice == 3) {
				registerWorkout();
			}
			else if(userChoice == 4) {
				getLastWorkouts();;
			}
			else if(userChoice == 5) {
				getResultsFromPeriod();
			}
			else if(userChoice == 6) {
				createExerciseGroup();
			}
			else if(userChoice == 7) {
				showExerciseGroup();
			}
			else if(userChoice == 8) {
				System.out.println("KJØR");
			}
			else {
				System.out.println("Wrong input");
			}
		}
	}
	
	private void registerApparatus() {
		System.out.println(
				"############################### \n"+
				"Write name of Apparatus \n" +
				"############################### \n");
		String apparatusName = scanner.nextLine();
		System.out.println(
				"############################### \n"+
				"Write description of Apparatus \n" +
				"############################### \n");
		String apparatusDescription = scanner.nextLine();
		///CONNNECTION TO DATABASE HERE
	}
	private void registerExercise() {
		while (true) {
			System.out.println(
					"############################### \n"+
					"Is the exercise with or without apparatus? Y/N \n" +
					"############################### \n");
			String exerciseType = scanner.nextLine();
			if(exerciseType.equals("Y")) {
				System.out.println(
						"############################### \n"+
						"Write: name, kilo, sett, apparatus \n" +
						"############################### \n");
				String exerciseInfo = scanner.nextLine();
				//CONNECT TO DATABASE
				break;
			}
			else if(exerciseType.equals("N")) {
				System.out.println(
						"############################### \n"+
						"Write: name, description \n" +
						"############################### \n");
				String exerciseInfo = scanner.nextLine();
				//CONNECT TO DATABASE
				break;
			}
			else {
				System.out.println("Wrong input");
			}
		}
	}	
	private void registerWorkout() {
		System.out.println(
				"############################### \n"+
				"Register workout: date, duration, form, performance, notes \n" +
				"############################### \n");
		String workoutInformation = scanner.nextLine();
		System.out.println(
				"############################### \n"+
				"Register exercises in workout: name1, name2, name3... \n" +
				"############################### \n");
		String exercises = scanner.nextLine();
		///CONNNECTION TO DATABASE HERE
	}
	private void getLastWorkouts() {
		System.out.println(
				"############################### \n"+
				"How many workouts do you want to view? \n" +
				"############################### \n");
		String numberOfWorkouts = scanner.nextLine();
		///CONNECT TO DATABASE
		System.out.println("RESULT FROM DATABASE");
	}
	private void getResultsFromPeriod() {
		System.out.println(
				"############################### \n"+
				"Write startdate: yyyy-mm-dd \n" +
				"############################### \n");
		String startDate = scanner.nextLine();
		System.out.println(
				"############################### \n"+
				"Write enddate: yyyy-mm-dd \n" +
				"############################### \n");
		String endDate = scanner.nextLine();
		//CONNECT TO DATABASE
	}
	private void createExerciseGroup() {
		System.out.println(
				"############################### \n"+
				"Write Exercisegroup name \n" +
				"############################### \n");
		String exerciseGroupName = scanner.nextLine();
		System.out.println(
				"############################### \n"+
				"Write exercises: exercise1, exercise2, exercise3...\n" +
				"############################### \n");
		String exercises = scanner.nextLine();
		//CONNECT TO DATABASE
	}
	private void showExerciseGroup() {
		System.out.println(
				"############################### \n"+
				"Write Exercisegroup name \n" +
				"############################### \n");
		String exerciseGroupName = scanner.nextLine();
		//CONNECT TO DATABASE
		System.out.println("DET SOM SKJER");
	}
	private void showAverageDurationForWeekdays() {
		//CONNECT TO DATABASE
	}

	
}
