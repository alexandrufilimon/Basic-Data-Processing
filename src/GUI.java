import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import com.opencsv.CSVReader;

public class GUI {
	final JFrame frame = new JFrame("Extractie Date");
	private JTable table;
	private CSVReader CSVFileReader;
	private int contorTabeleAdaugate;
	
	public GUI(ArrayList<Object> tabele, Map<Integer, Mail> mapMailAfiliere){
		ImageIcon img = new ImageIcon("medic.png");
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		// ++++++++++++++++++++++++ TAB AUTHORS CHART +++++++++++++++++++++++
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(33, 5, 904, 817);
		ImageIcon image = new ImageIcon("Rplot.png");
		JLabel imageLabel = new JLabel(image); 
		imageLabel.setBounds(0, 0, 799, 546);
		panel.add(imageLabel);
		// +++++++++++++++++++ DISPLAYING HOW MANY AUTHORS FROM EACH COUNTRIES +++++++++++++++++
		JTextArea textArea = new JTextArea();
		textArea.setOpaque(false);
		textArea.setFont(textArea.getFont().deriveFont(15f));
		textArea.setEditable(false);
		textArea.setBackground(new Color(0, 0, 0, 0));
		textArea.setBounds(933, 5, 368, 935);
		ProcessTari procTari = new ProcessTari();
		procTari.countryCount(mapMailAfiliere);
		final StringBuilder sb = new StringBuilder();

		for(final Entry<String, Integer> entryCountry : procTari.getMapTari().entrySet()){
				if(textArea.getText().isEmpty()){
					sb.append("• " + entryCountry.getKey() + " " + entryCountry.getValue() + " author\n");
				}else{
					if(entryCountry.getValue() == 1){
						sb.append("• " + entryCountry.getKey() + " " + entryCountry.getValue() + " author\n");
					}else{
						sb.append("• " + entryCountry.getKey() + " " + entryCountry.getValue() + " authors\n");
					}	
				}
				textArea.setText(sb.toString());
		}
		panel.add(textArea);
		// +++++ ADDING OPEN BUTTON +++++++++++++++
		JPanel panou = new JPanel();
		panou.setLayout(null);
		JButton butonOpen = new JButton();
		butonOpen.setBounds(1256, 0, 126, 25);
		butonOpen.setText("Open file");
		butonOpen.addActionListener(new ActionListener()
		{	  
			public void actionPerformed(final ActionEvent e){
				try {
					openFile();
					tabbedPane.add("new Table", GetScrollPane(table));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		panou.add(butonOpen);
		// +++++ ADDING CLEAR BUTTON +++++++++++++++
		JButton butonClear = new JButton();
		butonClear.setBounds(1145, 0, 97, 25);
		butonClear.setText("Clear");
		butonClear.addActionListener(new ActionListener()
		{	  
			public void actionPerformed(final ActionEvent e){
				// +++++++ CLEARING ALL NEW ADDED TABS
				tabbedPane.getTabCount();
				while(contorTabeleAdaugate !=0){
					int i = tabbedPane.getTabCount();
					tabbedPane.remove(i-1);
					contorTabeleAdaugate--;
				}
			}
		});
		panou.add(butonClear);
		// ++++++++++++++++++++++++ TABLE 1 +++++++++++++++++++++++++
		JScrollPane tablou1 = GetScrollPane((JTable)tabele.get(0));
		tablou1.setBounds(0, 31, 1382, 909);
		panou.add(tablou1);
		// ++++++++++++++++++++++++ ADDING COMPONENTS TO FRAME & TABBEDPANE +++++++++++++++++++++++++++++++
		frame.setSize(new Dimension(1400,1000));
		frame.setIconImage(img.getImage());
		//
		tabbedPane.add("Datas", panou);
		tabbedPane.add("Authors&Countries", GetScrollPane((JTable)tabele.get(1)));
		tabbedPane.add("Authors&Org/Uni", GetScrollPane((JTable)tabele.get(2)));
		tabbedPane.add("Plural words to singular", GetScrollPane((JTable)tabele.get(3)));
		tabbedPane.add("Authors Chart", panel);
		//
		frame.add(tabbedPane);
		frame.setVisible(true);
	}
	
	private JScrollPane GetScrollPane(final JTable table) {
		final JScrollPane js = new JScrollPane(table);
		table.setAutoCreateRowSorter(true);
		table.setShowGrid(true);
		table.setGridColor(Color.BLUE);
		js.setViewportView(table);

		return js;

	}
	
	private void openFile() throws IOException{
		String filePath = new DataProcessing().getFilePath();
		CSVFileReader = new CSVReader(new FileReader(filePath));
		List<String[]> myEntries = CSVFileReader.readAll();
		Object[] columnnames = (String[]) myEntries.get(0); // GETTING HEADER RECORDS
		
		DefaultTableModel tableModel = new DefaultTableModel(columnnames, myEntries.size()-1); 
		
		new TablesCreator().addRecordsNewTable(tableModel, myEntries); // ADDING RECORDS TO TABLE
		
		table = new JTable(tableModel);
		contorTabeleAdaugate++; //  USEFUL FOR CLEARING NEW ADDED TABS
		
	}
	

}
