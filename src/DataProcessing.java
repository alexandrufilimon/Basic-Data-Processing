import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;


public class DataProcessing {
	protected final Map<Integer, Inregistrari> listInfos = new TreeMap<Integer, Inregistrari>();
	protected final Map<Integer, Mail> mapMailAfiliere = new TreeMap<Integer, Mail>();
	
	public DataProcessing() {}
	
	// ++++++++++ MEMBER FUNCTIONS ++++++++++
	
	public void Load(){
		// +++++++++++++++++++++++++ Reading CSV File ++++++++++++++++++++++++
		try(final Reader in = new FileReader(getFilePath()))
		{
			final Iterable<CSVRecord> records = CSVFormat
					.RFC4180.withFirstRecordAsHeader().parse(in);
            // +++++++++++++++++++ Saving records from CSV into list +++++++++++++++++
            for(CSVRecord record : records)
            {
            	
            	final String ID = record.get("ID");
				final String eMail = record.get("eMail");
				final String Autori = record.get("Autori");
				final String Title = record.get("Title");
                Inregistrari rec = new Inregistrari(ID,eMail,Autori,Title);
                listInfos.put(Integer.parseInt(ID), rec);
            }
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1){
			e1.printStackTrace();
		}
	}

	public void getEmailDomains(){
		// +++++++++++++++++ Extracting @domain.* from emails +++++++++++++++++
		  final String spatternMail = "@[-0-9a-zA-Z.]+\\.[a-zA-Z]{2,4}";
		  final Pattern patternMail = Pattern.compile(spatternMail);
		  final Matcher matcherMail = patternMail.matcher("");
		  ////////////////////////////////////////////
		  Mail wrapper;
		  
	      for(final Map.Entry<Integer, Inregistrari> entryRecords : listInfos.entrySet()){
	    	  if(! entryRecords.getValue().geteMail().isEmpty()){
	    		  String s = entryRecords.getValue().geteMail();
	    		  matcherMail.reset(s);
	    		  while(matcherMail.find()){
	    			  String sDomain = matcherMail.group();
	    			  wrapper = new Mail(sDomain);
	    			  mapMailAfiliere.put(entryRecords.getValue().getID(), wrapper);
	    		  }
	    	  }
	      }
	}
	
	public void getCountryAbv(){
		// ++++++++++++++ Extracting country from emails +++++++++++++++
        final String spatternCountry = "[a-z]{2,4}+$";
        final Pattern patternCountry = Pattern.compile(spatternCountry);
        final Matcher matcherCountry = patternCountry.matcher("");
        ProcessTari procesTari = new ProcessTari();
        for(final Map.Entry<Integer, Mail> entryMail : mapMailAfiliere.entrySet()){
            String s = entryMail.getValue().getDomain();
            matcherCountry.reset(s);
            while(matcherCountry.find())
            {
            	String sCountry = matcherCountry.group();
            	if(procesTari.getMapCoduri().containsKey(sCountry.toUpperCase())){
            		// ADDING COUNTRY BY THEIR FULL NAME IN mapMailAfiliere
            		entryMail.getValue().setCountry(procesTari.getMapCoduri().get(sCountry.toUpperCase()));
            	}
            }   
        }
	}
	
	
	public void getUniOrg(){
		///////////////////////GET UNIVERSITIES NAMES///////////////////////
		final String spatternUni = "(?i)(?:(?<Univ>[^@;.]++(?<!\\.edu))\\.)++edu";
		final Pattern patternUni = Pattern.compile(spatternUni);
		final Matcher matcherUni = patternUni.matcher("");
		
		
		for(final Map.Entry<Integer, Mail> entryMail : mapMailAfiliere.entrySet()){
			String s = entryMail.getValue().getDomain();
			matcherUni.reset(s);
			while(matcherUni.find()){
				String sUniversity = matcherUni.group("Univ");
				entryMail.getValue().setUniversity(sUniversity);
			}
		}
		///////////////////////GET ORGANISATIONS NAMES///////////////////////
		final String spatternOrg = "(?i)(?:(?<Org>[^@;.]++(?<!\\.gov))\\.)++gov";
		final Pattern patternOrg = Pattern.compile(spatternOrg);
		final Matcher matcherOrg = patternOrg.matcher("");
		
		
		for(final Map.Entry<Integer, Mail> entryMail : mapMailAfiliere.entrySet()){
			String s = entryMail.getValue().getDomain();
			matcherOrg.reset(s);
			while(matcherOrg.find()){
				String sOrganisation = matcherOrg.group("Org");
				entryMail.getValue().setOrganisation(sOrganisation);
			}
		}
	}
	
	// +++++++++++++++++++ GETTERS ++++++++++++++++++++++++
	public Map<Integer, Inregistrari> getListInfos(){
		return this.listInfos;
	}
	
	public Map<Integer, Mail> getMailAfiliere(){
		return this.mapMailAfiliere;
	}
	
	// ++++++++++++++++++++++ JFILECHOOSER +++++++++++++++++++++++++++
	public String getFilePath(){
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Select a CSV File");
		jfc.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
		jfc.addChoosableFileFilter(filter);

		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			jfc.getSelectedFile().getPath();
		}
		return jfc.getSelectedFile().getPath();
	}
	
	// ++++++++++++ MAIN +++++++++++++++++

	public static void main(String[] args) throws IOException{
		//
		final DataProcessing mainEntry = new DataProcessing();
		mainEntry.Load();
		mainEntry.getEmailDomains();
		mainEntry.getCountryAbv();
		mainEntry.getUniOrg();
		new TablesCreator(mainEntry.getListInfos(), mainEntry.getMailAfiliere()); // Creating the GUI + Tables + Adding records
		new ExportingData(mainEntry.getListInfos(), mainEntry.getMailAfiliere()); // Exporting all datas to .csv files
		//
	}
}
