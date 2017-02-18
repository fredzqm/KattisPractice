import java.util.Scanner;

/**
 * https://open.kattis.com/submissions/1482689
 * 
 * P
 * 
 * @author zhang
 *
 */
public class Logo {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int TC = Integer.parseInt(sc.nextLine());
		for (int i = 0; i < TC; i++) {
			int len = Integer.parseInt(sc.nextLine());
			double x = 0, y = 0, theta = 0;
			for (int j = 0; j < len; j++) {
				String line = sc.nextLine();
				String[] command = line.split(" ");
				if (command[0].equals("lt")) {
					theta += Integer.parseInt(command[1]);
				} else if (command[0].equals("rt")) {
					theta -= Integer.parseInt(command[1]);
				} else if (command[0].equals("fd")) {
					int a = Integer.parseInt(command[1]);
					x += a * Math.cos(theta * Math.PI / 180);
					y += a * Math.sin(theta * Math.PI / 180);
				} else if (command[0].equals("bk")) {
					int a = Integer.parseInt(command[1]);
					x -= a * Math.cos(theta * Math.PI / 180);
					y -= a * Math.sin(theta * Math.PI / 180);
				}
			}
			System.out.println(Math.round(Math.sqrt(x * x + y * y)));
		}
	}
}