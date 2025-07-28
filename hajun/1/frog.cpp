#include <iostream>
#include <vector>
#include <queue>
#include <limits>
using namespace std;

int N;
int INF = numeric_limits<int>::max();
vector<vector<vector<int>>> dist;
vector<vector<char>> map;
int dr[4] = { 0, 1, 0, -1 };
int dc[4] = { 1, 0, -1, 0 };

struct Node {
	pair<int, int> pos;
	int jump_power, time;

	bool operator<(const Node& other) const {
		return time > other.time;
	}
};

int dijkstra(pair<int, int> start, pair<int, int> end) {
	priority_queue<Node> pq;

	dist[start.first][start.second][1] = 0;
	pq.push({ start, 1, 0 });

	while (!pq.empty()) {
		Node here = pq.top();
		int now_row = here.pos.first;
		int now_col = here.pos.second;
		pq.pop();

		if (here.time > dist[now_row][now_col][here.jump_power])
			continue;

		if (here.pos == end)
			return here.time;

		// 1번 행동
		for (int way = 0; way < 4; way++) {
			int next_r = now_row + dr[way] * here.jump_power;
			int next_c = now_col + dc[way] * here.jump_power;

			int next_time = here.time + 1;
			if (next_r >= 1 && next_r <= N && next_c >= 1 && next_c <= N) {
				if (map[next_r][next_c] == 'S')
					continue;

				int path_r = now_row, path_c = now_col;
				bool enemy = false;
				for (int count = 0; count < here.jump_power; count++) {
					path_r += dr[way], path_c += dc[way];
					if (map[path_r][path_c] == '#') {
						enemy = true;
						break;
					}						
				}
				if (enemy)
					continue;

				if (dist[next_r][next_c][here.jump_power] > next_time) {
					dist[next_r][next_c][here.jump_power] = next_time;
					pq.push({ {next_r, next_c}, here.jump_power, next_time });
				}
			}
		}

		//2번 행동
		if (here.jump_power < 5) {
			int next_jump_power = here.jump_power + 1;
			int next_time = here.time + next_jump_power * next_jump_power;
			if (dist[now_row][now_col][next_jump_power] > next_time) {
				dist[now_row][now_col][next_jump_power] = next_time;
				pq.push({ here.pos, next_jump_power, next_time });
			}
		}

		//3번 행동
		if (here.jump_power > 1) {
			int next_time = here.time + 1;
			for (int next_jump_power = 1; next_jump_power < here.jump_power; next_jump_power++) {
				if (dist[now_row][now_col][next_jump_power] > next_time) {
					dist[now_row][now_col][next_jump_power] = next_time;
					pq.push({ here.pos, next_jump_power, next_time });
				}
			}
		}		
	}

	return -1;
}

int main() {
	cin >> N;

	map.resize(N + 1, vector<char>(N + 1, ' '));

	for (int row = 1; row <= N; row++) {
		string input;
		cin >> input;
		for (int col = 1; col <= N; col++) {
			map[row][col] = input[col - 1];
		}
	}

	int Q;
	cin >> Q;

	for (int tc = 0; tc < Q; tc++) {
		dist.assign(N + 1, vector<vector<int>>(N + 1, vector<int>(6, INF)));

		int r1, c1, r2, c2;
		cin >> r1 >> c1 >> r2 >> c2;

		cout << dijkstra({ r1, c1 }, { r2, c2 }) << "\n";
	}
}