package Model.Global;

public class M_ModelViewException extends Exception{
    private boolean fatalError = false;

    //Constructors
    public M_ModelViewException(Exception e)
    {
        //Constructor of parent
        super(e);
    }
    public M_ModelViewException(String s)
    {
        //Constructor of parent
        super(s);
    }
    public M_ModelViewException(String s, boolean fatality)
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