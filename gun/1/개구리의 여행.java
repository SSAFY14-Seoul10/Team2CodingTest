import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class frog_journey {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static int N, Q;
    static char[][] map;
    static int[][] plan;

    public static void main(String[] args) throws IOException {
        init(); // 입력
        for (int i = 0; i < Q; i++) {
            System.out.println(solve(i));
        }
    }

    static int solve(int journey_idx) {
        // 시작점으로 초기화
        PriorityQueue<State> que = new PriorityQueue<>();
        que.offer(new State(0, plan[journey_idx][0], plan[journey_idx][1], 1));
        boolean[][][] visited = new boolean[N + 1][N + 1][6];

        // 다익스트라 시작
        while (!que.isEmpty()) {
            State current = que.poll();
            // 도착했을 때
            if (current.r == plan[journey_idx][2] && current.c == plan[journey_idx][3])
                return current.t;

            if (visited[current.r][current.c][current.power]) continue;

            visited[current.r][current.c][current.power] = true;

            // 점프력 증가
            if (current.power < 5) {
                int newPower = current.power + 1;
                int timeForIncrease = newPower * newPower;
                que.offer(new State(current.t + timeForIncrease, current.r, current.c, newPower));
            }

            // 점프력 감소
            for (int newPower = 1; newPower < current.power; newPower++) {
                que.offer(new State(current.t + 1, current.r, current.c, newPower));
            }

            // 점프
            for (int i = 0; i < 4; i++) {
                int nr = current.r + dir[i][0] * current.power;
                int nc = current.c + dir[i][1] * current.power;
                if (!valid(current.r, current.c, nr, nc)) continue;
                if (map[nr][nc] == '.')
                    que.offer(new State(current.t + 1, nr, nc, current.power));
            }
        }
        return -1;
    }
    static void init() throws IOException {
        N = Integer.parseInt(br.readLine());
        map = new char[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            String line = br.readLine();
            for (int j = 1; j <= N; j++) {
                map[i][j] = line.charAt(j - 1);
            }
        }
        Q = Integer.parseInt(br.readLine());
        plan = new int[Q][4];
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 4; j++) {
                plan[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }
    static class State implements Comparable<State> {
        int t, r, c, power;

        public State(int t, int r, int c, int power) {
            this.t = t;
            this.r = r;
            this.c = c;
            this.power = power;
        }

        @Override
        public int compareTo(State o) {
            return this.t - o.t;
        }
    }
    static boolean valid(int r, int c, int nr, int nc) {
        if (nr < 1 || nr > N || nc < 1 || nc > N) return false;
        if (r == nr) {
            int from = Math.min(c, nc);
            int to = Math.max(c, nc);
            for (int i = from; i <= to; i++) {
                if (map[r][i] == '#') return false;
            }
        }
        if (c == nc) {
            int from = Math.min(r, nr);
            int to = Math.max(r, nr);
            for (int i = from; i <= to; i++) {
                if (map[i][c] == '#') return false;
            }
        }
        return true;
    }
}