package com.roadtoepam.darthvider.dao;

import java.util.List;
import java.util.Optional;

import com.roadtoepam.darthvider.entity.User;
import com.roadtoepam.darthvider.exception.DaoException;

public interface UserDao {

	  /**
	   * Get all users.
	   *
	   * @return all the customers as a list.
	   * @throws Exception if any error occurs.
	   */
	  List<User> findAll() throws DaoException;

	  /**
	   * Get user as Optional by id.
	   *
	   * @param id unique identifier of the user.
	   * @return an optional with customer if a customer with unique identifier <code>id</code> exists,
	   *     empty optional otherwise.
	   * @throws Exception if any error occurs.
	   */
	  Optional<User> findById(int id) throws DaoException;

	  /**
	   * Add a customer.
	   *
	   * @param customer the customer to be added.
	   * @return true if customer is successfully added, false if customer already exists.
	   * @throws Exception if any error occurs.
	   */
	  boolean add(User customer, String password) throws DaoException;

	  /**
	   * Update a customer.
	   *
	   * @param customer the customer to be updated.
	   * @return true if customer exists and is successfully updated, false otherwise.
	   * @throws Exception if any error occurs.
	   */
	  boolean update(User customer) throws DaoException;

	  /**
	   * Delete a customer.
	   *
	   * @param customer the customer to be deleted.
	   * @return true if customer exists and is successfully deleted, false otherwise.
	   * @throws Exception if any error occurs.
	   */
	  boolean delete(User customer) throws DaoException;
	}