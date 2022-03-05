public class Main {
    public static void main(String[] args) throws Exception 
    {
        // clear message
        System.out.print("\033[H\033[2J");
        View.Login v1 = new View.Login();
        v1.Authentication();

        
    }
}
