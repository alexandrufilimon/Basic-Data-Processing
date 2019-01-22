import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class CustomTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomTable(final DefaultTableModel model) {
		super(model);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public Component prepareRenderer(
			TableCellRenderer renderer, int row, int column)
	{
		final Component cpRender = super.prepareRenderer(renderer, row, column);

		//  Alternate row color

		if ( ! isRowSelected(row))
			cpRender.setBackground(row % 2 == 0 ? getBackground() : new Color(220,220,220));

		return cpRender;
	}
}