package DryRun1;

import java.util.Scanner;

/**
 * https://open.kattis.com/problems/junk
 * 
 * P
 * @author zhang
 *
 */
public class SpaceJunk {
	public static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		int numTestCase = in.nextInt();
		for (int tc = 0; tc < numTestCase; tc++) {
			int x = in.nextInt();
			int y = in.nextInt();
			int z = in.nextInt();
			int r = in.nextInt();
			int vx = in.nextInt();
			int vy = in.nextInt();
			int vz = in.nextInt();

			x -= in.nextInt();
			y -= in.nextInt();
			z -= in.nextInt();
			r += in.nextInt();
			vx -= in.nextInt();
			vy -= in.nextInt();
			vz -= in.nextInt();

			int a = vx * vx + vy * vy + vz * vz;
			int b = 2 * x * vx + 2 * y * vy + 2 * z * vz;
			int c = x * x + y * y + z * z - r * r;

			int delta = b * b - 4 * a * c;
			if (a == 0) {
				System.out.println("No collision");
			} else if (delta < 0) {
				System.out.println("No collision");
			} else {
				double a1 = Math.min((-b - Math.sqrt(delta)) / (2 * a), (-b + Math.sqrt(delta)) / (2 * a));
				if (a1 < 0)
					System.out.println("No collision");
				else
					System.out.println(a1);
			}
		}
	}

}
