#include <iostream>
#include<vector>
#include<fstream>
#include<queue>
#include <algorithm>
using namespace std;
int FindTime(vector<vector<int>>* con, int element, int N) {
	vector<bool> isVisited;
	for (int i = 0; i < N; i++) {
		isVisited.push_back(false);
	}
	queue<int> queue;
	queue.push(element);
	isVisited[element]= true;
	int ansTime = -1;
	while (!queue.empty()) {
		ansTime++;
		int sliceSize = queue.size();
		for (int i = 0; i < sliceSize; i++) {
			int temp = queue.front();
			queue.pop();
			for (int j = 0; j < con->at(temp).size(); j++) {
				if (!isVisited[con->at(temp).at(j)]) {
					queue.push(con->at(temp).at(j));
					isVisited[con->at(temp).at(j)]= true;
				}
			}
		}
	}

	if (find(isVisited.begin(), isVisited.end(),false)!=isVisited.end()) {
		return -1;
	}
	else {
		return ansTime;
	}

}

void main(){
		fstream fin("in.txt");
		ofstream fout("out.txt");
		int N;
		fin >> N;
		vector<vector<int>> *con = new vector<vector<int>>(N);

		for (int i = 0; i < N; i++) {
			int J;
			fin >> J;
			for (int j = 0; j < J; j++) {
				int t;
				fin >> t;
				t--;
				con->at(i).push_back(t);
			}
		}
		int ansTime = -1;
		int ansEl = -1;
		for (int i = 0; i < N; i++) {
			int tTime = FindTime(con, i,N);
			if (tTime >= ansTime) {
				ansTime = tTime;
				ansEl = i;
			}
		}
		if (ansTime == -1) {
			fout<<("impossible");
		}
		else {
			fout << ansTime << endl;
			fout << (ansEl + 1) << endl;
		}

		fout.close();
		fin.close();
	
}

	

