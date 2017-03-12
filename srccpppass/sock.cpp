/*
 * sock.cpp
 *
 *  Created on: Mar 11, 2017
 *      Author: zhang
 */

#include <iostream>
#include <unordered_set>

using namespace std;
/*
	http://codeforces.com/contest/782/problem/B
	
*/

int sock() {
	int n;
	cin >> n;
	unordered_set<int> set;
	int max = 0;
	for (int i = 0; i < 2*n; i++) {
		int x;
		cin >> x;
		if (set.count(x)) {
			set.erase(x);
		} else {
			set.insert(x);
			if (set.size() > max)
				max = set.size();
		}
	}
	cout << max << endl;
}

// int main() {
// 	sock();
// }
