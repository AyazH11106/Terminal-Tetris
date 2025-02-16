// January 19 2023
// The main class that handles user input and how that interacts with the gameplay loop

import Shapes.*;
import Display.*;
import PlayerInfo.*;
import java.util.*;

class Main {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    //variables and objects
    Board gameBoard = new Board();

    Tetromino nextTetro = new Tetromino();

    char keyStroke;
    
    boolean gameRunning = true;

    String userInput = "";
    String userName;
    
    Item powerUp = new Item();
    ScoreLog ScoreLog = new ScoreLog(gameBoard);
    

    //Getting  username
    do{
      System.out.println("                        Please enter your name: ");
      System.out.print("                        ");
      userName = input.next();

    }while(userName.length()<0);
    ScoreLog.setUserName(userName);

    System.out.print("\033[H\033[2J"); // clear screen
    
    printTitle();
    
    //getting username
    do{
      System.out.print("                        ");
      
      userInput = input.next();
      
    }while(!(userInput.equalsIgnoreCase("play") || userInput.equalsIgnoreCase("info")));

    if(userInput.equalsIgnoreCase("info")){
      printManual();
      input.next();
      System.out.print("\033[H\033[2J");
    }
    
      
    //game loop
    do {
      display(nextTetro, gameBoard, powerUp);

      if (gameBoard.canMoveTetroDown()) {

        gameBoard.lowerTetromino();
        gameBoard.clearFilledRows();

        try {
          Thread.sleep(200);
        } catch (InterruptedException e) {
          System.out.println("System couldn't go to sleep");
        }

      }else if(gameBoard.getItemCollided()){

        powerUp.useItem(gameBoard);
        
      }else {
        
        
        //Checks the game if a block has passed the threshold line
        if (gameBoard.isPastThreshold()) {
          System.out.println("GAME OVER!\nYour score can be found in the "+ userName +"_Score.txt file (PlayerInfo/ScoreData/"+ userName +"_Score.txt)");
          //stops game loop
          gameRunning = false;
          
          
        }else{
          keyStroke = input.next().toLowerCase().charAt(0);
          if(keyStroke == 'i'){
            printManual();
            keyStroke = input.next().toLowerCase().charAt(0);
          }
          handleKeyInput(nextTetro, gameBoard, keyStroke, powerUp);
          
        }//end of keystroke else


      }//end of canMoveTetrodown else

    } while (gameRunning);

    ScoreLog.writeBasicScore();
    ScoreLog.readHighestScore();
    ScoreLog.writePastScore();
    ScoreLog.writeFinalScore();
    
    
    
  }// end main method


  public static void display(Tetromino tetro, Board gameBoard, Item powerUp) {
    System.out.print("\033[H\033[2J"); // clear screen
    System.out.println(tetro + "\n");
    System.out.println(gameBoard);
    if(powerUp.getSequenceStarted()){
      System.out.println("SEQUENCE HAS BEEN ACTIVATED. THE SEQUENCE IS:\n SQUARE, L, T, LINE, U, Z, J, O, DOUBLE L");
    }
    



  }// end display method

  public static void handleKeyInput(Tetromino tetro, Board gameBoard, char keyStroke, Item powerUp) {
    
    switch (keyStroke) {
      // w and s for rotating
      case 'w':
        tetro.rotate("Clockwise");
        break;

        
      case 's':
        tetro.rotate("AntiClockwise");
        break;

        
      // e to insert tetromino into board
      case 'e':
       //checks if a tetromino has been placed
        if (tetro.getIsTetrominoPlaced()) {
          break;
        } else {
          powerUp.insertPowerUp(gameBoard); // item gets inserted
          gameBoard.insertTetromino(tetro); // next shape gets inserted
          
          if(powerUp.getSequenceStarted()){
            tetro.nextShape();
            powerUp.continueSequence();
            tetro.setIsTetrominoPlaced(true);
            
          }else{
            gameBoard.insertTetromino(tetro); // next shape gets inserted
            tetro.setIsTetrominoPlaced(true); //set to true to prevent multiple tetrominos from being placed on board
            tetro.randomShape();
            break;
          }
          
        }

        
      case 'd':
        gameBoard.moveTetrominoX("right");
        break;

        
      case 'a':
        gameBoard.moveTetrominoX("left");
        break;

        
      // f to make the tetromino fall
      case 'f':
        tetro.setIsTetrominoPlaced(false);
        gameBoard.readyToFall();
        break;
    }

  }// end of handlekeyinput method

  public static void printManual(){
    System.out.print("\033[H\033[2J"); // clear screen
    System.out.println(" ██████╗  █████╗ ███╗   ███╗███████╗    ███╗   ███╗ █████╗ ███╗   ██╗██╗   ██╗ █████╗ ██╗");
    System.out.println("██╔════╝ ██╔══██╗████╗ ████║██╔════╝    ████╗ ████║██╔══██╗████╗  ██║██║   ██║██╔══██╗██║");
    System.out.println("██║  ███╗███████║██╔████╔██║█████╗      ██╔████╔██║███████║██╔██╗ ██║██║   ██║███████║██║");
    System.out.println("██║   ██║██╔══██║██║╚██╔╝██║██╔══╝      ██║╚██╔╝██║██╔══██║██║╚██╗██║██║   ██║██╔══██║██║");
    System.out.println("╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗    ██║ ╚═╝ ██║██║  ██║██║ ╚████║╚██████╔╝██║  ██║███████╗");
    System.out.println(" ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝    ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝");
    System.out.println("         ╔═══════════════════════════════════════════════════════════════════════╗");
    System.out.println("         ║                                                                       ║");
    System.out.println("         ║                                \u001B[34m**RULES**\u001B[0m                              ║");
    System.out.println("         ║            You must rotate the Tetriminos before they fall            ║");
    System.out.println("         ║                and fit them together to create lines.                 ║");
    System.out.println("         ║                                                                       ║");
    System.out.println("         ║    If you cannot complete a line, the blocks will eventually stack    ║");
    System.out.println("         ║                and rise to the top of the playing field               ║");
    System.out.println("         ║                         and the game will end.                        ║");
    System.out.println("         ║                                                                       ║");
    System.out.println("         ║                                                                       ║");
    System.out.println("         ║                              \u001B[34m**POWER-UPS**\u001B[0m                            ║");
    System.out.println("         ║        There are 3 Power-Ups that can be used by falling on them:     ║");
    System.out.println("         ║                                                                       ║");
    System.out.println("         ║        🎯 SEQUENCE - Makes the next 3 blocks fall in a sequence       ║");
    System.out.println("         ║      ⏭ RIGHT GRAVITY - Shifts all blocks one right after falling      ║");
    System.out.println("         ║       ⏮ LEFT GRAVITY - Shifts all blocks one left after falling       ║");
    System.out.println("         ║                                                                       ║");
    System.out.println("         ║                                                                       ║");
    System.out.println("         ║                           \u001B[34m**HOW TO GET SCORE**\u001B[0m                        ║");
    System.out.println("         ║                     8 score for every item collected                  ║");
    System.out.println("         ║                       20 score for a row completed                    ║");
    System.out.println("         ║                                                                       ║");
    System.out.println("         ║                                                                       ║");
    System.out.println("         ║      ═══ ═══ ═══ ═══ ═══ ═══ ═══ ═══ ═══ ═══ ═══ ═══ ═══ ═══ ═══      ║");
    System.out.println("         ║                                                                       ║");
    System.out.println("         ║                                                                       ║");
    System.out.println("         ║                              \u001B[34m**CONTROLS**\u001B[0m                             ║");
    System.out.println("         ║      Enter W or S to rotate the upcoming Tetromino clockwise or       ║");
    System.out.println("         ║        anticlockwise respectively. Enter E when done rotating         ║");
    System.out.println("         ║                                                                       ║");
    System.out.println("         ║     Once block is on board, enter A or D to move it left or right     ║");
    System.out.println("         ║               respectively. Enter F to drop the tetromino             ║");
    System.out.println("         ║                                                                       ║");
    System.out.println("         ║                                                                       ║");
    System.out.println("         ║                 \u001B[44mWhile playing enter i to see manual again\u001B[0m             ║");
    System.out.println("         ║                          \u001B[36mEnter anything to play\u001B[0m                       ║");
    System.out.println("         ╚═══════════════════════════════════════════════════════════════════════╝");
  }//end printManual


  public static void printTitle(){
    System.out.print("\033[H\033[2J");
    System.out.println("████████╗███████╗████████╗██████╗  ██████╗ ███╗   ███╗██╗ ██████╗  ██████╗ ███████╗");
    System.out.println("╚══██╔══╝██╔════╝╚══██╔══╝██╔══██╗██╔═══██╗████╗ ████║██║██╔════╝ ██╔═══██╗██╔════╝");
    System.out.println("   ██║   █████╗     ██║   ██████╔╝██║   ██║██╔████╔██║██║██║  ███╗██║   ██║███████╗");
    System.out.println("   ██║   ██╔══╝     ██║   ██╔══██╗██║   ██║██║╚██╔╝██║██║██║   ██║██║   ██║╚════██║");
    System.out.println("   ██║   ███████╗   ██║   ██║  ██║╚██████╔╝██║ ╚═╝ ██║██║╚██████╔╝╚██████╔╝███████║");
    System.out.println("   ╚═╝   ╚══════╝   ╚═╝   ╚═╝  ╚═╝ ╚═════╝ ╚═╝     ╚═╝╚═╝ ╚═════╝  ╚═════╝ ╚══════╝");
    System.out.println("                 Please use f11 to fullscreen for optimal experience\n");
    System.out.println("                        ╔════════════════════════════════╗");
    System.out.println("                        ║                ║               ║");
    System.out.println("                        ║ Enter \"Play\"   ║  Enter \"Info\" ║");
    System.out.println("                        ║   to start     ║  for manuals  ║");
    System.out.println("                        ║                ║               ║");
    System.out.println("                        ╚════════════════════════════════╝");
  }//end printTitle
  
}// end main class