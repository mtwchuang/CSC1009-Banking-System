package View;

public class V_Error {
    public void run(Exception e){
        System.out.println("");
        System.out.println(".#..#..#..######..#####...#####....####...#####...#..#..#.");
        System.out.println("..#.#.#...##......##..##..##..##..##..##..##..##...#.#.#..");
        System.out.println(".#######..####....#####...#####...##..##..#####...#######.");
        System.out.println("..#.#.#...##......##..##..##..##..##..##..##..##...#.#.#..");
        System.out.println(".#..#..#..######..##..##..##..##...####...##..##..#..#..#.");
        System.out.println("..........................................................\n");

        System.out.println("Fatal error encountered. Please contact an administrator.\n");

        System.out.println("Error Message:");
        System.out.println(e.getMessage() + "\n");

        System.out.println("Stack Trace:");
        e.printStackTrace();
        System.out.println("");
        
        System.exit(0);
    }
}
