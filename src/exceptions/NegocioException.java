package exceptions;



public class NegocioException extends Exception {
	
	public NegocioException(Throwable e) {
		super(e);
	}

	public NegocioException(String msg, Throwable e) {
		super(msg, e);
	}

	public NegocioException() {
	}

	public NegocioException(String msg) {
		super(msg);
	}
}
