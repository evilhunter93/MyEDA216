package krustyKookies;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

public class BlockingPane extends BasicPane {
	private static final long serialVersionUID = 1;
	private JLabel currentUser;
	private DefaultListModel<String> cookieListModel;
	private JList<String> cookieList;
	private JTextField[] fields;
	
	private static final int ID = 0;
	private static final int COOKIETYPE = 1;
	private static final int LOCATION = 2;
	private static final int DATE = 3;
	private static final int TIME = 4;
	private static final int NBR_FIELDS = 5;
	
	public BlockingPane(Database db) {
		super(db);
	}
	
	public JComponent createTopPanel(){
		
	}
}
