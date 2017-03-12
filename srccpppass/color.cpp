/*
 * sock.cpp
 *
 *  Created on: Mar 11, 2017
 *      Author: zhang
 */

#include <iostream>
#include <list>
#include <unordered_set>
#include <climits>
#include <cstdlib>
#include <cstdio>

using namespace std;

/*
 http://codeforces.com/contest/782/problem/C

 Tree coloring
 */

class Square {
public:
	list<Square*> children;
	int i = 0;

	void add(Square* s) {
		children.push_back(s);
	}

	void calculate(Square* p, int b) {
		unordered_set<int> adj;
		this->i = b;
		int a = 0;
		if (p != NULL)
			a = p->i;
		int i = 0;
		for (Square* sq : this->children) {
			if (sq != p) {
				i++;
				while (i == a || i == b)
					i++;
				sq->calculate(this, i);
			}
		}
	}
};

void color() {
	int n;
	cin >> n;
	Square s[n];
	for (int i = 0; i < n - 1; i++) {
		int a, b;
		cin >> a >> b;
		a--;
		b--;
		s[a].add(&s[b]);
		s[b].add(&s[a]);
	}
	s[0].calculate(NULL, 1);
	int m = 0;
	for (int i = 0; i < n; i++) {
		m = max(m, s[i].i);
	}
	cout << m << endl;
	for (int i = 0; i < n; i++) {
		cout << s[i].i << " ";
	}
}

//int main() {
//	color();
//}
