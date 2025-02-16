// January 12 2023
// A class that represents a tetromino. This class can rotate the tetromino and also has other funcionalities

package Shapes;
import java.util.*;
public class Tetromino {
  // fields
  private static final int[][] SQUARE = {{1, 1},
                                         {1, 1}};
  private static final int[][] L = {{1, 0},
                                    {1, 0},
                                    {1, 1}};

  private static final int[][] T = {{1, 0},
                                    {1, 1},
                                    {1, 0}};

  private static final int[][] LINE = {{1},
                                       {1},
                                       {1}};
  
  private static final int[][] U = {{1,1},
                                    {1,0},
                                    {1,1}};

  private static final int[][] Z = {{0,1},
                                    {1,1},
                                    {1,0}};

  private static final int[][] J = {{0,1},
                                    {0,1},
                                    {1,1}};
  
  private static final int[][] O = {{1,1,1},
                                    {1,0,1},
                                    {1,1,1}};

  private static final int[][] DOUBLE_L = {{0,1,0},
                                          {0,1,0},
                                          {1,1,1}};
                                  

  private static final int[][][] ALL_SHAPES = {SQUARE, L, T, LINE, U, Z, J, O, DOUBLE_L};

  private int[][] shape = SQUARE;

  private boolean isTetrominoPlaced = false;

  private int shapeIndex;

  
  // Constructors
  public Tetromino() {
    // Randomly select a shape
    this.shapeIndex = (int) (Math.random() * ALL_SHAPES.length); //to keep track for nextShape method
    this.shape = ALL_SHAPES[shapeIndex];
    this.isTetrominoPlaced = false;//isTetrominoPlaced set to false when a tetromino is created
  }

  // Overriding with shape name
  public Tetromino(String shape) {

    switch(shape.toLowerCase()){
      case "line":
        this.shape = LINE;
        break;
      case "t":
        this.shape = T;
        break;
      case "l":
        this.shape = L;
        break;
      default:
        this.shape = SQUARE;
        break;
    }  
  }
  



  // Accessors
  public int[][] getShape(){
    return this.shape;
  }

  public int getHeight(){
    return this.shape.length;
  }
  
  public int getWidth(){
    return this.shape[0].length;
  }

  public boolean getIsTetrominoPlaced(){
    return this.isTetrominoPlaced;
  }

  // Mutators 
  public void setIsTetrominoPlaced(boolean input){
    this.isTetrominoPlaced = input;
  }

  // Instance methods
  // Rotating clockwise or anti
  public void rotate(String direction) {
      int rows = shape.length;
      int cols = shape[0].length;
      int[][] rotatedShape = new int[cols][rows];

      for (int i = 0; i < rows; i++) {
          for (int j = 0; j < cols; j++) {
            if(direction.equalsIgnoreCase("clockwise")){
              rotatedShape[j][rows - 1 - i] = shape[i][j];
            }else{
              rotatedShape[j][i] = shape[i][cols - 1 - j];
            }
            
          }
      }

      shape = rotatedShape;
  }

  
  // Randomizes the current tetromino shape
  public void randomShape(){
    // Randomly select a shape
    this.shapeIndex = (int) (Math.random() * ALL_SHAPES.length); //to keep track for nextShape method
    this.shape = ALL_SHAPES[shapeIndex];
    
  }

  
  // moves on to the next shape in the sequence
  public void nextShape(){
    this.shapeIndex ++;
    this.shape = ALL_SHAPES[this.shapeIndex % ALL_SHAPES.length];
  }

  
  // Gets an array of the top down heights of each teromino column until their lowest blocks
  public int[] getBottoms() {
    int[] bottoms = new int[this.getWidth()];
    
    for(int column = 0; column < this.getWidth(); column++){ //goes through all the column of this tetromino object
      int lowestBlockInColumnIndex = this.getHeight() - 1;  
      
      while(this.shape[lowestBlockInColumnIndex][column] != 1){ //while a block is not encountered at the bottom of the column
        lowestBlockInColumnIndex -= 1; //goes up by one
      }
      bottoms[column] = lowestBlockInColumnIndex;
    }
    return bottoms;
  }


  

  
  // toString method that return the displayed block as a string in a box
  public String toString(){
    String tetrominoString = "╔═";
  
    for(int i = 0; i < this.shape[0].length; i++) {
      tetrominoString += "══";
    }
    tetrominoString += "═╗";
  
    for(int i = 0 ; i < this.shape.length; i++) {
      tetrominoString += "\n║";
      for(int j = 0; j < this.shape[0].length; j++) {
        if(j == 0){
          tetrominoString += " ";
        }
        if (this.shape[i][j] == 0){
          tetrominoString += "  ";
        }else{
          tetrominoString += "⬜";
        }
      }
      tetrominoString += " ║";

    }
    
    tetrominoString += "\n╚═";
    for(int i = 0; i < this.shape[0].length; i++) {
        tetrominoString += "══";
    }
    tetrominoString += "═╝";
    
    return tetrominoString;
  }
}//end class