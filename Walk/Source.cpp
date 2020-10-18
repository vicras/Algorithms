#include <iostream>
#include <fstream>
#include <vector>

using namespace std;
class Point {
public:
	int x;
	int y;
	
};
bool isConnect(Point aMan,Point aManNext, Point bDog) {
	double r=2*sqrt((int)pow((aMan.x - aManNext.x), 2) + (int)pow((aMan.y - aManNext.y), 2));
	double r2= sqrt((int)pow((aMan.x - bDog.x), 2) + (int)pow((aMan.y - bDog.y), 2))+ 
		sqrt((int)pow((aManNext.x - bDog.x), 2) + (int)pow((aManNext.y - bDog.y), 2));
	return r >= r2;
}

bool try_kuhn(int v, vector<vector<int>>graf, vector <bool> &isVisited, vector<int>& parosochet) {
	if (isVisited[v])  
		return false;
	isVisited[v] = true;
	for (long i = 0; i < graf[v].size(); i++) {
		int to = graf[v][i];
		if (parosochet[to] == -1 || try_kuhn(parosochet[to], graf, isVisited, parosochet)) {
			parosochet[to] = v;
			return true;
		}
	}
	return false;
}

int main() {
	//ifstream fin("input.txt");
	//ofstream fout("output.txt");
	//
	//int numN=0, numM=0;
	//fin >> numN >> numM;
	//Point* pointMan = new Point[numN];
	//Point* pointDog = new Point[numM];
	//vector<vector<int>> graf(numN);
	//vector<int> parosochet(numM, -1);
	//vector<bool> isVisited(numN, false);

	//for (int i = 0; i < numN; i++) {
	//	fin >> pointMan[i].x >> pointMan[i].y;
	//}
	//for (int i = 0; i < numM; i++) {
	//	fin >> pointDog[i].x >> pointDog[i].y;
	//}

	//for (int i = 0; i < numN-1; i++) {
	//	//cout << i << endl;
	//	for (int j = 0; j < numM; j++) {
	//		if (isConnect(pointMan[i], pointMan[i + 1], pointDog[j])) {
	//			graf[i].push_back(j);
	//			//cout << j << " ";
	//		}
	//	}
	//	//cout << endl;
	//}
	vector<vector<int>> graf(4);
	vector<int> parosochet(5, -1);
	vector<bool> isVisited(4, false);
	graf[0].push_back(0);
	graf[0].push_back(1);
	graf[0].push_back(3);

	graf[1].push_back(0);
	graf[1].push_back(1);
	graf[1].push_back(2);

	graf[2].push_back(3);
	graf[2].push_back(4);

	graf[3].push_back(1);

	int counter = 0;
	for (int i = 0; i < 4; i++) {
		isVisited.assign(4, false);
		if (try_kuhn(i, graf, isVisited, parosochet))
			counter++;
	}
	//cout << counter + 4 << " " << counter;
	cout << endl;
	for (int i = 0; i < parosochet.size(); i++) {
		cout << parosochet[i] << " ";
	}

	//int counter = 0;
	//for (int i = 0; i < numN; i++) {
	//	isVisited.assign(numN, false);
	//	if(try_kuhn(i, graf, isVisited,parosochet))
	//		counter++;
	//}
	//fout << counter + numN << " " << counter;
	//cout << endl;
	//for (int i = 0; i < parosochet.size(); i++) {
	//	cout << parosochet[i] << " ";
	//}
}