package ru.rsreu.travelers.datalayer;

public class DataBaseException extends Exception {
	private static final long serialVersionUID = 1L;

	public DataBaseException() {
		super();
	}

	public DataBaseException(String message) {
		super(message);
	}
}
