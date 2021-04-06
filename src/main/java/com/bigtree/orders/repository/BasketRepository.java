package com.bigtree.orders.repository;

import java.util.List;
import java.util.Optional;

import com.bigtree.orders.model.Basket;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository class for <code>Basket</code> domain objects All method names are compliant
 * with Spring Data naming conventions sb this interface can easily be extended for Spring
 * Data. See:
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 *
 * @author Nav
 */
@Repository
public interface BasketRepository extends CrudRepository<Basket, Integer>{
    
    /**
	 * Retrieve all <code>Basket</code>s from the data store for given user's email
	 * @return a <code>Collection</code> of <code>Basket</code>s
	 */
    @Transactional(readOnly = true)
    Basket findByEmail(String email);

	/**
	 * Retrieve <code>Basket</code>s from the data store for given user's email and BasketId
	 * @return a <code>Basket</code>
	 */
	@Transactional(readOnly = true)
	Basket findByEmailAndBasketId(String email, String basketId);

    /**
	 * Retrieve all <code>Basket</code>s from the data store.
	 * @return a <code>Collection</code> of <code>Basket</code>s
	 */
    @Transactional(readOnly = true)
    @Query("SELECT b FROM Basket b")
	List<Basket> findAll() throws DataAccessException;

	/**
	 * Retrieve <code>Basket</code>s from the data store for given BasketId
	 * @return a <code>Basket</code>
	 */
	@Transactional(readOnly = true)
	Optional<Basket> findByBasketId(String basketId);
}
