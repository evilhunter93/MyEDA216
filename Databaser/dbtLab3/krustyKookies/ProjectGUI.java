package krustyKookies;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

/**
 * MovieGUI is the user interface to the movie database. It sets up the main
 * window and connects to the database.
 */
public class ProjectGUI {

    /**
     * db is the database object
     */
    private Database db;

    /**
     * tabbedPane is the contents of the window. It consists of two panes, User
     * login and Book tickets.
     */
    private JTabbedPane tabbedPane;

    /**
     * Create a GUI object and connect to the database.
     * 
     * @param db
     *            The database.
     */
    public ProjectGUI(Database db) {
        this.db = db;

        JFrame frame = new JFrame("MovieBooking");
        tabbedPane = new JTabbedPane();

        UserLoginPane userLoginPane = new UserLoginPane(db);
        tabbedPane.addTab("User login", null, userLoginPane,
                          "Log in as a new user");

        ProductionPane productionPane = new ProductionPane(db);
        tabbedPane.addTab("Produce pallet", null, productionPane, "Produce a pallet");
        
        PalletSearchPane searchPane = new PalletSearchPane(db);
        tabbedPane.addTab("Search for pallet", null, searchPane, "Search for pallet");
        
        BlockingPane blockingPane = new BlockingPane(db);
        tabbedPane.addTab("Block pallet", null, blockingPane, "Block a pallet");

        tabbedPane.setSelectedIndex(0);

        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        tabbedPane.addChangeListener(new ChangeHandler());
        frame.addWindowListener(new WindowHandler());

        frame.setSize(500, 400);
        frame.setVisible(true);

        userLoginPane.displayMessage("Connecting to database ...");
                
        /* --- change code here --- */
        /* --- change xxx to the name of the file with your database --- */
        if (db.openConnection("KrustyKookies.db")) {
            userLoginPane.displayMessage("Connected to database");
        } else {
            userLoginPane.displayMessage("Could not connect to database");
        }
    }

    /**
     * ChangeHandler is a listener class, called when the user switches panes.
     */
    class ChangeHandler implements ChangeListener {
        /**
         * Called when the user switches panes. The entry actions of the new
         * pane are performed.
         * 
         * @param e
         *            The change event (not used).
         */
        public void stateChanged(ChangeEvent e) {
            BasicPane selectedPane = (BasicPane) tabbedPane
                .getSelectedComponent();
            selectedPane.entryActions();
        }
    }

    /**
     * WindowHandler is a listener class, called when the user exits the
     * application.
     */
    class WindowHandler extends WindowAdapter {
        /**
         * Called when the user exits the application. Closes the connection to
         * the database.
         * 
         * @param e
         *            The window event (not used).
         */
        public void windowClosing(WindowEvent e) {
            db.closeConnection();
            System.exit(0);
        }
    }
}