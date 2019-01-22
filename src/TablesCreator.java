import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;


public class TablesCreator {
	
	public TablesCreator(){}
	
	public TablesCreator(Map<Integer, Inregistrari> listData, Map<Integer, Mail> mapMailAfiliere) throws IOException{
		Table tablouri = new Table(
				new DefaultTableModel(new String [] {"ID","eMail","Autor","Titlu"}, 0), // Tab1
				new DefaultTableModel(new String [] {"ID","Autor","Tara"}, 0), // Tab2
				new DefaultTableModel(new String [] {"ID","Autor","Universitate","Organizatie"}, 0), // Tab3
				new DefaultTableModel(new String [] {"Cuvinte singular","Cuvinte plural"}, 0)); // Tab4
		// ++++++++++++++++++++++++++++++++++ ENTIRE .CSV FILE RECORDS TABLE ++++++++++++++++++++++++++++++++++
		for (final Map.Entry<Integer, Inregistrari> entryRecords : listData.entrySet()){
		   final int ID = entryRecords.getKey();
		   final String eMail = entryRecords.getValue().geteMail();
		   final String Autor = entryRecords.getValue().getAutor();
		   final String Titlu =	entryRecords.getValue().getTitle();

		   Object[] data = {ID, eMail, Autor, Titlu};
		   ((DefaultTableModel) tablouri.tab1.getModel()).addRow(data);
		}
		
        for(final Map.Entry<Integer, Mail> entryMail : mapMailAfiliere.entrySet()){
        	// ++++++++++++++++++++++++++++++++++ AUTHORS&COUNTRIES TABLE ++++++++++++++++++++++++++++++++++
        	if(!entryMail.getValue().getCountry().isEmpty()){
        		final int ID = entryMail.getKey();
        		final String Autor = listData.get(ID).getAutor().replace(",", ";");
        		final String Tara = entryMail.getValue().getCountry();

        		Object[] data = {ID,Autor,Tara};
        		((DefaultTableModel) tablouri.tab2.getModel()).addRow(data);
        	}
        	
        	// ++++++++++++++++++++++++++++++++++ AUTHORS,UNIVERSITIES&ORGANISATIONS TABLE ++++++++++++++++++++++++++++++++++
        	else if(!entryMail.getValue().getUniversity().isEmpty()){
        		final int ID = entryMail.getKey();
            	final String Autor = listData.get(ID).getAutor().replace(",", ";");
            	final String Universitate = entryMail.getValue().getUniversity().toUpperCase();
            				
            	Object[] data = {ID,Autor,Universitate,"-"};
            	((DefaultTableModel) tablouri.tab3.getModel()).addRow(data);
        	}
        	
        	else if(!entryMail.getValue().getOrganisation().isEmpty()){
        		final int ID = entryMail.getKey();
            	final String Autor = listData.get(ID).getAutor().replace(",", ";");
            	final String Organizatie = entryMail.getValue().getOrganisation().toUpperCase();
            				
            	Object[] data = {ID,Autor,"-",Organizatie};
            	((DefaultTableModel) tablouri.tab3.getModel()).addRow(data);
        	}
        } // END FOR

     	// ++++++++++++++++++++++++++++++++++ PLURAL TO SINGURAL WORDS TABLE ++++++++++++++++++++++++++++++++++
        Stemming words = new Stemming(listData);
        
        for(final Map.Entry<List<String>, String> entryWord : words.cuvinteSgPl.entrySet()){
    	   	Object[] data = {entryWord.getKey(), entryWord.getValue()};
        	((DefaultTableModel) tablouri.tab4.getModel()).addRow(data);
        }
		
        //
        ArrayList<Object> tabele = new ArrayList<Object>();
        tabele.add(tablouri.tab1); tabele.add(tablouri.tab2); tabele.add(tablouri.tab3); tabele.add(tablouri.tab4);
        
		new GUI(tabele, mapMailAfiliere); // CREATING GRAPHIC USER INTERFACE
		//
	}
	

	// +++++++++++++++++++++++ METHOD USED TO ADD RECORDS WHEN OPENED NEW FILE +++++++++++++++++++++++
	public void addRecordsNewTable(DefaultTableModel tableModel, List<String[]> myEntries){
		int rowcount = tableModel.getRowCount();
		
		for (int x=0; x<rowcount+1; x++)
		{
		    int columnnumber = 0;
		    if (x>0){
		        for (String thiscellvalue : (String[])myEntries.get(x))
		        {
		            tableModel.setValueAt(thiscellvalue, x-1, columnnumber);
		            columnnumber++;
		        }
		    }
		}
	}

}
