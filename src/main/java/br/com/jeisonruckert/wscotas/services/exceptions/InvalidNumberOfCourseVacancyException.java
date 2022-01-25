package br.com.jeisonruckert.wscotas.services.exceptions;

public class InvalidNumberOfCourseVacancyException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public InvalidNumberOfCourseVacancyException(String msg) {
		super(msg);
	}
}
