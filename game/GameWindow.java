package game;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;



@SuppressWarnings("serial")
public class GameWindow extends JFrame implements ActionListener {
    
    private GameButton[][] buttons; // 2D array of buttons
    private GameButton resetButton; // the reset button at the bottom
    private JLabel statusLabel; // the label at the top
    
    // to store the symbol of the current player. player 1 always starts
    private static String currentPlayer = "1";
    private static String nextPlayer = "2";
    
    // a counter to keep track of how many buttons have been pressed:
    private int counterFull = 0;

    public GameWindow(int size) {
        super("Let's play a game!");
        setDefaultCloseOperation(EXIT_ON_CLOSE); // exit the program when window closes
        
        statusLabel = new JLabel("Player 1, start playing..");
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(size, size)); // make a grid, with width and height = size of the button

        buttons = new GameButton[size][]; // set the number of arrays (buttons) we will have in the buttons array
        for (int i = 0; i < size; i++) {
            buttons[i] = new GameButton[size]; // sets number of JButtons in array index = i
            for (int j = 0; j < size; j++) {
                GameButton button = new GameButton();

                button.setFont(button.getFont().deriveFont(25.0f)); // change font size
                button.setPreferredSize(new Dimension(100, 100)); // button's new size (maybe)
                // set the ActionListener to this, so it uses actionPerformed() method:
                button.addActionListener(this);

                buttonPanel.add(button);
                buttons[i][j] = button; // eg. {{JButton}, {}, {}}
            }
        }

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(statusLabel, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.CENTER);
        
        resetButton = new GameButton();
        resetButton.setText("Reset");
        // set the ActionListener to this, so it uses actionPerformed() method:
        resetButton.addActionListener(this);
        getContentPane().add(resetButton, BorderLayout.SOUTH);
        
        pack();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // to check which button was clicked:
        Object buttonPressed = e.getSource();
        
        // loop over every row:
        for (int i = 0; i < buttons.length; i++){
        	// loop over every button in the row:
            for (int j = 0; j < buttons[i].length; j++){
                // if reset button was pressed, reset every button in buttons:
                if(buttonPressed == resetButton){
                    buttons[i][j].reset();
                    // set counterFull to -1 so when it starts again, it will increase it by 1 so it will equal to 0:
                    this.counterFull = -1;
                    
        
                } else if (buttonPressed == buttons[i][j]){
                    String symbol = this.getCurrentPlayer();
                    GameButton button = buttons[i][j];
                    button.setSymbol(symbol);

                    break; // do not continue looping if we found the wanted button

                }
            }
        }

        if(checkEndingConditions()){
            // disable all buttons:
            for (int i = 0; i < buttons.length; i++){
                for (int j = 0; j < buttons[i].length; j++){
                    buttons[i][j].setEnabled(false);;
                }
            }
        }else{
            // swap currentPlayer and nextPlayer
            swapPlayers();
        }

    
    }

    private String getCurrentPlayer(){
        return GameWindow.currentPlayer;
    }
    
    private int getCounterFull() {
    	return this.counterFull;
    }

    private void swapPlayers(){
        // swap currentPlayer and nextPlayer values after a button is pressed:
        String temp = GameWindow.currentPlayer;
        GameWindow.currentPlayer = GameWindow.nextPlayer;
        GameWindow.nextPlayer = temp;

        // update status label:
        statusLabel.setText("Player " + currentPlayer);
    }

    private boolean checkEndingConditions(){
        boolean full = false;
        boolean playerWon = false;
        
        // if this method is called, means a button a pressed. so increase counterFull by 1:
        this.counterFull++;

        for (int i = 0; i < buttons.length; i++){
            for (int j = 0; j < buttons[i].length; j++){

                if (getCounterFull() == (buttons.length * buttons[i].length)){
                	full = true;
                	this.statusLabel.setText("Board is full with no winner");
                	break;

                // if a 2x2 square has been filled by the same player (text != "", must be "1" or "2"), that means this player won:
                }else {
                	// matrix which will contain the button texts to see if all texts are the same, set length will be == 1 and a player won
                	// first, check if we are in the most bottom row or if the button is the most RHS button. in this case, do not do anything as these conditions do not make the player win or not:
                	if((i < buttons.length - 1) && (j < (buttons[i].length - 1))) {
                		Set<String> matrix = new HashSet<>();
                		matrix.add(buttons[i][j].getText());
                		matrix.add(buttons[i][j+1].getText());
                		matrix.add(buttons[i+1][j].getText());
                		matrix.add(buttons[i+1][j+1].getText());
                		
                		// if the matrix size is 1, means all 2x2 square texts are the same. if matrix's only element does not == "", means a player has won:
                		if(matrix.size() == 1 && !matrix.contains("")) {
                			playerWon = true;
                			this.statusLabel.setText("Player " + this.getCurrentPlayer() + " won!");
                			break;
                		}
                	}
                	
                }
            }
        }

        return (full || playerWon);
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameWindow(4).setVisible(true);
            }
        });
    }

}
