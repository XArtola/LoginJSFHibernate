package eredua.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.SelectEvent;

public class LoginBean {
	private String izena;
	private String pasahitza;
	private Date data;

	private ErabiltzailearenMota mota;
	private static List<ErabiltzailearenMota> motak = new ArrayList<ErabiltzailearenMota>();

	public LoginBean() {
		motak.add(new ErabiltzailearenMota(1, "ikaslea"));
		motak.add(new ErabiltzailearenMota(2, "irakaslea"));
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
		if (izena.length() != pasahitza.length()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Errorea: izenaren eta pasahitzaren luzera desberdinak dira."));
			return null;
		}
		if (izena.equals("pirata"))
			return "error";
		else
			return "ok";
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void onDateSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Data aukeratua: " + event.getObject()));
	}

	public ErabiltzailearenMota getMota() {
		return mota;
	}

	public void setMota(ErabiltzailearenMota mota) {
		this.mota = mota;
		//System.out.println("Erabiltzailearen mota: " + mota.getKodea() + "/" + mota.getErabMota());
	}

	public List<ErabiltzailearenMota> getMotak() {
		return motak;
	}

	public void setMotak(List<ErabiltzailearenMota> motak) {
		this.motak = motak;
	}

	public static ErabiltzailearenMota getObject(String mota) {
		for (ErabiltzailearenMota m : motak) {
			if (mota.equals(m.getErabMota()))
				return m;
		}
		return null;
	}

	public void listener(AjaxBehaviorEvent event) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Erabiltzailearen mota:" + mota.getKodea() + "/" + mota.getErabMota()));
	}

	public void onEventSelect(SelectEvent event) {
		this.mota = (ErabiltzailearenMota) event.getObject();
		// Egia esan, selection="#{login.mota}" atributuarekin ere lortzen da
		FacesContext.getCurrentInstance().addMessage("nireForm:mezuak",
				new FacesMessage("Erabiltzailearen mota (taula):" + mota.getKodea() + "/" + mota.getErabMota()));
	}

}