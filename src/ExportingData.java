import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;


public class ExportingData {
	public ExportingData(final Map<Integer, Inregistrari> listData, final Map<Integer, Mail> mapMailAfiliere){
		//+++++++++++++++++++++++++++++ AUTHORS_COUNTRIES.CSV STRING BUILDER +++++++++++++++++++++++++++++
		PrintWriter pw = null;
		try {
		   pw = new PrintWriter(new File("Authors_Countries.csv"));
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		}
        StringBuilder sb = new StringBuilder();
        sb.append("ID");
        sb.append(',');
        sb.append("Name");
        sb.append(',');
        sb.append("Country");
        sb.append(',');
        sb.append('\n');
        
      //+++++++++++++++++++++++++++++ AUTHORS_UNIVERSITY_ORGANISATION.CSV STRING BUILDER +++++++++++++++++++++++++++++
        PrintWriter pw1 = null;
		try {
		   pw1 = new PrintWriter(new File("Authors_University_Organisation.csv"));
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		}
        StringBuilder sb1 = new StringBuilder();
        sb1.append("ID");
        sb1.append(',');
        sb1.append("Name");
        sb1.append(',');
        sb1.append("University");
        sb1.append(',');
        sb1.append("Organisation");
        sb1.append(',');
        sb1.append('\n');
        
      //+++++++++++++++++++++++++++++ EXPORTING PROCESS FOR BOTH .CSV FILES +++++++++++++++++++++++++++++
        	///////////////////////////////////////////////////////////////////////////////////////
        for(final Map.Entry<Integer, Mail> entryMail : mapMailAfiliere.entrySet()){
        	//+++++++++++++++++++++++++++++ EXPORTING AUTHORS COUNTRIES +++++++++++++++++++++++++++++
        	if(!entryMail.getValue().getCountry().isEmpty() ) {
				sb.append(entryMail.getKey());
				sb.append(',');
				sb.append(listData.get(entryMail.getKey()).getAutor().replace(",", ";"));
				sb.append(',');
				sb.append(entryMail.getValue().getCountry());
				sb.append('\n');
        	}
        	//+++++++++++++++++++++++++++++ EXPORTING AUTHORS UNIVERSITIES +++++++++++++++++++++++++++++
        	if(!entryMail.getValue().getUniversity().isEmpty()){
				sb1.append(entryMail.getKey()); // ID
				sb1.append(',');
				sb1.append(listData.get(entryMail.getKey()).getAutor().replace(",", ";")); // NAME
				sb1.append(',');
				sb1.append(entryMail.getValue().getUniversity().toUpperCase()); // UNIVERSITY
				sb1.append(',');
				sb1.append('-');
				sb1.append(',');
				sb1.append('\n');
        	}
        	//+++++++++++++++++++++++++++++ EXPORTING AUTHORS ORGANISATIONS +++++++++++++++++++++++++++++
        	if(!entryMail.getValue().getOrganisation().isEmpty()){
				sb1.append(entryMail.getKey());
				sb1.append(',');
				sb1.append(listData.get(entryMail.getKey()).getAutor().replace(",", ";"));
				sb1.append(',');
				sb1.append('-');
				sb1.append(',');
				sb1.append(entryMail.getValue().getOrganisation().toUpperCase());
				sb1.append(',');
				sb1.append('\n');
			}
        } // END FOR

        pw.write(sb.toString());
        pw.close();
        System.out.println("Exporting authors and countries successful !");
        
        pw1.write(sb1.toString());
        pw1.close();
        System.out.println("Exporting authors and universities/organisations successful !");
        
	}

}
