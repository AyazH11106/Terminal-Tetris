// January 19 2023
// A class that represents where the tetromino show up on screen, it also includes game logic such as clearing a row when it is filled

package Display;

import PlayerInfo.ScoreHandiling;
import Shapes.*;

public class Board {
  // Fields
  // 2-D Array for the game board
  private static final int BOARD_HEIGHT = 20; // rows
  private static final int BOARD_WIDTH = 15; // columns
  private final int THRESHOLD;
  
  private int[][] gameBoard;

  private int tetroWidth = 0;
  private int tetroHeight = 0;
  private int tetroX; // top left x coordinate of inserted tetromino
  private int tetroY; // how far the inserted tetromino is from the ceiling (distance from ceiling)
  private int[] tetroBottoms;

  private boolean canFall = false;

  private boolean itemCollided; 
  private int clearedRows = 0;
  private int collidedItems = 0;
  


  // Constructors
  public Board() {
    this.gameBoard = new int[BOARD_HEIGHT][BOARD_WIDTH];
    this.THRESHOLD = 3;
    this.populateMap(); // filling it with 0 which are spaces when printing
    this.clearedRows = 0;
    this.collidedItems = 0;
  }

  public Board(int threshold) {
    this.gameBoard = new int[BOARD_HEIGHT][BOARD_WIDTH];
    this.THRESHOLD = threshold;
    this.populateMap(); // filling it with 0 which are spaces when printing

  }

  //accesors
  public int getThreshold(){
    return this.THRESHOLD;
  }
  
  public int getCell(int i, int j){
    return this.gameBoard[i][j];
  }
  
  public boolean getItemCollided(){
    return itemCollided;
  }

  public int getHeight(){
    return BOARD_HEIGHT;
  }

  public int getWidth(){
    return BOARD_WIDTH;
  }

  public int[][] getGameBoard(){
    return gameBoard;
  }

  public int getRandomRow(){
    return (int) ((BOARD_HEIGHT - THRESHOLD-1) * Math.random() + THRESHOLD);
  }

  public int getRandomColumn(){
    return (int) (BOARD_WIDTH * Math.random());
  }
  public int getClearedRows(){
    return this.clearedRows;
  }
  public int getCollidedItems(){
    return this.collidedItems;
  }
  
  //mutators
  public void setCell(int i, int j, int value){
    this.gameBoard[i][j] = value;
  }
  
  public void toggleItemCollided(){
    itemCollided = !itemCollided;
  }
  
  
  //TETROMINO HANDLING
  //Mutator method to set canFall to true when the tetromino is ready to fall
  public void readyToFall(){
    if(this.tetroWidth != 0){ //this double checks if its on the board
      this.canFall = true;
    }
  }



  // Instance methods
  //Populate the game board with empty spaces (0)
  public void populateMap() {// with empty spaces which is 0
    for (int i = 0; i < BOARD_HEIGHT; i++) {
      for (int j = 0; j < BOARD_WIDTH; j++) {
        this.gameBoard[i][j] = 0;
      }
    }

  }

  // Inserting the tetromino from the queue into the board
  public void insertTetromino(Tetromino tetro) {
    this.tetroHeight = tetro.getHeight();
    this.tetroWidth = tetro.getWidth();
    this.tetroBottoms = tetro.getBottoms();

    //copying the shape of the tetromino onto the board
    for (int i = 0; i < this.tetroHeight; i++) {
      for (int j = 0; j < this.tetroWidth; j++) {
        this.gameBoard[i][j] = tetro.getShape()[i][j];
      }
    }
    
    //position reset for tetromino
    this.tetroX = 0;
    this.tetroY = 0;

  }// end of insertTetromino

  // Moving the inserted tetromino left or right on the board by one
  public void moveTetrominoX(String direction) {
    if (this.tetroHeight != 0) { //double checking if tetro is on board
      
      if(direction.equalsIgnoreCase("right")){
        
        if(this.tetroX + this.tetroWidth < BOARD_WIDTH){ //if tetromino hasnt reached the right border
          for(int i = 0; i < this.tetroHeight; i++) {
            for(int j = this.tetroX + this.tetroWidth; j > this.tetroX; j--) {
              this.gameBoard[i][j] = this.gameBoard[i][j - 1];
              this.gameBoard[i][j - 1] = 0;
            }
          }
          this.tetroX += 1; 
        }
        
      }else if(direction.equalsIgnoreCase("left")){
        if(this.tetroX > 0){ //if tetromino hasnt reached the left border
          for(int i = 0; i < this.tetroHeight; i++) {
            for(int j = this.tetroX - 1; j < this.tetroX + this.tetroWidth - 1; j++) {
              this.gameBoard[i][j] = this.gameBoard[i][j + 1];
              this.gameBoard[i][j + 1] = 0;
            }
          }
          this.tetroX -= 1; 
        }
        
      }
      
    }
    
  }

  //moves the tetromino down when on the board. This was going to be part of move tetromino but it would get too crowded looking so I made it a seperate method
  public void lowerTetromino(){

    //loops through the columns of the tetro on the board
    for(int j = this.tetroX; j < this.tetroX + this.tetroWidth; j++){
      //loops through the rows but from the lowest block to the top
      for(int i = this.tetroY + this.tetroBottoms[j - this.tetroX] + 1; i > this.tetroY; i--){
        if(this.gameBoard[i][j] != 1){
          if(this.gameBoard[i][j] != 0){//checking if block ate an item
            this.itemCollided = true; 
            this.collidedItems += 1;
          }
          this.gameBoard[i][j] = this.gameBoard[i-1][j];
          this.gameBoard[i-1][j] = 0;
        }
      }
    }
    this.tetroY += 1;
  
  }
  
  // Checking if the tetromino can move down
  public boolean canMoveTetroDown(){
    int i;
    if(this.canFall){
      //loops through the columns of the tetro on the board
      for(int j = this.tetroX; j < this.tetroX + this.tetroWidth; j++){
        i = this.tetroY + this.tetroBottoms[j - this.tetroX]; // Lowest block index of tetromino at column j
        
        if(i == BOARD_HEIGHT - 1){ //if the last row of tetromino is the border
          this.canFall = false;
          return this.canFall;
        }else if(this.gameBoard[i+1][j] == 1 && this.gameBoard[i][j] == 1){ //if the last row of tetromino and the cell below it is 1
          this.canFall = false;
          return this.canFall;
        }
      }
    }
    return this.canFall;
  }

  //checks if a row on the game board is able to be cleared
  private boolean isRowFilled(int rowNumber){
    int total = 0;// keeps track of how many 1s are in the row
    //Iterate through each column in game board
    for(int j = 0; j < BOARD_WIDTH; j++){
      //checks if row is equal to 0
      if(this.gameBoard[rowNumber][j] == 1){ 
        //Increments total if the cell is 1
        total++;
      }
    }
    //if the total is equal to the width of the board, the row is filled
    if(total >= BOARD_WIDTH){
      this.clearedRows+=1;//add
      return true;
    }
    else{
      return false;
    }
    
  }
  
  //clears row with zeros if a row has been filled
  public void clearFilledRows(){
    //Iterate through each row in game board
    for(int i = 0; i < BOARD_HEIGHT; i++){
      //checks if current row is filled
      if(this.isRowFilled(i)){
        //clear filled row 
        for(int j = 0; j < BOARD_WIDTH; j++){
          this.gameBoard[i][j] = 0;
        }
        // allows the rows above the cleared row to shift down
        for(int k = i-1; k >= 0; k--){
          for(int j = 0; j < BOARD_WIDTH; j++){
            //shift the current row down the next row
            this.gameBoard[k+1][j] = this.gameBoard[k][j];
            //clears current row
            this.gameBoard[k][j] = 0;
          }
        }
      }
      
    }
  }

  //checks if Teromino is past the threshold line to indicate if player has lost the game
  public boolean isPastThreshold(){
    int count = 0;
    //Iterate through each column in game board
    for(int j=0; j<BOARD_WIDTH; j++){
      //checks if a block is touching a specified row is 
      if(this.gameBoard[this.THRESHOLD][j]==1){
        //if a block is touching the specified row, increment count
        count++;
        
      }
    }
    //if count is greater than 0, there is a block touching the specified row, otherwise no blocks are touching the specified row
    if(count > 0){
      return true;
    }
    else{
      return false;
    }
  }



  public boolean isOnBoard(int value){
    for(int i = 0; i < BOARD_HEIGHT; i++){
      for(int j = 0; j < BOARD_WIDTH; j++){
        if(this.gameBoard[i][j] == value){
          return true;
        }
      }
    }
    return false;
  }


  
  
  // toString makes it so it returns the entire board with all the tetrominos as a string which we can print out
  public String toString() {
    String boardString = "╔═";

    for (int i = 0; i < BOARD_WIDTH; i++) {
      boardString += "══";
    }
    boardString += "═╗";

    for (int i = 0; i < BOARD_HEIGHT; i++) {
      boardString += "\n║";
      
      for (int j = 0; j < BOARD_WIDTH; j++) {
        
        if (j == 0) {
          boardString += " ";
        }
        if (this.gameBoard[i][j] == 1){
          boardString += "⬜";
        } else if (this.gameBoard[i][j] == 2){
          boardString += "🎯";
        } else if (this.gameBoard[i][j] == 3){
          boardString += "⏩";
        } else if (this.gameBoard[i][j] == 4){
          boardString += "⏮ ";
        } else if(i == this.THRESHOLD){
          boardString += "_ ";
        } else if(this.gameBoard[i][j] == 0) {
          boardString += "  ";
        }
      }
      boardString += " ║";
    }

    boardString += "\n╚═";
    for (int i = 0; i < BOARD_WIDTH; i++) {
      boardString += "══";
    }
    boardString += "═╝";
    
    if(this.itemCollided){
      boardString += "\n"+ ScoreHandiling.getITEM_MULTIPLIER()+" points";
    }

    return boardString;
  }

}// end class