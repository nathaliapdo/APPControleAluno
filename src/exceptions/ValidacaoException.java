
package exceptions;


import java.util.ArrayList;
import java.util.Collection;

public class ValidacaoException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Collection<String> erros = new ArrayList<String>();

	public ValidacaoException(Throwable e) {
		super(e);
	}

	public ValidacaoException(String msg) {
		super(msg);
		erros.add(msg);
	}

	public ValidacaoException() {
	}

	public ValidacaoException(Collection<String> erros) {
		this.erros.addAll(erros);
	}

	public Collection<String> getErros() {
		return erros;
	}
}
