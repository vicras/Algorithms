#include <iostream>
#include <fstream>
#include <stack>
using namespace std;
void main() {
	ifstream fin("input.txt");
	ofstream fout("output.txt");
	stack<long long> ans;
	long long con, sum;
	fin >> con;
	if (con == 0) {
		cout << -1 << endl;
		return;
	}
	for (int i = 0; con > 0; i++) {
		if (con % 2 == 1) {
			fout << i<<endl;
		}
		con /= 2;
	}
	fout.close();
}



