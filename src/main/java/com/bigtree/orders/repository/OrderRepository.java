package com.bigtree.orders.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import com.bigtree.orders.model.Order;
import com.bigtree.orders.model.enums.OrderStatus;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository class for <code>Order</code> domain objects All method names are compliant
 * with Spring Data naming conventions so this interface can easily be extended for Spring
 * Data. See:
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 *
 * @author Nav
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, Integer>{
    

    /**
	 * Retrieve count of <code>Order</code> from the data store for given date
	 * @return a count
	 */
    @Transactional(readOnly = true)
    Long countByDate(LocalDate date);

    /**
	 * Retrieve all <code>Order</code>s from the data store for given user's email
	 * @return a <code>Collection</code> of <code>Order</code>s
	 */
    @Transactional(readOnly = true)
    List<Order> findByEmail(String email);

    /**
	 * Retrieve all <code>Order</code>s from the data store for given reference
	 * @return a <code>Collection</code> of <code>Order</code>s
	 */
    @Transactional(readOnly = true)
    @Query("SELECT o FROM Order o WHERE o.reference = ?1")
    Collection<Order> findByReference(String reference);
    
    /**
	 * Retrieve all <code>Order</code>s from the data store for given status
	 * @return a <code>Collection</code> of <code>Order</code>s
	 */
    @Transactional(readOnly = true)
    @Query("SELECT o FROM Order o WHERE o.status = ?1")
    Collection<Order> findByStatus(OrderStatus status);

    /**
	 * Retrieve all deleted <code>Order</code>s from the data store
	 * @return a <code>Collection</code> of <code>Order</code>s
	 */
    @Transactional(readOnly = true)
    @Query("SELECT o FROM Order o WHERE o.deleted = ?1")
    Collection<Order> findByDeleted(boolean deleted);
    
    /**
	 * Retrieve all <code>Order</code>s from the data store.
	 * @return a <code>Collection</code> of <code>Order</code>s
	 */
    @Transactional(readOnly = true)
    @Query("SELECT o FROM Order o WHERE o.deleted = 'false'")
	List<Order> findAll() throws DataAccessException;
}
