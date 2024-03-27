package exceptions;



public class ApplicationException extends Exception{
    
    public ApplicationException(Throwable e){
        super(e);
    }
    
    public ApplicationException(String msg , Throwable e){
        super(msg,e);
    }
    
    public ApplicationException(){
        
    }
    
    public ApplicationException(String msg){
        super(msg);
    }
}

