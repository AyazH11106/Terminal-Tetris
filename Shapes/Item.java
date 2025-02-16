// January 19 2023
// A class that represents an item. This class changes gameplay mechanics by either giving the next few tetrominos a sequence, or shifting the gravity by one block 

import java.util.*;
import Display.*;

public class Item {

  //fields
  private String type;
  private static String[] ALL_TYPES ={"sequence","rightGravity","leftGravity"};

  private int tetroSequence;
  private boolean sequenceStarted;
  

  
  
  //constructors
  public Item(){
    this.type = ALL_TYPES[(int) (Math.random() * ALL_TYPES.length)];
  }

  
  //accessors
  public String getType(){
    return this.type;
  }
  
  public int getSequence(){
    return this.tetroSequence;
  }

  public boolean getSequenceStarted(){
    return this.sequenceStarted;
  }

  
  //inserts the item randomly into game board
  public void insertPowerUp(Board gameBoard){
    int i, j, itemProbability;

    itemProbability = (int) (2 * Math.random()); //33% chance
  
    if(itemProbability == 1 && !gameBoard.isOnBoard(2) && !gameBoard.isOnBoard(3) && !gameBoard.isOnBoard(4)){
      do{
        i = gameBoard.getRandomRow();
        j = gameBoard.getRandomColumn();
      }while(gameBoard.getCell(i, j) != 0);

      changePowerUp();
      gameBoard.setCell(i, j, typeToNum(this.type));
      
    }  

  }


  //randomizes item type
  private void changePowerUp() {
    this.type = ALL_TYPES[(int) (Math.random() * ALL_TYPES.length)];
  }


  
  public void useItem(Board gameBoard){
    switch (this.type){
      case "sequence":
        this.continueSequence(); //when the tetro touches item it kickstarts sequence from 0 to 1 which starts the sequence 
        gameBoard.toggleItemCollided();
        this.sequenceStarted = true;
        break;

      case "rightGravity":
        rightGravity(gameBoard);
        gameBoard.toggleItemCollided();
        break;

      case "leftGravity":
        leftGravity(gameBoard);
        gameBoard.toggleItemCollided();
        break;

      default:
        break;
    }
  }

  //increases the sequence up to 3 times when the sequence power up has been activated
  public void continueSequence(){
    if(this.tetroSequence < 3){
      this.tetroSequence++;
    }else{
      this.tetroSequence = 0;
      this.sequenceStarted = false;
    }
    
  }


  public void rightGravity(Board gameBoard){
    for (int j = gameBoard.getWidth() - 1; j > 0; j--) {
      for (int i = gameBoard.getThreshold(); i < gameBoard.getHeight(); i++) {
        if (gameBoard.getGameBoard()[i][j] == 0) {
          gameBoard.getGameBoard()[i][j] = gameBoard.getGameBoard()[i][j-1];
          gameBoard.getGameBoard()[i][j-1] = 0;
        }
      }
    }
  }

  public void leftGravity(Board gameBoard){
    for (int j = 0; j < gameBoard.getWidth() - 1; j++) {
      for (int i = gameBoard.getThreshold(); i < gameBoard.getHeight(); i++) {
        if (gameBoard.getGameBoard()[i][j] == 0) {
          gameBoard.getGameBoard()[i][j] = gameBoard.getGameBoard()[i][j+1];
          gameBoard.getGameBoard()[i][j+1] = 0;
        }
      }
    }
  }


  
  // Method to find the index of a string in a string array
  private static int typeToNum(String type) {
      for (int i = 0; i < ALL_TYPES.length; i++) {
          if (ALL_TYPES[i].equals(type)) {
              return i + 2; // adding 2 because 0 is empty and 1 is ⬜
          }
      }
      return 0; // Return empty space if the string is not found
  }
  
}//end class
