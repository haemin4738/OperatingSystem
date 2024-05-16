/*
 * 간트 차트 출력 클래스
 */
package application;

import core.Core;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PrintGanttChart {
	ProcessColor processcoler = new ProcessColor();
	VBox v = new VBox();
	
	public void print(Core[] Corelist, ScrollPane GanttChart, HBox hBox, int time) {
		Label sec = new Label(time + 1 + "");
		sec.setStyle("-fx-font-size: 15px; -fx-font-family: \"Arial\"; -fx-pref-width: 60px; -fx-pref-height: 21px; -fx-alignment: TOP_RIGHT;");
		v.getChildren().add(sec);
		for(int i = 0; i < 4; i++) {
			if(Corelist[i] != null) {
				if(Corelist[i].getProcess() != null) {
					String PID = Corelist[i].getProcess().getProcessName();
					int num = Integer.parseInt(PID.replaceAll("[^0-9]", "")) - 1; // 프로세스 이름에서 숫자만 가져와서 1뺌. 인덱스에 접근하기 위해
					String Pcolor = processcoler.getColor(num);
					Label pid = new Label(PID + " ");
					pid.setStyle("-fx-font-size: 20px; -fx-border-color: black; "
							+ "-fx-pref-width: 60px; -fx-pref-height: 60px; "
							+ "-fx-alignment: CENTER; -fx-background-color: " + Pcolor + ";");
	    			v.getChildren().add(pid);
	    		}
	    		else {
	    			Label idle = new Label(" ");
	    			idle.setStyle("-fx-font-size: 20px; -fx-border-color: black; "
	    					+ "-fx-pref-width: 60px; -fx-pref-height: 60px; -fx-alignment: CENTER;");
	    			v.getChildren().add(idle);
	    		}
			}
			else {
				Label idle = new Label(" "); // 코어 Off시 빈칸
				idle.setStyle("-fx-font-size: 20px; -fx-font-color: white;"
						+ "-fx-pref-width: 60px; -fx-pref-height: 60px; -fx-alignment: CENTER;");
				v.getChildren().add(idle);
			}
		}
	
        hBox.getChildren().add(v);
        GanttChart.hvalueProperty().bind(hBox.widthProperty()); // 수평 스크롤바 갱신
        GanttChart.setContent(hBox);
	}
}
