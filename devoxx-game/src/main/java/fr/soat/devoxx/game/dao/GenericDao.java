package fr.soat.devoxx.game.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
@Deprecated
public class GenericDao<T extends Serializable> {

	@PersistenceContext
	EntityManager entityManager;

	public EntityManager getEntityManager(){
		return entityManager;
	}
	
}