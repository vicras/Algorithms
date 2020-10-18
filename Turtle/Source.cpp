#include <iostream>
#include <fstream>
using namespace std;
int main() {
	ifstream fin("input.txt");
	ofstream fout("output.txt");
	int I, J;
	fin >> I >> J;
	int **dp = new int*[I];
	
	for (int i = 0; i < I; i++) {
		dp[i] = new int[2]();
	}
	int ans = 0;

	for (int i = 0; i < I; i++) {
		dp[i][0] = 1;
	}
	dp[I - 1][1] = 1;

	if (I == 1 || J == 1) {
		fout<<"1"<<endl;
	}
	else {
		for (int j = 0; j < J; j++) {
			if (j % 2 != 0) {
				for (int i = I - 2; i >= 0; i--) {
					dp[i][1] = (dp[i + 1][1] + dp[i][0]) % 1000000007;
				}
				ans = dp[0][1];
			}
			else {
				for (int i = I - 2; i >= 0; i--) {
					dp[i][0] = (dp[i + 1][0] + dp[i][1]) % 1000000007;
				}
				ans = dp[0][0];
			}
		}
		fout<<ans % 1000000007<<endl;
	}

	fin.close();
	fout.close();
}