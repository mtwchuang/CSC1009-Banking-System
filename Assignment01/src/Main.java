public class Main {
    public static void main(String[] args) throws Exception 
    {
        // clear message
        System.out.print("\033[H\033[2J");
        View.V_Login v1 = new View.V_Login();
        v1.Authentication();

        
    }
}
