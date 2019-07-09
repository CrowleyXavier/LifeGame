package game;

import java.util.ArrayList;
import java.util.List;

public class BoardModel {

	private int cols;						//セルの列を表す
	private int rows;						//セルの行を表す
	private boolean[][] cells;				//現在のセルの状態を表す
	//履歴保存用
	public List<boolean[][]> history = new ArrayList<boolean[][]>();

	public BoardModel(int c,int r) {
		cols = c;
		rows = r;
		cells=new boolean[rows][cols];//(rows,cols)=(y,x)
	}

	public void undo() {
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

	//historyを空にしてcellsを全てfalseにする
	public void NewGame() {
		history.clear();
		for(int r=0;r<rows;r++) {
    		for(int c=0;c<cols;c++) {
    			cells[r][c]=false;
    		}
    	}
	}

	public boolean CellState(int x,int y) {
		if(cells[y][x]==true) {
			return true;
		}else {
			return false;
		}
	}

	//(x,y)で指定されたセルの状態を逆にする
	public void changeCellState(int x,int y) {
		cells[y][x]  = !cells[y][x];
	}

	//(x,y)で指定されたセルの状態をtrueにする
	public void trueCellState(int x, int y) {
		cells[y][x]=true;
	}

	//現在の世代の状態をhistoryに入れる
	private void AddNowCellToHistory() {
		boolean[][] save = new boolean[rows][cols];

		for(int r = 0;r<rows;r++) {
			for(int c = 0;c<cols;c++) {
				if(cells[r][c]==true) {
					save[r][c]=true;
				}else {
					save[r][c]=false;
				}
			}
		}
		//盤面を保存
		history.add(save);
	}

	//進行処理
	public void next() {
		AddNowCellToHistory();

		for(int r = 0;r<rows;r++) {			//rowsは行
			for(int c = 0;c<cols;c++) {		//colsは列
				int sum_cell_alive = 0;
				for(int i = -1;i<=1;i++) {
					for(int n = -1;n<=1;n++) {
						if(i==0&&n==0) {
							continue;
							}
						if(r+i == -1 || r+i == rows || c+n == -1 || c+n == cols ) {
							continue;
							}
						if(cells[r+i][c+n]==true ) {
							sum_cell_alive ++;
							}
					}
				}

				//誕生条件:死んでいるセルの周囲8セルに3つ生きているセルがある
				if(sum_cell_alive==3 && cells[r][c]==false) {
					cells[r][c]= true;
					}
				//死亡条件：生きているセルの周囲8セルに生きているセルが1個以下または4個以上
				else if(sum_cell_alive<2 || sum_cell_alive>3 ){
					if(cells[r][c]==true) {
					 cells[r][c]=false;
					}
				}
			}
		}
	}

}