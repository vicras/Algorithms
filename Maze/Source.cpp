#pragma warning(disable:4996);
#include <iostream>
#include <set>
#include <fstream>
#include <deque>
#include<algorithm>
#include <vector>
#include <unordered_set>
using namespace std;

enum direction {
	LEFT,
	RIGHT,
	UP,
	DOWN
};
direction Direction;

class Pair {
public:
	int i;
	int j;
	direction dirFrom;

	Pair(int ai, int aj) {
		i = ai;
		j = aj;
	}

	Pair() {
		i = 0;
		j = 0;
	}

	Pair(int ai, int aj, direction adirFrom) {
		i = ai;
		j = aj;
		dirFrom = adirFrom;
	}
	
	bool operator != (const Pair& c2)
	{
		return i != c2.i || c2.j != j;
	}

};

bool operator==(const Pair& a, const Pair& b)
{
	if (a.i == b.i) {
		if (b.j == a.j) {
			return true;
		}
	}
	return false;
}

class Cell {
public:
	bool isFree;
	int ans;
	Cell() {
		isFree = false;
		ans = 1;
	}
};


class  GoTo {
public:
	virtual bool Check(vector<vector<Cell>>& con, Pair* i) = 0;
};

class GoToLeft : public GoTo {
public:
	bool Check(vector<vector<Cell>>& con, Pair* i) {
		if (con[i->i][i->j - 1].isFree) {
			i->j--;
			i->dirFrom = RIGHT;
			return true;
		}
		return false;
	}
};

class GoToRight :public GoTo {
public:
	 bool Check(vector<vector<Cell>>& con, Pair* i) {
		if (con[i->i][i->j + 1].isFree) {
			i->j++;
			i->dirFrom = LEFT;
			return true;
		}
		return false;
	}
};

class GoToDown :public GoTo {
public:
	 bool Check(vector<vector<Cell>>& con, Pair* i) {
		if (con[i->i + 1][i->j].isFree) {
			i->i++;
			i->dirFrom = UP;
			return true;
		}
		return false;
	}
};

class GoToUp:public GoTo {
public:
	bool Check(vector<vector<Cell>>& con, Pair* i) {
		if (con[i->i - 1][i->j].isFree) {
			i->i--;
			i->dirFrom = DOWN;
			return true;
		}
		return false;
	}
};
int N, M, K;

namespace std {
	template<>
	struct hash<Pair> {
		inline size_t operator()(const Pair& x) const {
			size_t value = ((x.i * N) + x.j);
			return value;
		}
	};
}

GoTo* objUp[4]{ new GoToLeft(),new GoToDown(), new GoToRight(), new GoToUp() };
GoTo* objDown[4]{ new GoToRight(),new GoToUp(), new GoToLeft(), new GoToDown() };
GoTo* objLeft[4]{ new GoToDown(),new GoToRight(), new GoToUp(), new GoToLeft() };
GoTo* objRight[4]{ new GoToUp(),new GoToLeft(), new GoToDown(), new GoToRight() };

Pair* getNextCell(Pair* next, vector<vector<Cell>>& con) {
	GoTo** obj=nullptr;
	switch (next->dirFrom) {
	case UP:
		obj = objUp;
		break;
	case DOWN:
		obj = objDown;
		break;
	case LEFT:
		obj = objLeft;
		break;
	case RIGHT:
		obj = objRight;
		break;
	}
	for (int i = 0; i < 4; i++) {
		if (obj[i]->Check(con, next)) {
			return next;
		}
	}
	return NULL;
}


static void startSearch(Pair* next, set<int>* outGate, vector<vector<Cell>> &con, int marker, int & count) {
	deque<Pair> way;
	unordered_set<Pair>chek;
	Pair nex(next->i, next->j, next->dirFrom);
	Pair netx;
	way.push_back(nex);
	chek.insert(nex);
	while (next->i != N || outGate->find(next->j) == outGate->end()) {
		next = getNextCell(next, con);
		if (next == NULL) return;
		if (next->i==nex.i&& next->j == nex.j)return;
		netx.i=next->i;
		netx.j=next->j;
		netx.dirFrom = next->dirFrom;

		if (chek.find(netx)!=chek.end())
		{
			while (next->i != way.back().i|| next->j != way.back().j) {
				way.pop_back();
			}
			way.pop_back();
		}

		way.push_back(netx);
		chek.insert(netx);
	}
	Pair temp;
	while (way.size() != 1) {
		temp = way.front();
		way.pop_front();
		con[temp.i][temp.j].ans = marker + 2;
		con[temp.i][temp.j].isFree = false;
	}
	temp = way.front();
	con[temp.i][temp.j].ans = marker + 2;
	count++;
}


int main() {
	int count = 0;
	FILE* file = fopen("input.txt","r");
	FILE* outFile = fopen("output.txt","w");

	fscanf(file,"%i",&N);
	fscanf(file,"%i", &M);
	fscanf(file, "%i", &K);

	vector<vector<Cell>> con((N+2), vector<Cell>(M+2, Cell()));;
	int* inGate = new int[K];
	set<int> *outGate = new set<int>();
	
	for (int i = 0; i < K; i++) {
		fscanf(file, "%i", inGate+i);
	}

	for (int i = 0; i < K; i++) {
		int temp;
		fscanf(file, "%i", &temp);
		outGate->insert(temp);
	}

	for (int i = 1; i <= N; i++) {
		for (int j = 1; j <= M; j++) {
			int temp;
			fscanf(file, "%i", &temp);
			if (temp == 0) {
				con[i][j].isFree = true;
				con[i][j].ans = 0;
			}
			else {
				con[i][j].isFree = false;
				con[i][j].ans = 1;
			}
		}
	}


	for (int i = 0; i < K; i++) {
		Pair* temp = new Pair(1, inGate[i], UP);
		startSearch(temp, outGate, con, i, count);
	}
	

	fprintf( outFile, "%i\n", count);
	for (int i = 1; i <= N; i++) {
		for (int j = 1; j <= M; j++) {
			fprintf(outFile, "%i ", con[i][j].ans);
		}
		fprintf( outFile, "%s\n", "");
	}
	fclose(file);
	fclose(outFile);

};
