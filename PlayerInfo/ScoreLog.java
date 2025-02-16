// January 19 2023
// A class that logs the score. This class gives players a score in a text file.

package PlayerInfo;
import Display.*;
import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScoreLog{
  private String userName;
  private boolean isAppend=true;
  private Board gameBoard;
  private String line;
  private int highestScore;
  private static final int ITEM_MULTIPLIER = 8;
  private static final int ROW_MULTIPLIER = 20;
  //constructors
  public ScoreLog(Board gameBoard){
    this.gameBoard = gameBoard;
  }
  //accessors 
  public static int getITEM_MULTIPLIER(){
    return ITEM_MULTIPLIER;
  }
  public static int getROW_MULTIPLIER(){
    return ROW_MULTIPLIER;
  }
  
  public String getUserName(){
    return this.userName;
  }

  //mutators
  public void setUserName(String input){
    this.userName = input;
  }
  
  public void writeFinalScore(){
    try{
      // Create a FileWriter and PrintWriter for the user's score file
      FileWriter fw = new FileWriter("PlayerInfo/ScoreData/"+this.userName+"_Score.txt");
      PrintWriter pw = new PrintWriter (fw);

      // Print ASCII art and user's score information
      pw.println("  ______                             ");
      pw.println(".' ____ \\                            ");
      pw.println("| (___ \\_|.---.  .--.  _ .--. .---.  ");
      pw.println(" _.____`./ /'`\\] .'`\\ [ `/'`\\] /__\\\\ ");
      pw.println("| \\____) | \\__.| \\__. || |   | \\__., ");
      pw.println(" \\______.'.___.''.__.'[___]   '.__.' ");
      pw.println("");
      pw.println("");
      pw.println("            Your Score: " + (gameBoard.getClearedRows()*ROW_MULTIPLIER + gameBoard.getCollidedItems()*ITEM_MULTIPLIER));

      pw.println("╔═════════════════════════════════════╗");
      pw.println("║                                     ║"); 
      pw.println("║       Highest Score ever: "+ highestScore+"       ║");
      pw.println("║       Try and beat this score!      ║"); 
      pw.println("║                                     ║"); 
      pw.println("╚═════════════════════════════════════╝");  

      // Close the PrintWriter and FileWriter
      pw.close();
      fw.close();
    }
    catch(IOException e){
      System.out.println("Could not write to file "+this.userName+"_Score.txt");
    }
  }

  
  public void writePastScore(){
    try{
      // Create a FileWriter and PrintWriter for PastScores.txt with append mode
      FileWriter fw = new FileWriter("PlayerInfo/ScoreData/PastScores.txt", isAppend);
      PrintWriter pw = new PrintWriter (fw);
      // Get the current date and time
      DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
      LocalDateTime timeDate = LocalDateTime.now();
      // Write the user's past score information to PastScores.txt
      pw.println("Record: "+userName+" "+dtf.format(timeDate)+" Score: "+(gameBoard.getClearedRows()*ROW_MULTIPLIER + gameBoard.getCollidedItems()*ITEM_MULTIPLIER));
      pw.close();
      fw.close();
    }
    catch(IOException e){
      System.out.println("Could not write to file PastScores.txt");
    }
  }
  
  
  public void writeBasicScore(){
    try{
      // Create a FileWriter and PrintWriter for BasicScores.txt
      FileWriter fw = new FileWriter("PlayerInfo/ScoreData/BasicScores.txt", isAppend);
      PrintWriter pw = new PrintWriter (fw);
      // Calculate the basic score using cleared rows and collided items
      pw.println((gameBoard.getClearedRows() * ROW_MULTIPLIER + gameBoard.getCollidedItems()*ITEM_MULTIPLIER));
       // Close the PrintWriter and FileWriter
      pw.close();
      fw.close();
    }
    catch(IOException e){
      System.out.println("Could not write to file BasicScores.txt");
    }
  }

  
  public void readHighestScore(){
    // Create a FileReader and BufferedReader for BasicScores.txt
    int score;
    try{
      FileReader fr = new FileReader("PlayerInfo/ScoreData/BasicScores.txt");
      BufferedReader br = new BufferedReader(fr);
      
       // Read each line from the file
      line = br.readLine();
      while(line!=null){
        score = Integer.parseInt(line);
         // Update highestScore if the current score is greater
        if(score>highestScore){
          highestScore = score;
        }
         
        line = br.readLine();
      }
      
      br.close();
      
    }catch(IOException e){
      System.out.println("Could not read file BasicScores.txt");
    }
    
  }
  
}