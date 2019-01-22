import java.util.Map;
import java.util.TreeMap;


public class DictionaryFactory {
	final TreeMap<String, Integer> mapCuvinte = new TreeMap<>();
	
	public DictionaryFactory(final Map <Integer, Inregistrari> vInregistrari){
		
		for(final Map.Entry<Integer, Inregistrari> entryRecords : vInregistrari.entrySet()) {
			final String sTitlu = entryRecords.getValue().getTitle();
			if(sTitlu == null) {
				continue;
			}
			
			// impartim pe cuvinte
			final String [] arrCuvinte = sTitlu.split("[^\\p{L}0-9]");
			
			for(final String sCuvantOrig : arrCuvinte) {
				if(sCuvantOrig == null || sCuvantOrig.isEmpty()) {
					continue;
				}
				

				final String sCuvant = sCuvantOrig.toLowerCase();
				
				// verificam daca cuvantul exista deja in Dictionar
				final Integer count = mapCuvinte.get(sCuvant);
				if(count == null) {
					mapCuvinte.put(sCuvant, 1);
				} else {
					mapCuvinte.put(sCuvant, count + 1);
				}
			}
		}
	}
}
