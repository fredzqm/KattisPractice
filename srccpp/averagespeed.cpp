#include <iostream>
#include <sstream>
#include <string>
#include <cstring>
#include <cstdio>
#define MAXSIZE 1024

using namespace std;

/**
 * https://open.kattis.com/problems/averagespeed
 *
 * string processing
 */

long getTime(int h, int m, int s) {
	return (h * 60l + m ) * 60 + s;
}


void averageSpeed() {
	int h, m, s;
	char buff[MAXSIZE];
	int speed = 0;
	long time = 0;
	long distance = 0;
	while (true) {
		cin.getline(buff, MAXSIZE);
		if (cin.eof())
			break;
		if (strlen(buff) > 8){
			int nspeed;
		 	sscanf(buff, "%d:%d:%d %d", &h, &m, &s, &nspeed);
		 	long ntime = getTime(h, m, s);
		 	distance += (ntime - time) * speed;
		 	time = ntime;
		 	speed = nspeed;
		} else {
		 	sscanf(buff, "%d:%d:%d", &h, &m, &s);
		 	int ntime = getTime(h, m, s);
		 	printf("%02d:%02d:%02d %.2f km\n", h, m, s, (distance + (ntime - time) * speed) / 3600.0);
		}
	}
}

int main(int argc, char** argv) {
	averageSpeed();
}

