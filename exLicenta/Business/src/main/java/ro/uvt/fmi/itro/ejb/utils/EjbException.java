package ro.uvt.fmi.itro.ejb.utils;

public class EjbException extends Exception{
	public EjbException(String msg){
		super(msg);
	}
	public EjbException(String msg, Throwable ex){
		super(msg, ex);
	}
}
