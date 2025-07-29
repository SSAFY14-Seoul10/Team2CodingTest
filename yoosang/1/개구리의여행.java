import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int n, q;
    static int r1, c1, r2, c2;
    static char[][] map;
    static int[] dx = { 0, 1, 0, -1 };
    static int[] dy = { 1, 0, -1, 0 };
    static int[][][] result;
    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        map = new char[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            String s = br.readLine();
            for (int j = 0; j < n; j++) {
                map[i][j + 1] = s.charAt(j);
            }
        }

        q = Integer.parseInt(br.readLine());
        while (q-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            r1 = Integer.parseInt(st.nextToken());
            c1 = Integer.parseInt(st.nextToken());
            r2 = Integer.parseInt(st.nextToken());
            c2 = Integer.parseInt(st.nextToken());

            PriorityQueue<Node> pq = new PriorityQueue<>();
            result = new int[6][n + 1][n + 1];
            for (int i = 1; i <= 5; i++) {
                for(int j=0;j<=n;j++) {
                    Arrays.fill(result[i][j], INF);
                }
            }
            result[1][r1][c1] = 0;
            pq.add(new Node(r1, c1, 0, 1));
            while (!pq.isEmpty()) {
                Node cur = pq.poll();
                if(result[cur.k][cur.nx][cur.ny]<cur.w) continue;

                for (int d = 0; d < 4; d++) {
                    for (int i = 1; i <= 5; i++) {
                        int nx = cur.nx + dx[d] * i;
                        int ny = cur.ny + dy[d] * i;
                        // 범위 벗어나면
                        if (nx <= 0 || nx > n || ny <= 0 || ny > n)
                            break;
                        // 미끄러운 돌이면
                        if (map[nx][ny] == 'S')
                            continue;
                        // 천적이 있으면 이 좌표 이상 못감
                        if (map[nx][ny] == '#')
                            break;
                        int w = cur.w;

                        if (i < cur.k) {        // 점프력 감소
                            w += 2;	            // 감소에 1, 점프에 1

                        } else if (i == cur.k) {// 점프력 유지
                            w += 1;             // 점프에 1

                        } else {                // 점프력 증가
                            for (int j = cur.k + 1; j <= i; j++) {
                                w += j * j;     // 점프력이 k로 증가하면 k^2 만큼 증가
                            }
                            w += 1;             // 점프에 1
                        }
                        if(result[i][nx][ny] > w) {
                            result[i][nx][ny] = w;
                            pq.add(new Node(nx, ny, w, i));
                        }

                    }
                }

            }
            int min = INF;
            for(int i=1;i<=5;i++){
                min = Math.min(result[i][r2][c2],min);
            }
            if(min==INF) min = -1;
            bw.write(String.valueOf(min));
            bw.write("\n");
            bw.flush();
        }
    }

    static class Node implements Comparable<Node> {
        int nx;
        int ny;
        int w;
        int k;

        public Node(int nx, int ny, int w, int k) {
            this.nx = nx;
            this.ny = ny;
            this.w = w;
            this.k = k;
        }

        @Override
        public int compareTo(Node o) {
            // TODO Auto-generated method stub
            return this.w - o.w;
        }
    }
}

