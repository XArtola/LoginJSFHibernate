package eredua.domeinua;

import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Entity;

@Entity
public class LoginGertaera {
	@Id
	private Long id;
	private String deskribapena;
	private Date data;

	public LoginGertaera() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeskribapena() {
		return deskribapena;
	}

	public void setDeskribapena(String deskribapena) {
		this.deskribapena = deskribapena;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}