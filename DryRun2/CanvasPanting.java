package DryRun2;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * https://open.kattis.com/problems/canvas
 * 
 * P
 * @author zhang
 *
 */
public class CanvasPanting {
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		int numOfCase = in.nextInt();
		for (int tc = 0; tc < numOfCase; tc++) {
			int numPlate = in.nextInt();
			PriorityQueue<Long> pq = new PriorityQueue<>();
			for (int i = 0; i < numPlate; i++) {
				pq.add(in.nextLong());
			}
			long sum = 0;
			while (pq.size() > 1) {
				long a = pq.poll();
				long b = pq.poll();
				sum += a + b;
				pq.add(a + b);
			}
			System.out.println(sum);
		}
	}
}
