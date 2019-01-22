

public class Mail {
	protected String domain = new String(); // (at)domain(dot)***
	protected String university = new String();
	protected String organisation = new String();
	protected String country = new String();
	
	// ++++++++++++++++ CONSTRUCTORS ++++++++++++++++++++++++
	public Mail(String domain){
		this.domain = domain;
	}
	
	public Mail(String domain, String university, String organisation, String country){
		this.domain = domain;
		this.university = university;
		this.organisation = organisation;
		this.country = country;
	}

	
	// ++++++++++++++++++++ SETTERS & GETTERS ++++++++++++++++
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
