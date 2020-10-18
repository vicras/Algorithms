#include <iostream>
#include <fstream>
#include <algorithm>
#include <vector>
#include<random>
using namespace std;
vector<int>* findLIS(int* con, int size, bool isStraight, int* countLen, int* aprev);
int main() {
	setlocale(LC_ALL, "ru");
	ifstream fin("report.in");
	ofstream fout("report.out");
	/*srand(time(0));
	int lenght = rand() % 50 + 10;
	bool test = true;
	while (test) {
		cout << "* ";

		int* t = new int[lenght];
		for (int i = 0; i < lenght; i++) {
			t[i] = rand() % 150 + 2;
		}*/
	int size;
	fin >> size;
	int* con = new int[size];

	int* countUp = new int[size];
	int* prevUp = new int[size];
	int* countDown = new int[size];
	int* prevDown = new int[size];
	fill(prevUp, prevUp + size, -1);
	fill(prevDown, prevDown + size, -1);
	fill(countDown, countDown + size, 1);
	fill(countUp, countUp + size, 1);

	for (int i = 0; i < size; i++) {
		fin >> con[i];
	}

	findLIS(con, size, true, countUp, prevUp);
	reverse(con, con + size);
	findLIS(con, size, false, countDown, prevDown);
	int maxDays = 0, it=0;
	for (int i = 0; i < size; i++) {
		if (min(countDown[i], countUp[i]) > maxDays) {
			maxDays = min(countDown[i], countUp[i]);
			it = i;
		}
	}
	maxDays--;
	vector<int> ans;
	int temp = it;
	ans.push_back(it + 1);
	for (int i = 0; i < size; i++) {
		cout << prevUp[i] << " ";
	}
	cout << endl;
	for (int i = 0; i < size; i++) {
		cout << prevDown[i] << " ";
	}

	for (int i = 0; i < maxDays; i++) {
		temp = prevUp[temp];
		ans.push_back(temp+1);
	}
	reverse(ans.begin(), ans.end());
 	temp = it;
	for (int i = 0; i < maxDays; i++) {
		ans.push_back(size - prevDown[temp]);
		temp = size -1 -prevDown[temp];
	}
	cout << "\n**********************\n";
	fout << maxDays << endl;
	for (int i = 0; i < 2 * maxDays + 1; i++) {
		fout << ans[i]<<" ";
	}
	fout << endl;
	fout.close();
	fin.close();

	/*cout << maxDays << endl;
	for (int i = 0; i < lenght; i++)
		cout << con[i] << " ";
	cout << "\nlen\n" << lenght << endl;
	cout << endl;
	for (int i = 0; i < answer.size(); i++) {
		cout << ans[i] << " ";

	}
	cout << "\n*****************************************";
	cout << endl;
	for (int i = 0; i < size; i++) {
		cout << prevDown[i] << " ";
	}
	cout << "\n*****************************************";
	system("pause");*/
}


	vector<int>* findLIS(int* con, int size, bool isStraight, int* countLen, int* aprev) {
		int* dp = new int[size + 1];
		int* apos = new int[size + 1]();
		apos[0] = -1;
		dp[0] = INT32_MIN;
		for (int i = 1; i < size + 1; i++) {
			dp[i] = INT32_MAX;
		}
		for (int i = 0; i < size; i++) {
			int j = (upper_bound(dp, dp + size + 1, con[i]) - dp);
			if (dp[j - 1] < con[i] && con[i] < dp[j]) {
				dp[j] = con[i];
				apos[j] = i;
				countLen[i] = j;
				aprev[i] = apos[j - 1];
			}
			else {
				if (dp[j - 1] == con[i]) {
					countLen[i] = j - 1;
					aprev[i] = apos[j - 2];;
				}
				else {
					countLen[i] = 1;
					aprev[i] = -1;
				}
			}
		}
		if (!isStraight) {
			reverse(countLen, countLen + size);
			reverse(aprev, aprev + size);
		}
		vector<int>* answer = new vector<int>;
		/*int p = pos[length];
		while (p != -1) {
			if (isStraight) {
				answer->push_back(p + 1);
				hieght.push_back(a[p]);
				p = prev[p];
			}
			else {
				answer->push_back(size-(p));
				hieght.push_back(a[p]);
				p = prev[p];
			}
		}
		if (isStraight) {
			reverse(answer->begin(), answer->end());
			reverse(hieght.begin(), hieght.end());
		}
		else {

		}*/
		return answer;
	}


/*
12
1 500 450 400 350 300 250 200 150 100 70 50
50 70 100 150 200 250 300 350 400 450 500 1

*/