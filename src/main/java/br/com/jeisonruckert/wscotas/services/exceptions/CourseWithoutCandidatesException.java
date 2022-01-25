package br.com.jeisonruckert.wscotas.services.exceptions;

public class CourseWithoutCandidatesException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public CourseWithoutCandidatesException(String msg) {
		super(msg);
	}
}
