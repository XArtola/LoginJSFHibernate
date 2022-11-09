package eredua.bean;

public class LoginBean {
	private String izena;
	private String pasahitza;

	public LoginBean() {
	}

	public String getIzena() {
		return izena;
	}

	public void setIzena(String izena) {
		this.izena = izena;
	}

	public String getPasahitza() {
		return pasahitza;
	}

	public void setPasahitza(String pasahitza) {
		this.pasahitza = pasahitza;
	}

	public String egiaztatu() {
		if (izena.equals("pirata"))
			return "error";
		else
			return "ok";
	}
}