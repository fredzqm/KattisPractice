package DryRun1;
import java.util.Scanner;

/**
 * https://open.kattis.com/problems/vote
 * 
 * P
 * @author zhang
 *
 */
public class PopularVote {
	
	static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) {
		int numOfCase = in.nextInt();
		for (int tc = 0; tc < numOfCase; tc++){
			int n = in.nextInt();
			int sum = 0, first = 0, second = 0, index = -1;
			for (int i = 0 ; i < n; i++){
				int x = in.nextInt();
				sum += x;
				if (x > first){
					second = first;
					first = x;
					index = i;
				} else if (x > second) {
					second = x;
				}
			}
			index ++;
			if (second == first){
				System.out.println("no winner");
			} else if (first*2 > sum){
				System.out.println("majority winner " + index);
			} else {
				System.out.println("minority winner " + index);
			}
		}

	}

}
