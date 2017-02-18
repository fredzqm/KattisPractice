import java.util.Arrays;
import java.util.Scanner;
/**
 * 
 * @author zhang
 *
 */
public class Compromise {
    static Scanner in = new Scanner(System.in);
    
    public static void main(String[] args) {
        int numTestCase = in.nextInt();
        for (int testCase = 0; testCase < numTestCase; testCase++) {
            int n = in.nextInt();
            int m = in.nextInt();
            String[] x = new String[n];
            in.nextLine();
            for (int i = 0; i < n; i++) {
                x[i] = in.nextLine();
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < m; i++) {
                int c = 0;
                for (int j = 0; j < n; j++) {
                    if (x[j].charAt(i) == '1')
                        c++;
                }
                if (c*2 >= n)
                    sb.append('1');
                else
                    sb.append('0');
            }
            System.out.println(sb.toString());
        }
    }
    
}
