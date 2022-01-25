package br.com.jeisonruckert.wscotas.services.exceptions;

public class FileReaderException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public FileReaderException(String msg) {
		super(msg);
	}
}
