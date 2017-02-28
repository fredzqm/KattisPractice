#include <iostream>
#include <string>
#include <unordered_map>
#include <unordered_set>

using namespace std;

bool checkSeg(int seg, const string& str, const int* const next,
		const int len) {
	unordered_set<char> checked;
	for (int i = 0; i < seg; i++) {
		char c = str[i];
		if (checked.count(c))
			continue;
		int x = i, rootOccur = 1;
		while (x < seg) {
			x = next[x];
			rootOccur++;
		}
		for (int j = seg * 2; j <= len; j += seg) {
			int occur = 1;
			while (x < j) {
				x = next[x];
				occur++;
			}
			if (occur != rootOccur)
				return false;
		}
		checked.insert(c);
	}
	return true;
}

template<class K, class V>
std::ostream& std::operator<<(ostream &strm, const unordered_map<K, V> &a) {
	for (auto it = a.begin(); it != a.end(); ++it) {
		strm << " " << it->first << ":" << it->second;
	}
	return strm;
}

void multigram() {
	string str;
	getline(cin, str);
	int len = str.length();
	int next[len];
	unordered_map<char, int> map;
	for (int i = len - 1; i >= 0; i--) {
		char c = str[i];
		if (map.count(c)) {
			next[i] = map[c];
		} else {
			next[i] = len;
		}
		map[c] = i;
	}
	for (int seg = 1; seg <= len / 2; seg++) {
		if (len % seg != 0)
			continue;
		if (checkSeg(seg, str, next, len)) {
			cout << str.substr(0, seg) << endl;
			return;
		}
	}
	cout << -1 << endl;
}

int main(int argc, char** argv) {
	multigram();
}

