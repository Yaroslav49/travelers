package ru.rsreu.travelers.datalayer;

import ru.rsreu.travelers.datalayer.data.Account;

import java.util.List;
/**
 * Interface for Data Access Object that manages account operations.
 */
public interface AccountDAO {

	/**
	 * Attempts to log in an account with the specified login and password.
	 *
	 * @param login the login of the account
	 * @param password the password of the account
	 * @return the Account object if login is successful
	 * @throws DataBaseException if there is an error accessing the database
	 */
	public Account tryLoginAccount(String login, String password) throws DataBaseException;

	/**
	 * Retrieves an account by its unique identifier.
	 *
	 * @param id the unique identifier of the account
	 * @return the Account object corresponding to the given id
	 * @throws DataBaseException if there is an error accessing the database
	 */
	public Account getAccount(int id) throws DataBaseException;

	/**
	 * Retrieves a list of all accounts.
	 *
	 * @return a List of Account objects
	 * @throws DataBaseException if there is an error accessing the database
	 */
	public List<Account> getAccounts() throws DataBaseException;

	/**
	 * Retrieves a list of all authorized accounts.
	 *
	 * @return a List of authorized Account objects
	 * @throws DataBaseException if there is an error accessing the database
	 */
	public List<Account> getAuthorizedAccounts() throws DataBaseException;

	/**
	 * Sets an account as authorized by its unique identifier.
	 *
	 * @param accountId the unique identifier of the account to authorize
	 * @throws DataBaseException if there is an error accessing the database
	 */
	public void setAuthorized(int accountId) throws DataBaseException;

	/**
	 * Resets the authorization status of an account by its unique identifier.
	 *
	 * @param accountId the unique identifier of the account to reset authorization
	 * @throws DataBaseException if there is an error accessing the database
	 */
	public void resetAuthorized(int accountId) throws DataBaseException;

	/**
	 * Adds a new account with the specified login, password, and group ID.
	 *
	 * @param login the login of the new account
	 * @param password the password of the new account
	 * @param group_id the group ID associated with the new account
	 * @throws DataBaseException if there is an error accessing the database
	 */
	public void addAccount(String login, String password, int group_id) throws DataBaseException;

	/**
	 * Deletes an account by its unique identifier.
	 *
	 * @param accountId the unique identifier of the account to delete
	 * @throws DataBaseException if there is an error accessing the database
	 */
	public void deleteAccount(int accountId) throws DataBaseException;

	/**
	 * Updates an existing account's details by its unique identifier.
	 *
	 * @param id the unique identifier of the account to update
	 * @param login the new login for the account
	 * @param password the new password for the account
	 * @param group_id the new group ID for the account
	 * @throws DataBaseException if there is an error accessing the database
	 */
	public void updateAccount(int id, String login, String password, int group_id) throws DataBaseException;

	/**
	 * Updates an existing account's details without changing its group ID.
	 *
	 * @param id the unique identifier of the account to update
	 * @param login the new login for the account
	 * @param password the new password for the account
	 * @throws DataBaseException if there is an error accessing the database
	 */
	public void updateAccountWithoutGroup(int id, String login, String password) throws DataBaseException;
}

