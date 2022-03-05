public class Main {
    public static void main(String[] args) throws Exception {
        System.out.print("\033[H\033[2J");
        new View.V_Login().Authentication();
    }
}