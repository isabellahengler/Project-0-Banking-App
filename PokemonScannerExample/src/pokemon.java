import java.util.Scanner;

public class PartayScanner {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("Pikachu");
        String pokemon1 = scan.nextLine();

        System.out.println("JigglyPuff:");
        String pokemon2 = scan.nextLine();

        System.out.println("Squirtle");
        String pokemon3 = scan.nextLine();

        System.out.println("Charmander");
        String pokemon4 = scan.nextLine();

        System.out.println("Evie");
        String pokemon5 = scan.nextLine();

        System.out.println("RockGuy");
        String pokemon6 = scan.nextLine();

        System.out.println("Here are your pokemon!");
        System.out.println(pokemon1);
        System.out.println(pokemon2);
        System.out.println(pokemon3);
        System.out.println(pokemon4);
        System.out.println(pokemon5);
        System.out.println(pokemon6);
    }

}

