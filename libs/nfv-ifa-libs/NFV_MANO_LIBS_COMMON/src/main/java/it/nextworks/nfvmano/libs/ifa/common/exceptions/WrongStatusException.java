package it.nextworks.nfvmano.libs.ifa.common.exceptions;

public class WrongStatusException extends Exception {

    public WrongStatusException(String message){
        super(message);
    }

    public WrongStatusException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public WrongStatusException(Throwable throwable) {
        super(throwable);
    }

    public WrongStatusException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }

    public WrongStatusException() {
    }
}
