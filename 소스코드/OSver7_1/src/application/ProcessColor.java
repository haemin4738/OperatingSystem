/*
 * 프로세스 컬러클래스
 * 
 */

package application;

public class ProcessColor {
	String[] color = new String[15];
	
	public ProcessColor() {
		color[0] = "CYAN";
		color[1] = "KHAKI";
		color[2] = "LAVENDER";
		color[3] = "LAVENDERBLUSH";
		color[4] = "LAWNGREEN";
		color[5] = "LEMONCHIFFON";
		color[6] = "LIGHTBLUE";
		color[7] = "LIGHTCORAL";
		color[8] = "LIGHTCYAN";
		color[9] = "OLDLACE";
		color[10] = "OLIVE";
		color[11] = "OLIVEDRAB";
		color[12] = "ORANGE";
		color[13] = "ORANGERED";
		color[14] = "ORCHID";
	}
	
	public String getColor(int idx) {
		return color[idx];
	}
}