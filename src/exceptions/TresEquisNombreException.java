package exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class TresEquisNombreException extends Exception{
	
	private static final long serialVersionUID=4868649853116785874L;
	public TresEquisNombreException() {
		
		// TODO Auto-generated constructor stub
	}
	public TresEquisNombreException(String mensaje) {
		super(mensaje);
	}

}
