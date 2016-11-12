package DryRun2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 * https://open.kattis.com/problems/promotions
 * 
 * P
 * @author zhang
 *
 */
public class Promotions {

	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		in.hasNext();
		int a = in.nextInt();
		int b = in.nextInt();
		int e = in.nextInt();
		int p = in.nextInt();

		List<Employee> emps = new ArrayList<>();
		for (int i = 0; i < e; i++)
			emps.add(new Employee(i));
		for (int i = 0; i < p; i++) {
			Employee x = emps.get(in.nextInt());
			Employee y = emps.get(in.nextInt());
			x.isBefore(y);
			y.isAfter(x);
		}

		for (Employee x : emps) {
			x.findBefore();
			x.findAfter();
		}

		int a_will = 0;
		int b_will = 0;
		int b_not = 0;
		for (Employee x : emps) {
			int before = x.before.size();
			int after = x.after.size();
			if (a >= e - after)
				a_will++;
			if (b >= e - after)
				b_will++;
			if (b <= before)
				b_not++;
		}
		System.out.println(a_will);
		System.out.println(b_will);
		System.out.println(b_not);
	}

	static class Employee {
		int index;
		Set<Employee> before;
		Set<Employee> after;
		ArrayList<Employee> better = new ArrayList<>();
		ArrayList<Employee> worse = new ArrayList<>();

		public Employee(int i) {
			index = i;
		}

		public Collection<Employee> findAfter() {
			if (after == null) {
				after = new HashSet<>(worse);
				for (Employee x : worse) {
					after.addAll(x.findAfter());
				}
			}
			return after;
		}

		public Collection<Employee> findBefore() {
			if (before == null) {
				before = new HashSet<>(better);
				for (Employee x : better) {
					before.addAll(x.findBefore());
				}
			}
			return before;
		}

		public void isAfter(Employee x) {
			better.add(x);
		}

		public void isBefore(Employee y) {
			worse.add(y);
		}

		@Override
		public String toString() {
			return "" + index;
		}
	}

}
