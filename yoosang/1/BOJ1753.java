import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class P1753 {
    static int v, e, k;
    static int[] result;
    static List<List<int[]>> graph;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        v = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(br.readLine());

        result = new int[v + 1];
        Arrays.fill(result, -1);

        graph = new ArrayList<>();
        for (int i = 0; i < v + 1; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            graph.get(u).add(new int[]{v, w});
        }
        dijkstra();

        for (int i = 1; i <= v; i++) {
            System.out.println(result[i] >= 0 ? result[i] : "INF");
        }
    }

    static void dijkstra() {
        PriorityQueue<int[]> pq = new PriorityQueue<>(((o1, o2) -> o1[1] - o2[1]));
        // 시작은 k
        // k -> k의 w는 0
        pq.add(new int[]{k, 0});
        result[k] = 0;

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();

            // 가중치가 기존보다 크면 continue;
            if (result[cur[0]] < cur[1]) {
                continue;
            }

            // cur.v에서 연결된 정점 탐색
            for (int i = 0; i < graph.get(cur[0]).size(); i++) {
                int[] next = graph.get(cur[0]).get(i);
                int nextV = next[0];
                int nextW = next[1] + cur[1];

                // 더 작은 값이 나오면 갱신
                // 갱신되면 pq에 다시 넣음
                if (result[nextV] == -1 || result[nextV] > nextW) {
                    result[nextV] = nextW;
                    pq.add(new int[]{nextV, nextW});
                }
            }
        }
    }
}
