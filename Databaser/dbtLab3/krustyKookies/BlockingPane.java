package krustyKookies;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import krustyKookies.BookingPane.ActionHandler;
import krustyKookies.ProductionPane.CookieSelectionListener;

public class BlockingPane extends BasicPane {
	private static final long serialVersionUID = 1;
	private JLabel currentUser;
	private DefaultListModel<String> cookieListModel;
	private JList<String> cookieList;
	private DefaultListModel<String> idListModel;
	private JList<String> idList;
	private JTextField[] fields;
	
	private static final int BDATE = 0;
	private static final int EDATE = 1;
	private static final int NBR_FIELDS = 2;
	//private static final int ID = 0;
	//private static final int COOKIETYPE = 1;
	//private static final int LOCATION = 2;
	//private static final int DATE = 3;
	//private static final int TIME = 4;
	//private static final int NBR_FIELDS = 5;
	
	public BlockingPane(Database db) {
		super(db);
	}
	
	public JComponent createTopPanel(){
		String[] texts = new String[NBR_FIELDS];
		texts[BDATE] = "Starting date";
		texts[EDATE] = "Ending date";
		
		fields = new JTextField[NBR_FIELDS];
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new JTextField(20);
			fields[i].setEditable(true);
		}
		
		JPanel input = new InputPanel(texts, fields);
		
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		p1.add(new JLabel("Current user: "));
		currentUser = new JLabel("");
		p1.add(currentUser);

		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(p1);
		p.add(input);
		return p;
	}
	
	public JComponent createLeftPanel(){
		cookieListModel = new DefaultListModel<String>();

		cookieList = new JList<String>();
		cookieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cookieList.setPrototypeCellValue("123456789012");
		cookieList.addListSelectionListener(new CookieSelectionListener());
		JScrollPane p1 = new JScrollPane(cookieList);

		JPanel p = new JPanel();
		p.add(p1);
		return p;
	}
	
	public JComponent createBottomPanel(){
		JButton[] buttons = new JButton[1];
		buttons[0] = new JButton("Block pallets");
		return new ButtonAndMessagePanel(buttons, messageLabel, new ActionHandler());
	}
	
	class CookieSelectionListener implements ListSelectionListener
	{

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			// TODO Auto-generated method stub
			if(cookieList.isSelectionEmpty()){
				return;
			}
		}
	}
	
	
	class ActionHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			displayMessage("");
			if (cookieList.isSelectionEmpty()) {
				return;
			}
			if (!CurrentUser.instance().isLoggedIn()) {
				displayMessage("Must login first");
				return;
			}
			String[] bDate = fields[BDATE].toString().split(" ");
			String[] eDate = fields[EDATE].toString().split(" ");
			if(bDate == null || bDate.length != 3 || eDate == null || eDate.length !=3){
				return;
			}
			Date begDate = new GregorianCalendar(Integer.parseInt(bDate[0])-1900, Integer.parseInt(bDate[1]),
					Integer.parseInt(bDate[2])).getTime();
			Date endDate = new GregorianCalendar(Integer.parseInt(eDate[0])-1900, Integer.parseInt(eDate[1]),
					Integer.parseInt(eDate[2])).getTime();
			java.sql.Date startDate = new java.sql.Date(begDate.getTime());
			java.sql.Date endingDate = new java.sql.Date(endDate.getTime());
			String cookieType = cookieList.getSelectedValue();
			/* --- insert own code here --- */
			db.blockPallets(cookieType, startDate, endingDate);
		}
	}
}
