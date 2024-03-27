package exceptions;

public class RegistroInexistenteException extends NegocioException {

    public RegistroInexistenteException(Throwable e) {
        super(e);
    }

    public RegistroInexistenteException(String msg, Throwable e) {
        super(msg, e);
    }

    public RegistroInexistenteException() {

    }

    public RegistroInexistenteException(String string) {
        super(string);
    }
}
