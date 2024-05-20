/*
Shahmeer Khan

Apr. 18, 2024

Purpose: 
Recreate the randomized guessing board game: Mastermind, that generates a sequence of colors to be guessed by the user in a total of 10 attempts. This game will allow the user to learn from their previous guesses through a system where an x is placed for a wrong placement guess, a dash for a wrong placement and correct color, and an O for perfect color and placement. This game is offered in both easy and hard difficulty.
*/
import java.util.*;
import java.io.*;
public class Main {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    //variables 
    String username="";
    final String MENU = "\n1. Play\n2. How To\n3. Exit\n";
    int choice = 0;
    char randomColours[] = new char[5];
    String difficulty="";
    final char COLOURS[] = {'R','G','B','K','W','P','Y','V'};
    boolean valid = true,win=false;
    String guess="";
    char[]guessArray = new char[0];
    char[][]guessGrid = new char[10][5];
    char[][]comparisonGrid = new char[10][5];
    int trial = 0;

    System.out.println("\t\t\tWELCOME TO MASTERMIND\n\n\n");
    System.out.print("Enter your username: ");//asking for username to use later
    username = input.next();
    System.out.println("Welcome, " + username + "!");
    do{
      do{
        try{
          System.out.println(MENU);//printing menu as a single string variable
          System.out.print("Enter your choice: ");
          choice = input.nextInt();

        }catch(InputMismatchException e){
          System.out.println("Invalid Input.");
          choice = 0;
          input.next();
        }
      }while(choice<1||choice>3);

      if(choice==2){
        howTo();//printing the instructions if the user chooses "How To"
      }
      else if(choice==3){
        exitFunc();//printing the exit message using the exit method if the user chooses "Exit"
      }
      else{//running through game simulation if the user chooses neither

        do{
          System.out.print("Enter difficulty (Easy or Hard): ");
          difficulty = input.next();
        }while(!difficulty.equalsIgnoreCase("Easy")&&!difficulty.equalsIgnoreCase("Hard"));//error checking difficulty

        randomColours = generateColours(difficulty, COLOURS);//invoking generateColours()

        final int RANDOM_LENGTH = randomColours.length;


        do{
          System.out.println("\n\n-------------------------------TRIAL #" + (trial+1) + "------------------------------\n\n");//printing trial #
          do{
            valid = true;//resetting valid to true each time the loop runs
            System.out.println("\nEnter your code:");
            guess = input.next();
            if(guess.length()!=5){
              System.out.println("Your code must have 5 pins");
            }
            else{
              guess = guess.toUpperCase();
              guessArray = guess.toCharArray();//converting the user's guess into a char array
              valid = checkColours(guessArray,randomColours,COLOURS);//checking if the colours the user entered exist

            }
            if(!valid){
              System.out.println("\nREDIRECTING...\nEnter a valid code. Colours must exist.\n");
              System.out.println("VALID COLOURS: Red: R    |   Green: G   | Blue: B   | Black: K   |  White: W   | Pink: P   | Yellow: Y    |    Violet: V");
            }//invalid colours message

          }while(guess.length()!=5||!valid);//keep asking user to enter colours, while colours are not of appropriate length or they don't exist

          //invoking compareGrids() to compare guess with random colours
          compareGrids(guessGrid,comparisonGrid,guessArray,randomColours,trial);
          //invoking printGrids() to print both the guess grid and the comparison grid
          printGrids(guessGrid,comparisonGrid);
          trial++;//incrementing trial #

        }while(trial<10&&!checkWin(randomColours,guessArray));//game simulation will keep running as long as the user has less than 10 trials and they have not guessed the colours yet

        if(!checkWin(randomColours,guessArray)){//printing lose message to console and file
          win = false;
          System.out.print("\n\nYou have ran out of all of your tries. Sadly, this means you lose. The correct code was: ");
          for(int i=0;i<RANDOM_LENGTH;i++){
            System.out.print(randomColours[i]);
          }
          System.out.println("\n\n");
          printToFile(username,win,trial);
        }
        else{//printing win message to console and file
          win = true;
          if(trial!=1){
            System.out.println("\n\nCongratulations! You guessed the random colours in " + trial + " tries! You win!");
          }else{
            System.out.println("\n\nCongratulations! You guessed the random colours in " + trial + " try! You win!");
          }

          printToFile(username,win,trial);
        }

        trial = 0;//resetting trial # to 0 so that the simulation can restart if the user desires

      }

    }while(choice!=3);//keep looping back to the menu, as long as the user does not choose "Exit"

    }//end of main method

  //method to check if colours the user entered exist
  public static boolean checkColours(char guessArray[],char randomColours[], char[]COLOURS){
    final int RANDOM_LENGTH = randomColours.length;
    boolean check = true;
    for(int i=0;i<RANDOM_LENGTH;i++){
      switch(guessArray[i]){
        case('R'):
          check = true;
          break;
        case('G'):
          check = true;
          break;
        case('B'):
          check = true;
          break;
        case('K'):
          check = true;
          break;
        case('W'):
          check = true;
          break;
        case('P'):
          check = true;
          break;
        case('Y'):
          check = true;
          break;
        case('V'):
          check = true;
          break;
        default:
          check = false;
          break;
      }
      if(!check){//return check right away if any of the colours don't exist (we don't want it to keep looping)
        return check;
      }
    }//end for loop
    return check;
  }//end of checkColours method

  //method to print the instructions
  public static void howTo(){
    System.out.println("\t\t\t\t\t\t\t\t\t\tHOW TO\n\n");
    System.out.println("The computer generates a sequence of 5 colours, the user tries to guess the sequence and is given 10 trials. After each trial the computer gives the user information on which colour is in the correct place, is correct but not in the right place, or is wrong. Based on the information given after every guess the user tries to make a better guess. The game ends when the user guesses all correct positions for all colours or if the user is out of trials (10 trials).");
    System.out.println("\nVALID COLOURS: Red: R    |   Green: G   | Blue: B   | Black: K   |  White: W   | Pink: P   | Yellow: Y    |    Violet: V");
  }//end of howTo() method

  //method to print exit message
  public static void exitFunc(){
    System.out.println("Ending Mastermind\n");
    try{

      String exitText = "Program Exterminated";
      char exitTextArray [] = exitText.toCharArray();
      int ArrayLength = exitTextArray.length;
      //adding wait times and dots for a better UI
      Thread.sleep(1000);
      System.out.print(".");
      Thread.sleep(1000);
      System.out.print(".");
      Thread.sleep(1000);
      System.out.print(".\n\n");
      //looping through charArray and adding a short wait time for a better UI
      for(int i = 0; i <ArrayLength; i++){
        System.out.print(exitTextArray[i]);
        Thread.sleep(150);
      }

    }catch(InterruptedException e){
      System.out.println("Thread Error.");
    }
  }//end of exitFunc() method

  //method for printing the user's score to a file
  //takes in the score (number of attempts to break the code, as well as the number of times the program ran)
  public static void printToFile(String username, boolean win, int trial){
    try{
      FileWriter fw = new FileWriter(username + ".txt", true);
      PrintWriter pw = new PrintWriter(fw);
      if(win){
        pw.println("You won this game. Your score was " + trial);
      }else{
        pw.println("You have ran out of all of your tries. Sadly, this means you lose");
      }
      pw.close();
    }catch(IOException e){
      System.out.println("Could not write to file");
    }//end of catch
  }//end of printToFile() method

  //method to generate random colours, based on difficulty chosen by user
  public static char[] generateColours(String difficulty, char colours[]){
    final int COLOURS_LENGTH = colours.length;
    char randomColours[] = new char[5];
    final int RANDOM_LENGTH = randomColours.length;
    int randomIndex=0;

    for(int i=0;i<RANDOM_LENGTH;i++){
      randomIndex = (int)(Math.random()*COLOURS_LENGTH);
      randomColours[i]=colours[randomIndex];

      if(difficulty.equalsIgnoreCase("Easy")){
        for(int j=0;j<RANDOM_LENGTH;j++){
          while(randomColours[i]==randomColours[j]&&i!=j){
            randomIndex = (int)(Math.random()*COLOURS_LENGTH);
            randomColours[i] = colours[randomIndex];
            j=0;
          }
        }
      }
    }

    return randomColours;
  }//end of generateColours() method

  public static void compareGrids(char[][]guessGrid,char[][] comparisonGrid,char[]guessArray,char[]randomColours,int trial){
    final int ROWS = guessGrid.length;
    final int COLS = guessGrid[0].length;

    if(trial==0){
      for(int i=0;i<ROWS;i++){
        for(int j=0;j<COLS;j++){
          guessGrid[i][j]='X';
          comparisonGrid[i][j] = 'X';
        }//end columns for loop
      }//end rows for loop
    }//end if

    for(int j=0;j<COLS;j++){//end for loop
      guessGrid[trial][j] = guessArray[j];

      if(guessArray[j]==randomColours[j]){
        comparisonGrid[trial][j] = 'O';
      }//end if

      else if (guessArray[j]==randomColours[0]||guessArray[j]==randomColours[1]||guessArray[j]==randomColours[2]||guessArray[j]==randomColours[3]||guessArray[j]==randomColours[4]){
        comparisonGrid[trial][j] = '-';
      }//end else if

      else{
        comparisonGrid[trial][j] = 'X';
      }//end else
    }//end for loop

  }//end of compareGrids method

  //printing grids method
  public static void printGrids(char[][]guessGrid,char[][]comparisonGrid){
    final int ROWS = guessGrid.length;
    final int COLS = guessGrid[0].length;

    //color codes
    final String RESET = "\033[0m";
    final String RED = "\u001b[31m";
    final String GREEN = "\u001b[32m";
    final String BLUE = "\u001b[34m";
    final String PINK = "\033[0;95m";
    final String VIOLET = "\033[0;35m";
    final String BLACK = "\u001b[30m";
    final String YELLOW = "\u001b[33m";
    final String WHITE = "\u001b[37m";

    //printing the grids

    //nested for loop looping through 2d array
    for(int i=0;i<ROWS;i++){
      System.out.println("");
      for(int j=0;j<COLS;j++){
        switch (guessGrid[i][j]){
          //adding a color for each element of 2d char array for UI
          case('R'):
            System.out.print(RED + guessGrid[i][j]+ RESET);
            break;
          case('G'):
            System.out.print(GREEN + guessGrid[i][j]+ RESET);
            break;
          case('B'):
            System.out.print(BLUE + guessGrid[i][j]+ RESET);
            break;
          case('V'):
            System.out.print(VIOLET + guessGrid[i][j]+ RESET);
            break;
          case('P'):
            System.out.print(PINK + guessGrid[i][j]+ RESET);
            break;
          case('K'):
            System.out.print(BLACK + guessGrid[i][j]+ RESET);
            break;
          case('Y'):
            System.out.print(YELLOW + guessGrid[i][j]+ RESET);
            break;
          case('W'):
            System.out.print(WHITE + guessGrid[i][j]+ RESET);
            break;
          default:
            System.out.print(guessGrid[i][j]);
            break;
        }// end of switch case
      }
      System.out.print("\t\t\t\t");
      for(int j=0;j<COLS;j++){
        System.out.print(comparisonGrid[i][j]);
      }
    }
  }//end of printGrids method

//method to check if user won
  public static boolean checkWin(char[]randomColours,char[]guessArray){
    final int RANDOM_LENGTH = randomColours.length;
    for(int i=0;i<RANDOM_LENGTH;i++){
      if(guessArray[i]!=randomColours[i]){
        return false;
      }
    }
    return true;
  }//end of checkWin method

}//end of class