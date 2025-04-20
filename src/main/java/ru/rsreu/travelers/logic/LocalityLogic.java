package ru.rsreu.travelers.logic;

import ru.rsreu.travelers.datalayer.*;
import ru.rsreu.travelers.datalayer.data.Locality;
import ru.rsreu.travelers.datalayer.data.TypeLocality;

import java.util.ArrayList;
import java.util.List;

public class LocalityLogic {
    private static DAOFactory factory = DAOFactory.getInstance(DataBaseType.ORACLE);
    private static LocalityDAO localityDAO = factory.getLocalityDAO();
    private static TypeLocalityDAO typeLocalityDAO = factory.getTypeLocalityDAO();
    public static List<Locality> getLocalities() {
        try {
            return localityDAO.getLocalities();
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
            return new ArrayList<Locality>();
        }
    }

    public static Locality getLocality(int localityId) {
        try {
            return localityDAO.getLocality(localityId);
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
            return Locality.NULL_LOCALITY;
        }
    }

    public static List<TypeLocality> getTypesLocalities() {
        try {
            return typeLocalityDAO.getTypesLocalities();
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
            return new ArrayList<TypeLocality>();
        }
    }

    public static boolean createLocality(String name, int type) {
        try {
            int maxType = typeLocalityDAO.getTypesLocalities().size();
            if (type < 1 || type > maxType) {
                return false;
            }
            return localityDAO.createLocality(name, type);
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public static boolean editLocality(int localityId, String name, int type) {
        try {
            int maxType = typeLocalityDAO.getTypesLocalities().size();
            if (type < 1 || type > maxType) {
                return false;
            }
            localityDAO.editLocality(localityId, name, type);
            return true;
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public static boolean deleteLocality(int localityId) {
        try {
            if (localityDAO.localityUsed(localityId)) {
                return false;
            }
            localityDAO.deleteLocality(localityId);
            return true;
        } catch (DataBaseException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

}
