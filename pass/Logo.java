package pass;
import java.util.Scanner;

/**
 * https://open.kattis.com/problems/logo
 * 
 * 3.1, geometric
 * 
 * @author zhang
 *
 */
public class Logo {
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		int numTc = in.nextInt();
		for (int tc = 0; tc < numTc; tc++) {
			int len = in.nextInt();
			double x = 0, y = 0, theta = 0;
			for (int j = 0; j < len; j++) {
				String direction = in.next();
				int distance = in.nextInt();
				switch (direction) {
				case "lt":
					theta += distance * Math.PI / 180;
					break;
				case "rt":
					theta -= distance * Math.PI / 180;
					break;
				case "fd":
					x += distance * Math.cos(theta);
					y += distance * Math.sin(theta);
					break;
				case "bk":
					x -= distance * Math.cos(theta);
					y -= distance * Math.sin(theta);
					break;
				default:
					throw new RuntimeException();
				}
			}
			long disToOrigin = Math.round(Math.sqrt(x * x + y * y));
			System.out.println(disToOrigin);
		}
	}
}