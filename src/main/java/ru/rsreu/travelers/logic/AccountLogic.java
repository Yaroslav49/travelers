package ru.rsreu.travelers.logic;

import ru.rsreu.travelers.datalayer.*;
import ru.rsreu.travelers.datalayer.data.Account;
import ru.rsreu.travelers.datalayer.data.UserGroup;

import java.util.ArrayList;
import java.util.List;

public class AccountLogic {
    private static DAOFactory factory = DAOFactory.getInstance(DataBaseType.ORACLE);
    private static AccountDAO accountDAO = factory.getAccountDAO();
    private static UserDAO userDAO = factory.getUserDAO();
    private static TripDAO tripDAO = factory.getTripDAO();
    private static ParticipantTripDAO participantTripDAO = factory.getParticipantTripDAO();
    private static UserGroupDAO userGroupDAO = factory.getUserGroupDAO();

    public static List<Account> getAllAccounts() {
        try {
            return accountDAO.getAccounts();
        } catch (DataBaseException exception) {
            return new ArrayList<Account>();
        }
    }

    public static List<Account> getAuthorizedAccounts() {
        try {
            return accountDAO.getAuthorizedAccounts();
        } catch (DataBaseException exception) {
            return new ArrayList<Account>();
        }
    }

    public static Account getAccount(int accountId) {
        return getAccountById(accountId);
    }

    public static List<UserGroup> getUserGroups() {
        try {
            return userGroupDAO.getUserGroups();
        } catch (DataBaseException exception) {
            return new ArrayList<UserGroup>();
        }
    }

    public static EditAccountResult editAccount(int adminId, Account newAccount) {
        int id = newAccount.getId();
        int groupId = newAccount.getGroupId();
        String login = newAccount.getLogin();
        String password = newAccount.getPassword();
        String name = newAccount.getName();
        Account oldAccount = getAccountById(id);
        boolean posibilityDeleteUser = checkPossibilityDeleteUser(oldAccount);
        try {
            if (posibilityDeleteUser && oldAccount.getId() != adminId) {
                if (needAddUser(oldAccount.getGroupId(), groupId)) {
                    System.out.println("add User starts");
                    userDAO.addUser(id, name);
                } else if (needDeleteUser(oldAccount.getGroupId(), groupId)) {
                    System.out.println("delete User starts");
                    userDAO.deleteUser(id);
                }
                System.out.println("update Account starts");
                accountDAO.updateAccount(id, login, password, groupId);
                if (groupId > 2 && name != null && !name.equals("")) {
                    System.out.println("update Name user starts");
                    userDAO.updateNameUser(id, name);
                }
                return EditAccountResult.SUCCESS;
            } else {
                System.out.println("update Account without group starts");
                accountDAO.updateAccountWithoutGroup(id, login, password);
                if (name != null && !name.equals("")) {
                    System.out.println("update Name user starts");
                    userDAO.updateNameUser(id, name);
                }

                if (oldAccount.getGroupId() != groupId) {
                    if (oldAccount.getId() == adminId) {
                        return EditAccountResult.ERROR_EDIT_SELF;
                    }
                    return EditAccountResult.ERROR_EDIT_OTHER;
                } else {
                    return EditAccountResult.SUCCESS;
                }
            }
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
            return EditAccountResult.UNKNOWN_ERROR;
        }
    }

    public static boolean deleteAccount(int accountId) {
        Account account = getAccountById(accountId);
        boolean posibilityDeleteUser = checkPossibilityDeleteUser(account);
        try {
            if (posibilityDeleteUser) {
                if (account.getGroupId() > 2) {
                    userDAO.deleteUser(accountId);
                }
                accountDAO.deleteAccount(accountId);
                return true;
            } else {
                return false;
            }

        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public static void addAccount(String login, String password, int group_id, String name) {
        try {
            if (group_id < 1 || group_id > 4) {
                System.out.println("Error: try to add account with incorrect group_id");
            } else {
                accountDAO.addAccount(login, password, group_id);
                if (group_id > 2) {
                    Account account = accountDAO.tryLoginAccount(login, password);
                    userDAO.addUser(account.getId(), name);
                }
            }
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static boolean needAddUser(int oldGroup, int newGroup) {
        return (oldGroup == 1 || oldGroup == 2) && (newGroup == 3 || newGroup == 4);
    }

    private static boolean needDeleteUser(int oldGroup, int newGroup) {
        return (oldGroup == 3 || oldGroup == 4) && (newGroup == 1 || newGroup == 2);
    }

    private static boolean checkPossibilityDeleteUser(Account account) {
        int groupId = account.getGroupId();
        if (groupId < 3) {
            return true;
        }
        try {
            int accountId = account.getId();
            if (groupId == 3) {
                return !tripDAO.driverHasTrips(accountId);
            }
            if (groupId == 4) {
                return !participantTripDAO.passengerParticipateInTrips(accountId);
            }
            return true;
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
            return true;
        }
    }

    private static Account getAccountById(int accountId) {
        try {
            return accountDAO.getAccount(accountId);
        } catch (DataBaseException exception) {
            return Account.NULL_ACCOUNT;
        }
    }

}
