#include <iostream>
#include <vector>
#include <queue>

using namespace std;

int v, e, k;
vector<pair<int, int>> adj[20005]; // 비용, 정점 번호
const int INF = 0x3f3f3f3f;
int d[20005]; // 최단 거리 테이블

void reset() {
	for (int i = 0; i < 20005; i++)
	{
		d[i] = INF;
	}
}

void input() {
	cin >> v >> e >> k;
	for (int i = 0; i < e; i++)
	{
		int u, v, w;
		cin >> u >> v >> w;
		adj[u].push_back({ w,v });
	}
}

void solve() {
	priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>> > pq;
	d[k] = 0; //  시작 정점을 0으로 초기화

	pq.push({ d[k],k });

	while (!pq.empty()) {
		auto cur = pq.top(); pq.pop();

		if (d[cur.second] != cur.first) continue;
		// 이전에 들어갔던 더 긴 거리 정보가 아직 큐에 남아있기 때문에 걸러줘야함
		// 이론적으로 < 로 확인해도 되지만, 큐에 잘못된 값이 들어가면 <는 탐지 못하기 때문에 !=로 해야함
		
		for (auto nxt : adj[cur.second]) {
			if (d[nxt.second] <= d[cur.second] + nxt.first) continue;
			d[nxt.second] = d[cur.second] + nxt.first;
			pq.push({ d[nxt.second],nxt.second });
		}
	}
}

void output() {
	for (int i = 1; i <= v; i++) {
		if (d[i] == INF) cout << "INF\n";
		else cout << d[i] << "\n";
	}
}

int main() {
	reset();
	input();
	solve();
	output();
	return 0;
}