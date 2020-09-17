import java.util.Scanner; 
 
public class Main {
 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  
 
        String input1 = scanner.nextLine();
        String input2 = scanner.nextLine();
        
        String[] input1_Parts = input1.split(" ");
        String[] input2_Parts = input2.split(" ");

        System.out.println(input1_Parts[0]);
        System.out.println(input1_Parts[1]);
        System.out.println(input1_Parts[2]);
        
        System.out.println(input2_Parts[0]);
        System.out.println(input2_Parts[1]);

         
        
    }
}