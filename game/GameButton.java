package game;

import javax.swing.JButton;
import java.awt.Color;

@SuppressWarnings("serial")
public class GameButton extends JButton{

    private String symbol;
    
    
    // Symbol is either 1 or 2
    public void setSymbol(String symbol){
        // set the variable symbol for this button to the given symbol:
        this.symbol = symbol;
        setText(symbol);
        setBackground(Color.RED);
        
        if(symbol.equals("1")){
            setBackground(Color.RED);

        }else if (symbol.equals("2")){
            setBackground(Color.BLUE);
        }
        setEnabled(false);
    }

    public String getSymbol(){
        return this.symbol;
    }

    // reset the button to its original state:
    public void reset(){
        setText("");
        symbol = null;
        setBackground(null);
        setEnabled(true);
    }
}