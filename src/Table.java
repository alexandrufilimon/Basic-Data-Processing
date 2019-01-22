import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Table {
	public JTable tab1, tab2, tab3, tab4;

	
	public Table(DefaultTableModel tab1, DefaultTableModel tab2, DefaultTableModel tab3, DefaultTableModel tab4){
		this.tab1 = new CustomTable(tab1);
		this.tab2 = new CustomTable(tab2);
		this.tab3 = new CustomTable(tab3);
		this.tab4 = new CustomTable(tab4);
	}
	public Table(DefaultTableModel tab1){
		this.tab1 = new JTable(tab1);
	}
}