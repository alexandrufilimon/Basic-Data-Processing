import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.WordnetStemmer;

public class Stemming {
	protected final Map<List<String>, String> cuvinteSgPl = new HashMap<List<String>, String>(); // <cuvantBaza, cuvantInitial>
	protected String dictPath;
	
	public Stemming(final Map<Integer, Inregistrari> listData) throws IOException{
		openDictionary(); // LOCATING DICTIONARY
		
		final Dictionary dict = new Dictionary(new File(dictPath));
        final DictionaryFactory dictFactory = new DictionaryFactory(listData);
		dict.open();
		WordnetStemmer stemmer = new WordnetStemmer(dict);
		
		for(final Entry<String, Integer> entry : dictFactory.mapCuvinte.entrySet()) {
			final List<String> listCuvantBaza = stemmer.findStems(entry.getKey(), POS.NOUN);;
			if( ! listCuvantBaza.isEmpty() && entry.getKey().matches("^\\p{L}++$") 
					&& entry.getKey().length() > 2) {
				final String sCuvant = entry.getKey();
				cuvinteSgPl.put(listCuvantBaza, sCuvant);
			}
		}
		
		dict.close();
	}
	
	public void openDictionary(){
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Select Dictionary");
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.setAcceptAllFileFilterUsed(false);

		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			jfc.getSelectedFile().getPath();
		}
		this.dictPath = jfc.getSelectedFile().getPath();
	}

}
