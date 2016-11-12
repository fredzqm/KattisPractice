package DryRun1;
import java.util.Scanner;

/**
 * https://open.kattis.com/problems/magical3
 * 
 * P
 * @author zhang
 *
 */
public class TheMagicThree {
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {

		next: while (in.hasNext()) {
			int x = in.nextInt() - 3;
			if (x <= 3) {
				if (x == -3) {
					break;
				} else if (x == 0) {
					System.out.println(4);
				} else
					System.out.println("No such base");
			} else {
				int sqrt = (int) (x > 100 ? Math.round(Math.sqrt(x)) : x);
				for (int i = 4; i <= sqrt; i++) {
					if (x % i == 0) {
						System.out.println(i);
						continue next;
					}
				}
				if (x % 3 == 0)
					System.out.println(x / 3);
				else if (x % 2 == 0)
					System.out.println(x / 2);
				else
					System.out.println(x);
			}
		}
	}

}
