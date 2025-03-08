package Exceptions;

public class InformationIncompleteException extends Exception{
    public InformationIncompleteException()
    {
        super("You cannot create an account without credentials/name!");
    }
}
