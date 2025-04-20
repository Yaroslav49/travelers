package ru.rsreu.travelers.logic;

import jakarta.servlet.http.HttpSession;
import ru.rsreu.travelers.datalayer.*;
import ru.rsreu.travelers.datalayer.data.Account;

import java.util.Objects;

public class LoginLogic {
	private static Account currentAccount;

	private static DAOFactory factory = DAOFactory.getInstance(DataBaseType.ORACLE);
	private static AccountDAO accountDAO = factory.getAccountDAO();

	public static LoginResult tryLogin(String login, String password) {
		try {
			currentAccount = accountDAO.tryLoginAccount(login, password);
			if (currentAccount.getId() < 0) {
				return LoginResult.INCORRECT_PASSWORD;
			}
			if (currentAccount.isBlocked()) {
				return LoginResult.BAN;
			}
			return LoginResult.OK;
		} catch (DataBaseException exception) {
			currentAccount = null;
			System.out.println(exception.getMessage());
			return LoginResult.DATABASE_ERROR;
		}
	}
	
	public static int getCurrentAccountId() {
		return currentAccount != null ? currentAccount.getId() : -1;
	}
	
	public static int getCurrentAccountGroupId() {
		return currentAccount != null ? currentAccount.getGroupId() : -1;
	}
	public static String getCurrentAccountName() { return currentAccount != null && currentAccount.getName() != null ? currentAccount.getName() : ""; }

	public static void setAuthorized(int accountId) {
		try {
			accountDAO.setAuthorized(accountId);
		} catch (DataBaseException exception) {
			System.out.println(exception.getMessage());
		}
	}

	public static void resetAuthorized(int accountId) {
		try {
			accountDAO.resetAuthorized(accountId);
		} catch (DataBaseException exception) {
			System.out.println(exception.getMessage());
		}
	}

	//заглушка
	private static Account getAccount(String login, String password) throws DataBaseException {
		if (Objects.equals(login, "Anna")) {
			return new Account(1, login, password, 4, false, false);
		} else if (Objects.equals(login, "Ashot")) {
			return new Account(2, login, password, 3, false, false);
		} else if (Objects.equals(login, "admin")) {
			return new Account(3, login, password, 1, false, false);
		} else if (Objects.equals(login, "moder")) {
			return new Account(4, login, password, 2, false, false);
		} else {
			return new Account(-1, login, password, 4, false, false);
		}
	}
	
}
