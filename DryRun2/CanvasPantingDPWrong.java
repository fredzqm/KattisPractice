package DryRun2;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class CanvasPantingDPWrong {
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		int numOfCase = in.nextInt();
		for (int tc = 0; tc < numOfCase; tc++) {
			int numPlate = in.nextInt();
			int[] a = new int[numPlate];
			int[] b = new int[numPlate + 1];
			for (int i = 0; i < numPlate; i++) {
				a[i] = in.nextInt();
			}
			Arrays.sort(a);
			if (numPlate == 1) {
				System.out.println(a[0]);
			} else {
				for (int i = 1; i < numPlate + 1; i++) {
					b[i] = b[i - 1] + a[i - 1];
				}
				int[][] sol = new int[numPlate][numPlate];
				for (int len = 1; len < numPlate; len++) {
					for (int x = 0; x < numPlate - len; x++) {
						int y = x + len;
						int min = Integer.MAX_VALUE;
						for (int i = x; i < y; i++) {
							min = Math.min(sol[x][i] + sol[i + 1][y], min);
						}
						sol[x][y] = min + b[y + 1] - b[x];
					}
				}
				System.out.println(sol[0][numPlate - 1]);
			}
		}
	}
}
