package game;

import java.util.ArrayList;
import java.util.List;

public class BoardModel {
	private int cols;
	private int rows;
	private boolean[][] cells,Bcells;
	private ArrayList<BoardListener> listeners;
	public List<boolean[][]> history = new ArrayList<boolean[][]>();



	public BoardModel(int c,int r) {
		cols = c;
		rows = r;
		cells=new boolean[rows][cols];//(rows,cols)=(y,x)
		Bcells = new boolean[rows][cols];
		listeners = new ArrayList<BoardListener>();
	}


	public void undo() {//undoメソッド

		if(history.size()!=0) {
			cells = history.get(history.size()-1);
			history.remove(history.size()-1);
			System.out.println(history.size());
		}
	}
	public boolean isUndoable() {
		if(history.size()!=0) {
			return true;
		}else return false;
	}


	public int getCols() {
		return cols;
	}

	public int getRows() {
		return rows;
	}

	public void printForDebug() {
		for(int r = 0;r<rows;r++) {
			for(int c = 0;c<cols;c++) {
				if(cells[r][c]==false) {
					System.out.print('.');
				}else {
					System.out.print('*');
				}
			}
			System.out.println();
		}
		System.out.println("------------");
	}

	public void changeCellState(int x,int y) {
		//(x,y)で指定されたセルの状態を変更する
		cells[y][x]  = !cells[y][x];
		fireUpdate();
	}

	public void addListener(BoardListener listener) {
		listeners.add(listener);
	}


	public void fireUpdate() {
		for(BoardListener listener: listeners) {
			listener.updated(this);
		}
	}

	public void next() {//進行処理
		//現在の世代の状態を保存		Bcells=cellsだと参照になってしまうためforで回す
		boolean[][] save = new boolean[rows][cols];
		for(int r = 0;r<rows;r++) {
			for(int c = 0;c<cols;c++) {
				if(cells[r][c]==true) {
					Bcells[r][c] = true;
					save[r][c]=true;
				}else {
					Bcells[r][c] = false;
					save[r][c]=false;
				}
			}
		}

		history.add(save);//盤面を保存

		//int psum =0;
		//int msum = 0;
		for(int r = 0;r<rows;r++) {//rowsは行
			for(int c = 0;c<cols;c++) {//colsは列
				int count = 0;
				for(int i = -1;i<=1;i++) {
					for(int n = -1;n<=1;n++) {
						if(i==0&&n==0) {continue;}
						if(r+i == -1 || r+i == rows || c+n == -1 || c+n == cols ) {continue;}
						if(Bcells[r+i][c+n]==true ) {count ++;}

					}
				}
				if(count==3 && cells[r][c]==false) {//誕生条件:死んでいるセルの周囲8セルに3つ生きているセルがある
					cells[r][c]= true;
				//	psum++;
					System.out.println(count);
					}//復活
				if(count<2 || count>=4 ){//死亡条件：生きているセルの周囲8セルに生きているセルが1個以下または4個以上
					if(cells[r][c]==true) {
					 cells[r][c]=false;
				//	 msum ++;
					}
				}
				//System.out.println(count);
			}
		}
		fireUpdate();
		//System.out.println(psum + "---" + msum);
	}

}
