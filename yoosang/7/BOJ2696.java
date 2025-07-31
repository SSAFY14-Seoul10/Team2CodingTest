import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int t;
	static int m;

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		t = Integer.parseInt(br.readLine());
		while (t-- > 0) {
			m = Integer.parseInt(br.readLine());
			Queue<Integer> result = new LinkedList<>();
			Queue<Integer> temp = new LinkedList<>();
			PriorityQueue<Integer> pq = new PriorityQueue<>();

			StringTokenizer st = null;
			for (int i = 0; i < m; i++) {
				if (i % 10 == 0)
					st = new StringTokenizer(br.readLine());
				pq.add(Integer.parseInt(st.nextToken()));
				if (i % 2 == 0) {
					for (int j = 0; j < i / 2; j++) {
						temp.offer(pq.poll());
					}
					int num = pq.poll();
					result.offer(num);
					pq.offer(num);
					while (!temp.isEmpty()) {
						pq.offer(temp.poll());
					}
				}
			}
			bw.write(String.valueOf(result.size()));
			bw.write("\n");
			int idx = 0;
			while (!result.isEmpty()) {
				bw.write(String.valueOf(result.poll()));
				bw.write(" ");
				if (++idx == 10) {
					bw.write("\n");
					idx = 0;
				}
			}
			bw.write("\n");
		}
		bw.flush();

		br.close();
		bw.close();
	}
}