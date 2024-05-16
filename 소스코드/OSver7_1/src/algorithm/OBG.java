/*
 * 우리조 스케줄링
 * One By Group
 * 그룹중 하나만 코어에 존재 할 수 있습니다.
 * 그 하나가 종료 되면 그룹에 있는 다른 하나가 어떤 코어에 들어갈 권한이 생김
 * 
 */
package algorithm;

import java.util.LinkedList;

import application.PrintGanttChart;
import application.Process;
import core.Core;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class OBG {
	int time = 0; // 실시간
	Timeline timeline; // 정해진 시간마다 주어진 코드를 실행하게 해주는 클래스 변수

	public OBG(Timeline timeline) {
		this.timeline = timeline;
	}

	public void run(Core[] coreList, LinkedList<Process> waitingList, LinkedList<Process> endList,
			ObservableList<Process> obser, ObservableList<Process> observableList, ScrollPane GanttChart, HBox hBox,
			Text[] corePower, Text Avgresponsetime, TableView<Process> tableView2, Button[] Buttons,ChoiceBox<String> choicebox) {

		timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
			// 입력받은 프로세스의 AT가 현재 시간이 되면 waitingList에 삽입
			for (Process p : obser) {
				if (p.getArrivalTime() == time)
					waitingList.add(p);
			}

			// 남은 코어 자리에 waitingList에 있는 프로세스들을 번호 순서대로 할당함

			for (int index = 0; index < 4; ++index) {
				if (coreList[index] != null && !coreList[index].isVisited() && !waitingList.isEmpty()) { // 코어리스트 중 코어가
																											// OFF가 아니거나
																											// 미사용중이면

					for (int i = 0; i < waitingList.size(); ++i) {
						boolean canInsert = true;
						Process p = waitingList.poll();

						// 실행중인 프로세스 중에서 GN이 같은게 있으면 waitingList 가장 뒤에 배치
						for (int j = 0; j < 4; ++j) {
							if (coreList[j] != null && coreList[j].isVisited()) {
								if (coreList[j].getProcess().getgroupnumber().equals(p.getgroupnumber())) {
									waitingList.add(p);
									canInsert = false;
									break;
								}
							}
						}

						// GN이 겹치는 프로세스가 없어서 프로세스를 코어에 할당할 수 있는 경우 할당함
						if (canInsert) {
							coreList[index].setProcess(p);
							coreList[index].setVisited(true);
							break;
						}
					}
				}
			}

			// 간트차트 출력
			PrintGanttChart Gantt = new PrintGanttChart();
			Gantt.print(coreList, GanttChart, hBox, time);

			// 코어별로 1초만큼의 일을 함.
			// BT만큼의 작업을 다하면 작업이 완료되고, 작업이 완료되면 end큐에 넣음
			for (int index = 0; index < 4; ++index) {
				if (coreList[index] != null && coreList[index].isVisited()) {
					boolean done = coreList[index].work(time);
					if (done)
						endList.add(coreList[index].getProcess());
				} else if (coreList[index] != null)
					coreList[index].setprevUsed(false); // 프로세스가 할당되지 않은 코어는 prevUsed를 false로 설정
			}

			// 코어별 소비전력 출력
			for (int index = 0; index < 4; index++) {
				if (coreList[index] != null) {
					double coreP = Math.round(coreList[index].getPowerConsumption() * 10) / 10.0;
					corePower[index].setText(String.valueOf(coreP + "W"));
				}
			}

			// 실시간 1초 증가
			time++;

			// 처음에 입력된 프로세스의 수와 완료된 프로세스의 수가 같으면 종료
			if (endList.size() == observableList.size()) {
				// 결과 테이블 출력
				tableView2.setItems(obser);

				// Average response time
				int sum = 0;
				for (Process p : obser) {
					sum += p.getturnaroundTime();
				}
				double AvgResTime = Math.round((double) sum / obser.size() * 100) / 100.0;
				Avgresponsetime.setText(String.valueOf(AvgResTime) + "s");

				Buttons[0].setDisable(false);
				Buttons[1].setDisable(false);
				Buttons[2].setDisable(false);
				Buttons[3].setDisable(false);
				choicebox.setDisable(false);
				// time line 정지
				timeline.stop();
			}
		}));

		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
}
