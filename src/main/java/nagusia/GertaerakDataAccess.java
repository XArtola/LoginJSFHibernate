package nagusia;

import eredua.HibernateUtil;
import eredua.domeinua.Erabiltzailea;
import eredua.domeinua.LoginGertaera;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.*;

public class GertaerakDataAccess {
	public GertaerakDataAccess() {
	}

	public Erabiltzailea createAndStoreErabiltzailea(String izena, String pasahitza, String mota) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Erabiltzailea e = new Erabiltzailea();
		e.setIzena(izena);
		e.setPasahitza(pasahitza);
		e.setMota(mota);
		session.persist(e);
		session.getTransaction().commit();
		return e;
	}

	public LoginGertaera createAndStoreLoginGertaera(String erabiltzailea, boolean login, Date data) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		LoginGertaera e = new LoginGertaera();
		try {
			e.setErabiltzailea((Erabiltzailea) session.get(Erabiltzailea.class, erabiltzailea));
			e.setLogin(login);
			e.setData(data);
			session.persist(e);
			session.getTransaction().commit();
		} catch (org.hibernate.PropertyValueException ex) {
			System.out.println("Errorea: data falta da ");
			e = null;
			session.getTransaction().rollback();
			e = null;
		} catch (Exception ex) {
			System.out.println("Errorea: erabiltzailea ez da existitzen: " + ex.toString());
			e = null;
		}
		return e;
	}

	public List<LoginGertaera> getLoginGertaerak() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List result = session.createQuery("from LoginGertaera").list();
		// System.out.println("getLoginGertaerak() : "+result);
		session.getTransaction().commit();
		return result;
	}

	public List<LoginGertaera> getLoginGertaerakv1(String erabiltzaileaIzena) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query q = session.createQuery(
				"select lg from LoginGertaera lg inner join lg.erabiltzailea e where e.izena= :erabiltzaileaIzena");
		q.setParameter("erabiltzaileaIzena", erabiltzaileaIzena);
		List<LoginGertaera> result = q.list();
		session.getTransaction().commit();
		return result;
	}

	public List<LoginGertaera> getLoginGertaerakv2(String erabiltzaileaIzena) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query q = session.createQuery("from LoginGertaera lg where lg.erabiltzailea.izena= :erabiltzaileaIzena");
		q.setParameter("erabiltzaileaIzena", erabiltzaileaIzena);
		List<LoginGertaera> result = q.list();
		session.getTransaction().commit();
		return result;
	}

	public List<LoginGertaera> getLoginGertaerakv3(String erabiltzaileaIzena) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Criteria c = session.createCriteria(LoginGertaera.class).createCriteria("erabiltzailea")
				.add(Restrictions.eq("izena", erabiltzaileaIzena));
		List<LoginGertaera> result = c.list();
		session.getTransaction().commit();
		return result;
	}

	public List<Erabiltzailea> getErabiltzaileak() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Erabiltzailea> result = session.createQuery("from Erabiltzailea").list();
		session.getTransaction().commit();
		return result;
	}

	public boolean deleteErabiltzailea(String erabiltzailea) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Erabiltzailea e = (Erabiltzailea) session.get(Erabiltzailea.class, erabiltzailea);
			session.delete(e);
			session.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println("Errorea: " + ex.toString());
			return false;
		}
		return true;
	}

	public Erabiltzailea createAndStoreErabiltzaileaLoginGertaeraBatekin(String izena, String pasahitza, String mota,
			boolean login, Date data) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Erabiltzailea e = new Erabiltzailea();
		e.setIzena(izena);
		e.setPasahitza(pasahitza);
		e.setMota(mota);
		LoginGertaera lg = new LoginGertaera();
		lg.setErabiltzailea(e);
		lg.setLogin(login);
		lg.setData(data);
		session.persist(e);
		session.persist(lg);
		session.getTransaction().commit();
		return e;
	}

	public static void main(String[] args) {
		GertaerakDataAccess e = new GertaerakDataAccess();
		System.out.println("Gertaeren sorkuntza:"); //
		e.createAndStoreErabiltzailea("Ane", "125", "ikaslea");
		e.createAndStoreLoginGertaera("Ane", true, new Date());
		e.createAndStoreLoginGertaera("Ane", false, new Date());
		e.createAndStoreErabiltzailea("Kepa", "126", "ikaslea");
		e.createAndStoreLoginGertaera("Kepa", true, new Date());
		e.createAndStoreLoginGertaera("Kepa", false, new Date());

		List<Erabiltzailea> er = e.getErabiltzaileak();
		System.out.println("3.1 => Erabiltzaileak:" + er);
		List<LoginGertaera> lg = e.getLoginGertaerak();
		System.out.println("3.1 => Login Gertaerak: " + lg);

		Erabiltzailea erab = lg.get(0).getErabiltzailea();
		// lg.get(0) login gertaeratik erabiltzailea lortzen da: .getErabiltzailea()
		System.out.println("3.2 => " + erab);

		List<LoginGertaera> lg1 = e.getLoginGertaerakv1(erab.getIzena());
		System.out.println("3.3.1 => " + erab.getIzena() + "ren Login Gertaerak: " + lg1);
		List<LoginGertaera> lg2 = e.getLoginGertaerakv2(erab.getIzena());
		System.out.println("3.3.2 => " + erab.getIzena() + "ren Login Gertaerak: " + lg2);
		List<LoginGertaera> lg3 = e.getLoginGertaerakv3(erab.getIzena());
		System.out.println("3.3.3 => " + erab.getIzena() + "ren Login Gertaerak: " + lg3);
		System.out.println("3.4 => " + erab.getGertaerak());
		LoginGertaera lgAne = e.createAndStoreLoginGertaera("Ane", true, null);
		System.out.println("4.1 => " + lgAne);

		e.createAndStoreErabiltzailea("Nekane", "127", "ikaslea");
		e.createAndStoreLoginGertaera("Nekane", true, new Date());
		System.out.println("4.2.1 => " + e.getLoginGertaerak());
		boolean res = e.deleteErabiltzailea("Nekane");
		System.out.println("4.2.2 => " + e.getLoginGertaerak());
		
		erab= e.createAndStoreErabiltzaileaLoginGertaeraBatekin("Peru","128","ikaslea",true,new Date());
		System.out.println("4.3 => " + e.getErabiltzaileak());
		System.out.println("4.3 => " + e.getLoginGertaerak());
		System.out.println("4.3 => Erabiltzailea: " +erab+" Bere gertaerak: "+erab.getGertaerak());
	}
}