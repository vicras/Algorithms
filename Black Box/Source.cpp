#include <iostream>
#include <queue>
#include <fstream>
#include <vector>
#pragma warning(disable:4996);
using namespace std;

class BlackBox {
private:
	priority_queue<int, vector<int>, std::greater<int>> minHeap;
	priority_queue<int> maxHeap;
	int size;

public:
	BlackBox() {
		size = 0;
	}

	int getSize() { return size; }

	void add(int newElement) {
		size++;
		if (minHeap.size()==0||newElement < minHeap.top()) {
			if (maxHeap.empty()||newElement>maxHeap.top())
			{
				minHeap.push(newElement);
				return;
			}
			maxHeap.push(newElement);
			int temp = maxHeap.top();
			maxHeap.pop();
			minHeap.push(temp);
		}
		else {
			minHeap.push(newElement);
		}		
	}

	int take() {
		int	ans = minHeap.top();
		minHeap.pop();
		maxHeap.push(ans);
		return ans;
	}

};
int main() {
	FILE* fin = fopen("input.txt", "r");
	FILE* fout = fopen("output.txt", "w");
	int pushRequest, takeRequest;
	fscanf(fin, "%i", &pushRequest);
	fscanf(fin, "%i", &takeRequest);
	int* con = new int[(pushRequest)];
	BlackBox blackBox;
	for (int i = 0; i < pushRequest; i++) {
		fscanf(fin, "%i", con+i);
	}
	int temp, j = 0;
	for (int i = 0; i < takeRequest; i++) {
		fscanf(fin, "%i", &temp);
		while (temp != blackBox.getSize()) {
			blackBox.add(con[j]);
			j++;
		}
		fprintf(fout, "%i", blackBox.take());
		if (i != takeRequest - 1) fprintf(fout, " ");
	}
	fprintf(fout, "%s\n", "");
	fclose(fout);
	fclose(fin);

}