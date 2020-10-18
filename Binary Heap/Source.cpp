#include <iostream>
#include <fstream>

using namespace std;
int main() {
	ifstream fin("input.txt");
	ofstream fout("output.txt");
	int size;
	fin >> size;
	int* con = new int[size + 1];
	for (int i = 1; i < size + 1; i++) {
		fin >> con[i];
	}
	for (int i = 1; i < size/2 + 1; i++) {
		if (i * 2 < size + 1) {
			if (con[i] > con[i * 2])
			{
				fout << "No"<<endl;
				return 0;
			}
		}
		if (i * 2+1 < size + 1) {
			if (con[i] > con[i * 2+1])
			{
				fout << "No" << endl;
				return 0;
			}
		}
	}
	fout << "Yes" << endl;
	fout.close();
	fin.close();
}