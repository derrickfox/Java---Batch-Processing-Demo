/*
 * Derrick Fox
 * CS 214 - Advanced Java
 * Project 10 - Address Book
 * April 27, 2015
 * Java 1.8 JavaFX 2.2
 */
package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

class ComparePane extends BorderPane {
	
	
    private Label statusLabel = new Label("Pick One");
    private Button connectButton = new Button("Connect to Database");
    private TextArea reportTextArea = new TextArea();
    private Button batchButton = new Button("Batch Update");
    private Button unBatchButton = new Button("Non-Batch Update");
    private Statement myStmt;
    
    public ComparePane() throws SQLException {
    	//Statement myStmt = DBConnectionPane.connection.createStatement();

        myStmt = DBConnectionPane.connection.createStatement();
    	String sqlDropBookTable = "drop table if exists Temp;";
        myStmt.executeUpdate(sqlDropBookTable);
    	String sqlCreateBookTable = "create table Temp(num1 double, num2 double, num3 double);";
    	myStmt.executeUpdate(sqlCreateBookTable);
    	
    	Stage theStage = Main.theStage;
		  //Scene newScene = new Scene(new NewPane(), 300, 300);
		VBox mainBox = new VBox(5);
		HBox topBox = new HBox(5);
		HBox bottomBox = new HBox(5);
		
		topBox.getChildren().addAll(statusLabel, connectButton);
		bottomBox.getChildren().addAll(batchButton, unBatchButton);
		
		mainBox.getChildren().addAll(topBox, reportTextArea, bottomBox);
		
		Scene newScene = new Scene(mainBox, 300, 300);
		theStage.setTitle("Project 10");
		theStage.setScene(newScene);
		theStage.show();
		  
		  

    	connectButton.setOnAction(e -> checkIfConnected());
    	batchButton.setOnAction(e -> batchProcess());
		
    	unBatchButton.setOnAction(e -> unBatchProcess());
    	
    }
    public void checkIfConnected(){
    	
    	try {
			if (DBConnectionPane.connection.isClosed() == false) {
				statusLabel.setText("Connected"); 
			}else{
				new DBConnectionPane();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	long startTime = System.nanoTime();
    	
    	int testInt = 0;
    	for(int i = 0; i < 10000; i++) {
    		testInt++;
    	}
    	long estimatedTime = System.nanoTime() - startTime;
    	
    	System.out.println("Run time is " + (estimatedTime / 1000) + " nano seconds.");
    }
    
    public void unBatchProcess()   {
    	
    	double[] double1 = new double[1000];
    	double[] double2 = new double[1000];
    	double[] double3 = new double[1000];
    	
    	for (int i = 0; i < 1000; i++){
    		double1[i] = Math.random();
    		double2[i] = Math.random();
    		double3[i] = Math.random();
    	}
    	long startTime = System.nanoTime();

    	for (int i = 0; i < 1000 ; i++){
        	try {
				myStmt.executeUpdate("INSERT INTO Temp " +
							    "(num1, num2, num3)" +
					  			"values("+ double1[i] +", "+ double2[i] + ", " + double3[i] + ")");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	long estimatedTime = System.nanoTime() - startTime;
    	
    	reportTextArea.appendText("Un-Batched Update Complete\nTime Elapsed was " + (estimatedTime/1000) + " nanoseconds.\n\n");

    }
    
    public void batchProcess()   {
      	double[] double1 = new double[1000];
    	double[] double2 = new double[1000];
    	double[] double3 = new double[1000];
    	
    	for (int i = 0; i < 1000; i++){
    		double1[i] = Math.random();
    		double2[i] = Math.random();
    		double3[i] = Math.random();
    	}
    	System.out.println(double1[8] + " " + double2[384] + " " + double3[999]);
    	long startTime = System.nanoTime();

    	for (int i = 0; i < 1000 ; i++){
        	try {
   				myStmt.addBatch("insert into Temp values("+ double1[i] +", "+ double2[i] + ", " + double3[i] + ")");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		try {
			int count[] = myStmt.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Batch Sent");
    	long estimatedTime = System.nanoTime() - startTime;
    	reportTextArea.appendText("Batch Update Complete\nTime Elapsed was " + (estimatedTime/1000) + " nanoseconds.\n\n");
    }
   
}

