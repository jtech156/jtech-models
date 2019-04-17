package com.jtech.helper.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AbstractBaseRepository {

	private ThreadLocal<Session> currentSession = new ThreadLocal<Session>();

	@PersistenceContext
	private EntityManager entityManager;

	public <T> T save(T entity) {

		this.getCurrentSession().save(entity);
		this.getCurrentSession().flush();
		return entity;
	}

	public <T> T update(T entity) {

		this.getCurrentSession().update(entity);
		this.getCurrentSession().flush();
		return entity;
	}

	public <T> void delete(T entity) {

		this.getCurrentSession().delete(entity);
		this.getCurrentSession().flush();
	}

	public <T> List<T> getResultList(Criteria cr, Class<T> clz) {

		List<T> output = this.castList(clz, cr.list());
		return output;
	}

	public <T> T getSingleResultOrNull(Criteria cr, Class<T> clz) {
		List<T> output = null;
		output = this.castList(clz, cr.setMaxResults(1).list());
		if (output == null || output.isEmpty()) {
			return null;
		} else {
			return output.get(0);
		}
	}

	public Session getCurrentSession() {
		Session session = currentSession.get();
		if (session != null) {
			return session;
		} else {
			return entityManager.unwrap(Session.class);
		}
	}

	public CriteriaBuilder getCriteriaBuilder() {
		return entityManager.getCriteriaBuilder();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public <T> List<T> castList(Class<T> clz, Collection<?> c) {
		List<T> r = new ArrayList<T>(c.size());
		for (Object o : c)
			r.add(clz.cast(o));
		return r;
	}
}