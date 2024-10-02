import java.util.*;
public class asses_1{
    public static void main(String args[]){
        Scanner sc= new Scanner(System.in);
        int num = sc.nextInt();
        if (noIsArmstrong(num)==true){
            System.out.println("true");
        }
        else{
            System.out.println("false");
        }
        
    }
    public static boolean noIsArmstrong(int num){
        int sum = 0;
        int n = num;
        boolean flag = false;
        while(n != 0){
            int remainder = n % 10;
            sum = sum + remainder*remainder*remainder;
            n = n/10;

        }
        if (sum == num){
            flag = true;            
        }
        return flag;

    }
}