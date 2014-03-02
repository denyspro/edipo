package br.com.edipo.ada.model;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.edipo.ada.controller.UsuarioMB;
import br.com.edipo.ada.entity.Usuario;
import br.com.edipo.ada.util.PersistenceUtil;

/***
 * <i>Stateless bean</i> que faz o papel de modelo para o dom�nio de usu�rios.
 * 
 * @author Denys
 */
@Stateless
public class UsuarioSB {

	private static final Logger log = Logger.getLogger(UsuarioMB.class
			.getName());

	public static List<Usuario> getAll() {
		String jpql = "select u from Usuario u";

		return PersistenceUtil.getEntityManager().createQuery(jpql, Usuario.class)
				.getResultList();
	}

	public static Usuario getById(Integer id) {
		return PersistenceUtil.getEntityManager().find(Usuario.class, id);
	}

	public static Usuario getBySurrogate(String id) {

		String jpql = "select u from Usuario u where u.dsIdentificador = :dsIdentificador";
		Usuario u = null;

		Query query = PersistenceUtil.getEntityManager().createQuery(jpql,
				Usuario.class);
		query.setParameter("dsIdentificador", id);

		try {
			u = (Usuario) query.getSingleResult();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenceUtil.closeEntityManager();
		}

		return u;
	}

	public static boolean save(Usuario u) {

		boolean r = false;

		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(u);
			tx.commit();

			r = true;
		} catch (Exception e) {
			log.severe(e.toString());

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
		} finally {
			PersistenceUtil.closeEntityManager();
		}

		return r;
	}

	public static boolean delete(Usuario u) {

		boolean r = false;

		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.remove(u);
			tx.commit();

			r = true;
		} catch (Exception e) {
			log.severe(e.toString());

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
		} finally {
			PersistenceUtil.closeEntityManager();
		}

		return r;
	}
}