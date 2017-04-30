package krustyKookies;

import javax.swing.*;
import javax.swing.event.*;

import krustyKookies.BookingPane.NameSelectionListener;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;

public class ProductionPane extends BasicPane {

	private static final long serialVersionUID = 1;
	private JLabel currentUser;
	private DefaultListModel<String> cookieListModel;
	private JList<String> cookieList;
	private JTextField[] fields;
	
	private static final int ID = 0;
	private static final int COOKIETYPE = 1;
	private static final int DATE = 2;
	private static final int NBR_FIELDS = 3;
	
	public ProductionPane(Database db) {
		super(db);
	}

	public JComponent createTopPanel() {
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
	
	class ActionHandler implements ActionListener {
		/**
		 * Called when the user clicks the Book ticket button. Books a ticket
		 * for the current user to the selected performance (adds a booking to
		 * the database).
		 * 
		 * @param e
		 *            The event object (not used).
		 */
		public void actionPerformed(ActionEvent e) {
			displayMessage("");
			if (cookieList.isSelectionEmpty()) {
				return;
			}
			if (!CurrentUser.instance().isLoggedIn()) {
				displayMessage("Must login first");
				return;
			}
			String cookieType = cookieList.getSelectedValue();
			/* --- insert own code here --- */
			Pallet pallet = db.producePallet(cookieType);
			
			fields[0].setText(Integer.toString(pallet.getID()));
			fields[1].setText(pallet.getCookieType());
			fields[2].setText(pallet.getLocation());
			fields[3].setText(pallet.getDate());
			fields[4].setText(pallet.getTime());
		}
	}
}
