package exceptions;




public class RegistroExistenteException extends NegocioException {
	
	public RegistroExistenteException(Throwable e) {
		super(e);
	}

	public RegistroExistenteException(String msg, Throwable e) {
		super(msg, e);
	}

	public RegistroExistenteException() {

	}

	public RegistroExistenteException(String string) {
		super(string);
	}

}
