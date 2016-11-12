package Qualifier;

import java.util.Scanner;

/**
 * https://open.kattis.com/problems/robotopia
 * 
 * @author zhang
 *
 */
public class Robotopia {

	public static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) {
		in.hasNextLine();
		String s = in.nextLine();
		int l = s.length();
		if (l <= 7){
			int x = Integer.parseInt(s);
			int num = 1;
			for (int i = 1 ; i <= 10;i++){
				num *= i;
				if (num == x){
					System.out.println(i);
					return;
				}
			}
		}else{
			double len = 0;
			int i = 1;
			while (len < l){
				i++;
				len += Math.log(i) / Math.log(10);
			}
//			System.out.println(len +" " + i);
			System.out.println(i-1);
		}
	}
	

}
