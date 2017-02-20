import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

public class TextProcessorTest {

	 @Test
	public void test() {
		runRandomTest(10, 3, 3);
		runRandomTest(10, 7, 5);
		runRandomTest(10, 9, 5);
		runRandomTest(10, 9, 5);
		runRandomTest(10, 9, 5);
	}

	// @Test
	public void test2() {
		runTest("1153124322251011422692362792219183922393416123837125143216716341639169", 10,
				new int[] { 2, 14, 18, 10, 20, 29, 23, 34, 7, 19 });
	}

	@Test
	public void test3() {
		runTest("bcdbbc", 6, new int[] { 1 });
	}

	private void runRandomTest(int length, int W, int Q) {
		Random r = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++)
			sb.append(r.nextInt(length));

		int[] ques = new int[Q];
		for (int i = 0; i < Q; i++) {
			ques[i] = r.nextInt(length) + 1;
		}
		runTest(sb.toString(), W, ques);
	}

	private void runTest(String str, int W, int[] ques) {
		StringBuilder sb = new StringBuilder();
		sb.append(ques[0]);
		for (int i = 1; i < ques.length; i++)
			sb.append(", " + ques[i]);
		System.out.printf("String: \"%s\"\nW: %d\nques: new int[]{%s}\n\n", str, W, sb);

		long[] found = TextProcessor.solve(str, W, ques);
		for (int qi = 0; qi < ques.length; qi++) {
			int start = ques[qi] - 1;
			int end = start + W;
			start = Math.max(start, 0);
			end = Math.min(end, str.length());

			Set<String> set = new HashSet<>();
			for (int i = start; i < end; i++) {
				for (int j = i + 1; j <= end; j++) {
					set.add(str.substring(i, j));
				}
			}
			printSet(set);
			assertEquals("from: " + start + " to: " + end + " string: " + str.substring(start, end), set.size(),
					found[qi]);
		}
	}

	private void printSet(Set<String> set) {
		ArrayList<String> ls = new ArrayList<>(set);
		Collections.sort(ls);
		System.out.println(ls);
	}

}
