给定一个大小为N*M的迷宫，由通道('.')和墙壁('#')组成，其中通道S表示起点，通道G表示终点，每一步移动可以达到上下左右中不是墙壁的位置。试求出起点到终点的最小步数。（本题假定迷宫是有解的）(N,M<=100)

#S######.#  
......#..#  
.#.##.##.#  
.#.......*  
##.##.####  
....#....#  
.#######.#  
....#....*  
.####.###*  
....#...G#  

这道题目以及解法均来自《挑战程序设计竞赛（第2版）》第34页-36页；

个人觉得这个例题很好地表现了广度优先搜索是如何与队列先进先出（FIFO）的思想联系起来的，通过不断取得某个状态后能够达到的所有状态并将其加入队列， 并且由于队列本身的特性先加入队列的状态总是先得到处理，这样就达到了一个目的：总是先将需要转移次数更少的状态进行分析处理，换句话说就是总是取得了这个状态的树中更接近根部的节点，又或者是总是让搜索树的广度得到尽可能增加。

在这个问题中，找到从起点到终点的最短路径其实就是一个建立队列的过程：

1.从起点开始，先将其加入队列，设置距离为0；

2.从队列首端取出位置，将从这个位置能够到达的位置加入队列，并且让这些位置的距离为上一个位置的距离加上1；

3.循环2直到将终点添加到队列中，这说明我们已经找到了路径；

注意到在这个过程中，每次处理的位置所对应的距离是严格递增的，因此一旦找到终点，当时的距离就是最短距离；

同样基于这个原因，搜索可移动到的位置所使用的判断条件中不仅仅是不碰墙壁、不超过边界，还有一个就是没有到达过，因为如果已经到达了这个位置，这说明已经有更短的路径到达这个位置，这次到达这个位置的路径是更差的，不可能得到更好的最终解。

源代码：
```C++
#include <iostream>
#include <queue>
using namespace std;
const int MAX_N = 100;
const int MAX_M = 100;
const int INF = 0x3f3f3f3f;
typedef pair<int, int> P;
char maze[MAX_N][MAX_M + 1];
int N, M;
int sx, sy; //起点的位置
int gx, gy; //终点的位置
 
int d[MAX_N][MAX_M];//储存起点到某一点的距离
int dx[4] = { 1,0,-1,0 }, dy[4] = { 0,1,0,-1 }; //表明每次x和y方向的位移
 
void bfs()
{
	queue<P> que;
	for (int i = 0; i < N; i++)
		for (int j = 0; j < M; j++)
			d[i][j] = INF;	//初始化所有点的距离为INF
	que.push(P(sx, sy));
	d[sx][sy] = 0;	//从起点出发将距离设为0，并放入队列首端
 
	while (que.size()) //题目保证有路到终点，所以不用担心死循环
	{
		P p = que.front(); que.pop();//弹出队首元素
		int i;
		for (i = 0; i < 4; i++)
		{
			int nx = p.first + dx[i];
			int ny = p.second + dy[i];//移动后的坐标
			//判断可移动且没到过
			if (0 <= nx&&nx < N
				&& 0 <= ny&&ny < M
				&&maze[nx][ny] != '#'
				&&d[nx][ny] == INF)//之前到过的话不用考虑，因为距离在队列中递增，肯定不会获得更好的解
			{
				que.push(P(nx, ny));	//可以移动则设定距离为之前加一，放入队列
				d[nx][ny] = d[p.first][p.second] + 1;
				if(nx==gx && ny==gy) break;
 
                        }
		}
		if(i!=4) break;
	}
 
}
 
int main()
{
	cin>>N>>M;
	for (int i = 0; i < N; i++)
		cin>>maze[i];
	for (int i = 0; i < N; i++)
		for (int j = 0; j < M; j++)
		{
			if (maze[i][j] == 'S')
			{
				sx = i; sy = j;
			}
			if (maze[i][j] == 'G')
			{
				gx = i; gy = j;
			}
		}
	bfs();
	cout<<d[gx][gy]<<endl;
 
	return 0;
}
```