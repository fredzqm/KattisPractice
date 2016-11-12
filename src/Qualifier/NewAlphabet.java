package Qualifier;

import java.util.Scanner;

/**
 * https://open.kattis.com/problems/anewalphabet
 * 
 * P
 * @author zhang
 *
 */
public class NewAlphabet {
	public static Scanner in = new Scanner(System.in);

	static String[] map = new String[]
			{"@", "8", "(", "|)", "3", "#", "6", "[-]", "|", "_|", "|<", "1", "[]\\/[]", "[]\\[]","0", "|D",  "(,)", "|z", "$", "']['", "|_|", "\\/", "\\/\\/" , "}{", "`/", "2"};
	
	public static String getStr(char c) {
		if ('a' <= c && c <= 'z')
			return map[c - 'a'];
		if ('A' <= c && c <= 'Z')
			return map[c - 'A'];
		return ""+c;
	}
	
	public static void main(String[] args) {
		in.hasNextLine();
		String s = in.nextLine();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++){
			sb.append(getStr(s.charAt(i)));
		}
		System.out.println(sb);
	}
}
