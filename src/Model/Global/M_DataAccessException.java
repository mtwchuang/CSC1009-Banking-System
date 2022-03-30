package Model.Global;

public class M_DataAccessException extends Exception{
    private boolean fatalError = false;

    //Constructors
    public M_DataAccessException(Exception e)
    {
        //Constructor of parent
        super(e);
    }
    public M_DataAccessException(String s)
    {
        //Constructor of parent
        super(s);
    }
    public M_DataAccessException(String s, boolean fatality)
    {
        //Constructor of parent
        super(s);

        this.fatalError = fatality;
    }

    //Getter
    public boolean fatalOccurred(){
        return this.fatalError;
    }
}