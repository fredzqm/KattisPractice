package DryRun1;
import java.util.HashSet;
import java.util.Scanner;

/**
 * https://open.kattis.com/problems/everywhere
 * 
 * P
 * @author zhang
 *
 */
public class IveBeenEveryWhere {

	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		int numOfCase = in.nextInt();
		for (int tc = 0; tc < numOfCase; tc++) {
			int n = in.nextInt(); in.nextLine();
			HashSet<String> set = new HashSet<>();
			for (int i = 0; i < n; i++) {
				set.add(in.nextLine());
			}
			System.out.println(set.size());
		}
	}

}
