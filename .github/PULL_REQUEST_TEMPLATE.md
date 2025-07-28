## 🚀 이슈 번호

**Resolve:** {#1}

## 🧩 문제 해결

**스스로 해결:** 🔺

### 🔎 접근 과정

> 
다익스트라 개념이 잘 기억안나 다익스트라 구현에 관해 복습했고, 이를 활용해 문제를 풀었습니다.
heapq를 통해 우선순위큐로 w가 가장 작은 노드를 pop/push에서 log V를 보장했습니다.
(weight, vertex) 페어를 heapq에 넣어 Edge 수만큼 While loop을 돌며, distance를 업데이트해주었습니다.

- 🔹 **어떤 알고리즘을 사용했는지**
다익스트라 사용
- 🔹 **어떤 방식으로 접근했는지**

### ⏱️ 시간 복잡도

> **시간 복잡도 분석을 작성해주세요.**  
> 최악의 경우 수행 시간은 어느 정도인지 분석합니다.

- **Big-O 표기법:** `O(ElogV)`
- **이유:**
While loop이 최대 간선 개수 E 만큼 돌고,
이후 loop 안에서 heappop/heappush가 logV만큼 소모됨.

## 💻 구현 코드

```
# 여기에 구현 코드를 작성하세요.

# ---- 다익스트라 정리 ----
# graph[u]에 (v,w) append
# distance[]는 INF로, 자기자신 V는 0으로 초기화한 뒤 while loop
# loop에서 heapq.heappop으로 뺀 뒤, 업데이트한 경로 예외처리(continue)
# 현재 정점에서 인접 정점 탐색
# 새로운 경로의 weight가 더 작으면 dist 업데이트 후 다음 v q에 push함. (heapq.heappush(q, (new_dist, neighbor)))

import heapq
import math
import sys
input = sys.stdin.readline

V, E = map(int, input().split())
k = int(input())

graph = [[] for _ in range(V+1)]
for _ in range(E):
    u, v, w = map(int, input().split())
    graph[u].append((v, w))
dist = [math.inf]*(V+1)
dist[k] = 0
q = [(0, k)] # w, k 초기화

while q:
    w, k = heapq.heappop(q) 
    if dist[k] > w: # 이미 더 짧은 거리로 업데이트됐으면 패스
        continue
    for nextv, nextw in graph[k]: # k에서 갈 수 있는 간선 체크
        if w+nextw < dist[nextv]:
            dist[nextv] = w+nextw
            heapq.heappush(q, (w+nextw, nextv))
            
for i in range(1, len(dist)):
    if (dist[i] == math.inf):
        print("INF")
    else:
        print(dist[i])


```
