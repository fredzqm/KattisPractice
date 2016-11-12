package DryRun2;
import java.util.Scanner;

/**
 * https://open.kattis.com/problems/dicecup
 * 
 * P
 * @author zhang
 *
 */
public class DiceCup {
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		int a = in.nextInt();
		int b = in.nextInt();
		if (a > b){
			int t = a;
			a = b;
			b = t;
		}
		for (int i = 1 + a; i <= b+1; i++){
			System.out.println(i);
		}
	}
}
