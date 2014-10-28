package hr.fer.zemris.optjava.dz13;

import java.util.LinkedList;

public class AntVisual extends Ant {

	public LinkedList<Integer> list = null;
	
	public AntVisual(boolean[][] ma,int n) {
		super(ma,n);
		list = new LinkedList<Integer>();
	}
	
	public boolean mov(){
		number_of_moves--;
		if(number_of_moves < 0) {
			return false;
		}
		list.add(1);
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
		list.add(0);
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
		list.add(2);
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
