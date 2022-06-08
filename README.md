# Othello
##### Othello game application based on java.

# About the Project 
  ##### Application of the game Othello, allowing to play against the computer or against a friend. (based on AI algorithm)
 
# Principle of the game 
  ###### Othello is a game played on a board of 64 squares (8x8). The two players, Black and White, have 64 two-color pawns, black on one side and white on the other (a pawn is black if its black side is visible and vice versa).
  ###### Initially four pawns, two black and two white, are placed in the middle of the board (pawns of the same color diagonally) and each player has 30 pawns in front of him (the player in black starts the game).
  ###### Players take turns. The player with the hand must place a pawn of his color on an empty square on the board and frame at least one opposing pawn between the pawn he places and a pawn of his color (the framed pawns are then turned over).
  ###### Le but est d’avoir plus de pions de sa couleur que l'adversaire à la fin de la partie qui s'achève quand les deux joueurs ne peuvent plus faire un coup légal.
  
# Features

  ###### Storage in memory and display of a given state.
  ###### Cancellation/repetition of moves and possibility of replaying the game from the beginning, a move is played after a given number of seconds.
  ###### Choice of search strategy parameters: algorithm, depth and evaluation function.
  ###### Allow a Human-Machine or Human-Human game, in both cases the player can ask for help (move to play).
  ###### Backup and restoration of a completed game or not.
  ###### The application must have a user-friendly graphical interface and take into consideration multiple users.
  
# Tools
  ###### IDE: Eclipse 2020 
  ###### Language: Java 
  ###### JDK: 1.8 
  ###### Libraries: Swing, AWT
  
  # Interfaces of the application
  
  The first window that appears when you launch the application is the one concerning identification/registration:
  <p align="center">
    <img width="400" height="350" src="https://user-images.githubusercontent.com/67929106/172575978-0d911802-402c-479c-90e1-f39990dc4afa.png">
  </p>
  
  Once the two fields are filled the two buttons register and identify becomes activated:
  <p align="center">
    <img width="400" height="350" src="https://user-images.githubusercontent.com/67929106/172576116-cbbd82f9-fee1-4651-b6f0-69090f0a6c07.png">
  </p>
  
  First case: registration with a new user :
  <p align="center">
    <img width="400" height="350" src="https://user-images.githubusercontent.com/67929106/172576136-e0580628-8ca9-463d-bdc1-23df3a3b8173.png">
  </p>
  
  Second case: registration with an existing user :
  <p align="center">
    <img width="400" height="350" src="https://user-images.githubusercontent.com/67929106/172576166-e59f4181-58ce-4dc4-b363-3c0f7d680305.png">
  </p>
  
  Third case: registration with an existing user :
  <p align="center">
    <img width="400" height="350" src="https://user-images.githubusercontent.com/67929106/172576191-8132ab7e-bb34-4396-955c-e2fc6fd29cfb.png">
  </p>
  
  If the user identifies himself with an existing account, he is redirected to the game preparation window :
  <p align="center">
    <img width="400" height="350" src="https://user-images.githubusercontent.com/67929106/172576208-56561553-0309-4747-81c8-6dcc13135ab9.png">
  </p>
  
  When the user identifies himself after having already started a game, the latter is saved so that he can continue it the next time, otherwise he can play a new game :
  <p align="center">
    <img width="400" height="350" src="https://user-images.githubusercontent.com/67929106/172576231-65b4e63d-fcf2-458a-8552-35afceefc41b.png">
  </p>
  
  This is the game interface that is displayed after the validation of the chosen parameters, we notice that the Cancel button is deactivated since no move has yet been made :
  <p align="center">
    <img width="400" height="350" src="https://user-images.githubusercontent.com/67929106/172576259-65a40857-c796-4a1d-bf0b-a311349c3207.png">
  </p>
  
  ##### The yellow circles indicate the possible moves that you are allowed to make
  
  By clicking on the Indication button the program displays a position
  <p align="center">
    <img width="400" height="350" src="https://user-images.githubusercontent.com/67929106/172576284-95bb41c7-3c7f-4926-83ef-5e170e470a68.png">
  </p>
  
  ##### the Cancel button "Annuler" is now enabled
  
  In the event that someone wins, it is displayed via a message, in our case it is the human who has won :
  <p align="center">
    <img width="400" height="350" src="https://user-images.githubusercontent.com/67929106/172576307-7e9576db-fbd4-4b80-a5f8-f64e896f87ba.png">
  </p>
  
  # N.B 
   ###### Check the principle of the Alpha-Beta algorithm, it will helps to understand the logical path in the code.
   
 # Licence
    
   ###### Distributed under the MIT Licence.
   
 Thanks for Reading ;) ! 
