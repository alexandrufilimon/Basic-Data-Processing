import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class ProcessTari {
	protected final Map<String, String> mapCoduri = new TreeMap<String, String>(); // Treemap<Abreviere tara, Nume complet tara>
	protected final Map<String, Integer> mapTari = new TreeMap<String, Integer>(); // Treemap<Tara, numar autori din tara>
	
	public ProcessTari(){
		// ++++++++ ADDING ALL EXISTING COUNTRIES IN mapCoduri ++++++
        String[] locales = Locale.getISOCountries();
        for (String country : locales) {
            Locale locale = new Locale("", country);
            mapCoduri.put(locale.getCountry().toUpperCase(), locale.getDisplayCountry());
        }
	}
	
	public Map<String, String> getMapCoduri(){
		return this.mapCoduri;
	}
	
	public Map<String, Integer> getMapTari(){
		return this.mapTari;
	}
	
	public void countryCount(final Map<Integer, Mail> mapMailAfiliere){
		// ++++++++++++++++++++++++ COUNTING AUTHORS FROM EACH COUNTRY +++++++++++++++++++++++
		for(final Map.Entry<Integer, Mail> entryCountry : mapMailAfiliere.entrySet()){
			if(!entryCountry.getValue().getCountry().isEmpty()){
				final Integer iCount = mapTari.get(entryCountry.getValue().getCountry());
				if(iCount == null) {
					mapTari.put(entryCountry.getValue().getCountry(), 1);
					} else {
					mapTari.replace(entryCountry.getValue().getCountry(), iCount + 1);
					}
			}
		}
	}
}
