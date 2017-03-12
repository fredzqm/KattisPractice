/*
 * sock.cpp
 *
 *  Created on: Mar 11, 2017
 *      Author: zhang
 */

#include <iostream>
#include <unordered_set>
#include <climits>
#include <cstdlib>
#include <cstdio>

using namespace std;
/*
	http://codeforces.com/contest/782/problem/B
	
	binary search
*/

bool ok(int* x, int* v, int n, double time) {
	double low = INT_MIN;
	double upp = INT_MAX;
	for (int i = 0; i < n; i++) {
		double a = x[i] - time * v[i];
		double b = x[i] + time * v[i];
		low = max(low, a);
		upp = min(upp, b);
		if (low > upp)
			return false;
	}
	return true;
}

void meet() {
	int n;
	cin >> n;
	int x[n];
	int v[n];
	for (int i = 0; i < n; i++) {
		cin >> x[i];		
	}
	double i = 0, j = 0;
	for (int i = 0; i < n; i++) {
		cin >> v[i];
		double t = (abs(x[0] - x[i]) + 0.0) / v[i];
		j = max(j, t);	
	}
	while (j - i > 0.000001) {
		double mid = (i + j) / 2;
		if (ok(x, v, n, mid)) {
			j = mid;
		} else {
			i = mid;
		}
	}
	printf("%0.6f", j);
}

// int main() {
// 	meet();
// }
