package business;

import java.io.Serializable;

public class AddMemberWindowException extends Exception implements Serializable {

    public AddMemberWindowException() {
        super();
    }

    public AddMemberWindowException(String msg) {
        super(msg);
    }

    public AddMemberWindowException(Throwable t) {
        super(t);
    }

    private static final long serialVersionUID = 8978723266036027364L;

}
