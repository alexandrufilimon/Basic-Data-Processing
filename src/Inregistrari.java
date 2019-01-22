
public class Inregistrari {
	protected int ID;
	protected String eMail;
	protected String autor;
	protected String title;
	
	// ++++++++++++++++++++++ CONSTRUCTORS +++++++++++++++++++++++
	public Inregistrari(){}
	
	public Inregistrari(String ID, String eMail, String autor, String title){
		this.ID = Integer.parseInt(ID);
		this.eMail = eMail;
		this.autor = autor;
		this.title = title;
	}
	
	// +++++++++++++++++++++ GETTERS +++++++++++++++++++++++
	public int getID(){
		return this.ID;
	}
	public String geteMail(){
		return this.eMail;
	}
	public String getAutor(){
		return this.autor;
	}
	public String getTitle(){
		return this.title;
	}
}
