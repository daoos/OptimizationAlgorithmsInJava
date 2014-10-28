package hr.fer.zemris.optjava.dz13;

public class Ant {

	public int x,y,field_width,field_heigth,number_of_moves,up=0,right=1,down=2,left=3;
	public int orientation;
	public boolean[][] map = null;
	public int number_of_collected_food=0;
	public boolean see_food=false;
	
	public Ant(boolean[][] ma,int n) {
		x = 0;
		y = 0;
		number_of_moves = n;
		map = new boolean[ma.length][ma[0].length];
		for(int i = 0;i < ma.length;i++) {
			for(int j = 0; j < ma[0].length;j++) {
				map[i][j] = ma[i][j];
			}
		} 
		field_width = map[0].length;
		field_heigth = map.length;
		orientation = right;
		if(map[0][0]) {
			number_of_collected_food++;
		}
		see_food = map[0][1];
	}
	
	public boolean mov(){
		number_of_moves--;
		if(number_of_moves < 0) {
			return false;
		}
		if( orientation == right ) {
			y++;
			if (y >= field_width ) {
				y -= field_width;
			}
			if(map[x][y]) {
				number_of_collected_food++;
				map[x][y] = false;
			}
			if(y+1 < field_width) {
				see_food = map[x][y+1];
			}else {
				see_food = map[x][0];
			}
			return true;
		}
		if( orientation == left ) {
			y--;
			if( y < 0 ) {
				y += field_width;
			}
			if(map[x][y]) {
				number_of_collected_food++;
				map[x][y] = false;
			}
			if( y - 1 >= 0) {
				see_food = map[x][y-1];
			}else {
				see_food = map[x][field_width-1];
			}
			return true;
		}
		if( orientation == down ) {
			x++;
			if( x >= field_heigth) {
				x -=field_heigth;
			}
			if(map[x][y] == true) {
				number_of_collected_food++;
				map[x][y] = false;
			}
			if(x+1 < field_heigth) {
				see_food = map[x+1][y];
			}else {
				see_food = map[0][y];
			}
			return true;
		}
		if( orientation == up ) {
			x--;
			if( x < 0 ) {
				x += field_heigth;
			}
			if(map[x][y]) {
				number_of_collected_food++;
				map[x][y] = false;
			}
			if(x-1 >= 0) {
				see_food = map[x-1][y];
			}else {
				see_food = map[field_heigth-1][y];
			}
			return true;
		}
		return true;
	}
	
	public void rotate_left(){
		number_of_moves--;
		if(number_of_moves < 0) {
			return;
		}
		if(orientation == right ){
			orientation = up;
			if( x > 0){
				see_food = map[x-1][y];
			}else {
				see_food = map[field_heigth-1][y];
			}
			return;
		}
		if(orientation == left ){
			orientation = down;
			if( x < field_heigth-1){
				see_food = map[x+1][y];
			}else {
				see_food = map[0][y];
			}
			return;
		}
		if(orientation == down ){
			orientation = right;
			if( y < field_width-1){
				see_food = map[x][y+1];
			}else {
				see_food = map[x][0];
			}
			return;
		}
		if(orientation == up ){
			orientation = left;
			if( y > 0){
				see_food = map[x][y-1];
			}else {
				see_food = map[x][field_width-1];
			}
			return;
		}
	}
	
	public void rotate_rigth(){
		number_of_moves--;
		if(number_of_moves < 0) {
			return;
		}
		if( orientation == right ){
			orientation = down;
			if( x < field_heigth-1){
				see_food = map[x+1][y];
			}else {
				see_food = map[0][y];
			}
			return;
		}
		if( orientation == left ){
			orientation = up;
			if( x > 0){
				see_food = map[x-1][y];
			}else {
				see_food = map[field_heigth-1][y];
			}
			return;
		}
		if( orientation == down ){
			orientation = left;
			if( y > 0){
				see_food = map[x][y-1];
			}else {
				see_food = map[x][field_width-1];
			}
			return;
		}
		if( orientation == up ){
			orientation = right;
			if( y < field_width-1){
				see_food = map[x][y+1];
			}else {
				see_food = map[x][0];
			}
			return;
		}
	}

}
