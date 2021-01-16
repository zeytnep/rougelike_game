package rogue;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.TerminalPosition;


import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;

import javax.swing.*;
import javax.swing.JPanel;
import java.awt.Container;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.BorderLayout;


public class TextUI extends JFrame {

    private TerminalScreen screen;
    private final char startCol = 1;
    private final char msgRow = 1;
    private final char roomRow = 3;
    private final int twentyFive = 25;
    private final int ten = 10;
    private final int four = 4;
    private final int setFixedCellHeight = 35;
    private final int setFixedCellWidth = 95;

    private SwingTerminal terminal;

    public static final int WIDTH = 750;
    public static final int HEIGHT = 500;

    private Container contentPane;
    private JPanel itemListPanel;
    private JPanel thePanel;
    private JList<String> inventoryList = new JList<>();
    private DefaultListModel<String> inventoryModel = new DefaultListModel<>();
    private Player player;

    private HashMap<Item, String> sack;
    private ArrayList<Item> sackList;
    private ArrayList<Item> itemsInGui = new ArrayList<>();
    private int sackSize;
    private int sackListSize;

    private String message = " ";
    private String[] options = {"Eat", "Toss", "Wear"};

/**
Constructor for TextUI class.  Creates the screens, sets
cursor to top left corner and does nothing else.
**/
    public TextUI(Rogue theGame) {
        super();
        contentPane = getContentPane();
        setDefaults(getContentPane());
        player = theGame.getPlayer();
        setTerminal();
        setUpComponents();
        pack();
        start();
    }


    /**
    Redraws the whole screen including the room and the message.
    @param message the message to be displayed at the top of the room
    @param room the room map to be drawn
    **/

    public void draw(String message, String room, Rogue game) {
        setPlayerAndInventory(game);
        try {
            screen.clear();
            screen.refresh();
            setMessage(message);
            putString(room, startCol, roomRow);
            screen.refresh();
        } catch (IOException e) {

        }
    }

    private void setPlayerAndInventory(Rogue game) {
        player = game.getPlayer();

        sack = player.getPlayersInventory(); //i have a hasmap of the inventory now!!:)
        sackList = player.getArrayListInventory(); //i have an  arraylist of inventory

        sackSize = sack.size();
        sackListSize = sackList.size();
    }

    private void setDefaults(Container contentPane) {
        setTitle("Rogue Game");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane.setLayout(new BorderLayout());
    }

    private void setUpComponents() {
        setUpPanels();
        menuBar();
    }


    private void setUpPanels() {
        thePanel = new JPanel();
        itemListPanel = new JPanel();
        setUpLabelPanel();
        setUpItemsPanel();
    }

    private void setTerminal() {
        JPanel terminalPanel = new JPanel();
        terminal = new SwingTerminal(); //add terminal into terminalpanel
        terminal.setCursorPosition(four, four);
        terminal.setCursorVisible​(true);
        terminal.setSize(ten, ten);
        terminalPanel.setSize(ten, ten);

        terminalPanel.add(terminal);
        contentPane.add(terminalPanel);
    }

    private void setUpLabelPanel() {
        Border prettyLine = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        thePanel.setBorder(prettyLine);
        JLabel exampleLabel = new JLabel("Name");
        thePanel.add(exampleLabel);
        JTextField dataEntry = new JTextField("Your name goes here", twentyFive);
        thePanel.add(dataEntry);
        JButton clickMe = new JButton("Enter");
        thePanel.add(clickMe);

        contentPane.add(thePanel, BorderLayout.SOUTH);
    }

    private void setUpItemsPanel() {
        Border prettyLine = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        inventoryList.setModel(inventoryModel);
        inventoryList.setFixedCellHeight(setFixedCellHeight);
        inventoryList.setFixedCellWidth(setFixedCellWidth);

        itemListPanel.setBorder(prettyLine);
        itemListPanel.add(inventoryList);

        itemListPanel.setBackground(Color.blue);
        itemListPanel.setOpaque(true);
        contentPane.add(itemListPanel, BorderLayout.EAST);

        inventoryList.getSelectionModel().addListSelectionListener(e -> getOptions());
    }

    private void getOptions() {
        String selectedItem = (String) inventoryList.getSelectedValue();
        String m = " Item name: " + selectedItem;
        String message2 = "Options for " + selectedItem;
        int chosenOption;
        chosenOption = JOptionPane.showOptionDialog(itemListPanel, m, message2, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }

    private void addToInventory() {

       if (sackList.isEmpty()) {
           return;
       } else {
            for (int i = 0; i < sackListSize; i++) {
                if (!itemsInGui.contains(sackList.get(i))) {
                    itemsInGui.add(sackList.get(i));
                    inventoryModel.addElement(sackList.get(i).getName());
                }
            }
       }
    }


    private void menuBar() {
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
        // create the File menu
        JMenu fileMenu = new JMenu("File");
        menubar.add(fileMenu);
        // create the Open Item
        JMenuItem openItem = new JMenuItem("Open");
        fileMenu.add(openItem);
        // create the Quit Item
        JMenuItem quitItem = new JMenuItem("Save Current Game");
        fileMenu.add(quitItem);
    }

    private void start() {
        try {
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(TerminalPosition.TOP_LEFT_CORNER);
            screen.startScreen();
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


/**
Prints a string to the screen starting at the indicated column and row.
@param toDisplay the string to be printed
@param column the column in which to start the display
@param row the row in which to start the display
**/
        public void putString(String toDisplay, int column, int row) {
            Terminal t = screen.getTerminal();
            try {
                t.setCursorPosition(column, row);
            for (char ch: toDisplay.toCharArray()) {
                t.putCharacter(ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

/**
Changes the message at the top of the screen for the user.
@param msg the message to be displayed
**/
            public void setMessage(String msg) {
                putString("                                                ", 1, 1);
                putString(msg, startCol, msgRow);
            }




/**
Obtains input from the user and returns it as a char.  Converts arrow
keys to the equivalent movement keys in rogue.
@return the ascii value of the key pressed by the user
**/
        public char getInput() {
            KeyStroke keyStroke = null;
            char returnChar;
            while (keyStroke == null) {
            try {
                keyStroke = screen.pollInput();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
         if (keyStroke.getKeyType() == KeyType.ArrowDown) {
            returnChar = Rogue.DOWN;  //constant defined in rogue
        } else if (keyStroke.getKeyType() == KeyType.ArrowUp) {
            returnChar = Rogue.UP;
        } else if (keyStroke.getKeyType() == KeyType.ArrowLeft) {
            returnChar = Rogue.LEFT;
        } else if (keyStroke.getKeyType() == KeyType.ArrowRight) {
            returnChar = Rogue.RIGHT;
        } else {
            returnChar = keyStroke.getCharacter();
        }
        return returnChar;
    }

/**
the main method.
@param args command line arguments are unused at this point.
**/

public static void main(String[] args) {
    char userInput = 'h';
    String message;
    String configurationFileLocation = "fileLocations.json";
    //Parse the json files
    RogueParser parser = new RogueParser(configurationFileLocation);
    // allocate memory for the game and set it up
    Rogue theGame = new Rogue(parser);
    //set up the initial game display
    Player thePlayer = new Player("Zeynep");
    //allocate memory for the GUI
    theGame.setPlayer(thePlayer);

    String name = thePlayer.getName();
    message = "Welcome to my Rogue game " + name;
    theGame.getNextDisplay();

    TextUI theGameUI = new TextUI(theGame);
    theGameUI.setVisible(true);

    theGameUI.draw(message, theGame.getNextDisplay(), theGame);

    while (userInput != 'q') {
    //get input from the user
    userInput = theGameUI.getInput();

    //ask the game if the user can move there
    try {
        message = theGame.makeMove(userInput);
        theGameUI.draw(message, theGame.getNextDisplay(), theGame);
        theGameUI.addToInventory();
    } catch (InvalidMoveException badMove) {
        message = badMove.getMessage();
        theGameUI.setMessage(message);
    }
    }

    // do something here to say goodbye to the user


}
}
