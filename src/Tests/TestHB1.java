package Tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;

import fitec.dba.metier.Auteur;
import fitec.dba.metier.User;

public class TestHB1 {

	private static SessionFactory sessionFactory;

	private static int counter = 0;

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the sessionFactory from hibernate?=.cfg.xml
			return new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.out.println("Initialiser SessionFactroy creation failed. " + ex);
			throw new ExceptionInInitializerError(ex);
		}

	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Before
	public void setUp() {
		System.out.println("setup " + counter);
		sessionFactory = buildSessionFactory();

	}

	@Test
	public void test() {
		System.out.println("Enter Test 1");
		assertNotNull("Pas de connexion à Hibernate", sessionFactory);
		System.out.println("Connexion Hibernate OK");
		Session session = sessionFactory.openSession();
		
		Integer id = 2;
		User user = session.find(User.class, id);
		
		if ( user != null )
			System.out.println(user);
		
		Auteur auteur = (Auteur) session.createQuery("SELECT A FROM Auteur A WHERE A.id = :id ").setParameter("id", 1).getSingleResult();
		
		if ( auteur != null )
			System.out.println(auteur);
		
		
		System.out.println("start auteur list");
		Set<Auteur> auteurs = new HashSet<>();
		
		List<Auteur> auteurList = (List<Auteur>) session.createQuery("FROM Auteur").list();
		
		
		
		
		for (Auteur auteur2 : auteurList) {
			System.out.println(auteur2.getNom());
			
			
		}
		
		 auteurs.addAll(auteurList);
		 System.out.println("size auteur Set: "+auteurs.size());
		for (Auteur auteur2 : auteurs) {
			System.out.println(auteur2.getNom());
		}
		
		Iterator<Auteur> it = auteurs.iterator();
		while (it.hasNext()) {
			System.out.println("iterator");
		 System.out.println(it.next().getNom());
		}
	}
}
