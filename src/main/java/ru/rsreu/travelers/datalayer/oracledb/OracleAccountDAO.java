package ru.rsreu.travelers.datalayer.oracledb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

import ru.rsreu.travelers.datalayer.AccountDAO;
import ru.rsreu.travelers.datalayer.DataBaseException;
import ru.rsreu.travelers.datalayer.data.Account;

public class OracleAccountDAO implements AccountDAO {
    private static Resourcer resourcer = ProjectResourcer.getInstance();
    private final int NUMBER_COLUMN_ACCOUNT_IDENTIFIER = 1;
    private final int NUMBER_COLUMN_LOGIN = 2;
    private final int NUMBER_COLUMN_PASSWORD = 3;
    private final int NUMBER_COLUMN_GROUP_ID = 4;
    private final int NUMBER_COLUMN_IS_BLOCKED = 5;
    private final int NUMBER_COLUMN_IS_AUTHORIZED = 6;
    private final int NUMBER_COLUMN_NAME = 7;
    private Connection connection;

    public OracleAccountDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Account tryLoginAccount(String login, String password) throws DataBaseException {
        return getAccount(login, password);
    }

    @Override
    public Account getAccount(int id) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.getAccount");
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            Account account;
            if (result.next()) {
                account = this.getAccountWithName(result);
            } else {
                account =  Account.NULL_ACCOUNT;
            }
            result.close();
            preparedStatement.close();
            return account;
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public List<Account> getAccounts() throws DataBaseException {
        String sqlQuery = resourcer.getString("query.allAccounts");
        return getAccounts(sqlQuery);
    }

    @Override
    public List<Account> getAuthorizedAccounts() throws DataBaseException {
        String sqlQuery = resourcer.getString("query.authorizedAccounts");
        return getAccounts(sqlQuery);
    }

    @Override
    public void setAuthorized(int accountId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.setAuthorized");
        changeAuthorized(accountId, sqlQuery);
    }

    @Override
    public void resetAuthorized(int accountId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.resetAuthorized");
        changeAuthorized(accountId, sqlQuery);
    }

    @Override
    public void addAccount(String login, String password, int group_id) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.createAccount");
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, group_id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }

    public void deleteAccount(int accountId) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.deleteAccount");
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, accountId);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public void updateAccount(int id, String login, String password, int group_id) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.fullUpdateAccount");
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, group_id);
            preparedStatement.setInt(4, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public void updateAccountWithoutGroup(int id, String login, String password) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.updateAccountWithoutGroup");
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }

    private Account getAccount(ResultSet result) throws SQLException {
        int id = result.getInt(NUMBER_COLUMN_ACCOUNT_IDENTIFIER);
        String login = result.getString(NUMBER_COLUMN_LOGIN);
        String password = result.getString(NUMBER_COLUMN_PASSWORD);
        int groupId = result.getInt(NUMBER_COLUMN_GROUP_ID);
        boolean isBlocked = result.getInt(NUMBER_COLUMN_IS_BLOCKED) > 0;
        boolean isAuthorized = result.getInt(NUMBER_COLUMN_IS_AUTHORIZED) > 0;
        return new Account(id, login, password, groupId, isBlocked, isAuthorized);
    }

    private Account getAccountWithName(ResultSet result) throws SQLException {
        Account account = getAccount(result);
        if (account.getGroupId() > 2) {
            String name = result.getString(NUMBER_COLUMN_NAME);
            account.setName(name);
        }
        return account;
    }

    private Account getAccount(String login, String password) throws DataBaseException {
        String sqlQuery = resourcer.getString("query.login");
        try {
            System.out.println("login=" + login + "&password=" + password);
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                Account account = this.getAccountWithName(result);
                result.close();
                preparedStatement.close();
                return account;
            } else {
                result.close();
                preparedStatement.close();
                return Account.NULL_ACCOUNT;
            }

        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }

    private List<Account> getAccounts(String sqlQuery) throws DataBaseException {
        List<Account> accounts = new ArrayList<Account>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery(sqlQuery);
            while (result.next()) {
                Account account = getAccount(result);
                accounts.add(account);
            }
            result.close();
            statement.close();
            return accounts;
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            throw new DataBaseException(exception.getMessage());
        }
    }

    private void changeAuthorized(int accountId, String sqlQuery) throws DataBaseException {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, accountId);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException exception) {
            throw new DataBaseException(exception.getMessage());
        }
    }
}
