package application;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import algorithm.*;
import core.*;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Controller implements Initializable, Cloneable {

	private int processnumber = 1;
	@FXML
	private Button AddButton, ResetButton, RunButton, HelpButton; // 버튼

	@FXML
	private ChoiceBox<String> algorithmBox; // 프로세스 선택박스

	@FXML
	private Text Avgresponsetime; // 평균 응답 시간
	@FXML
	private Text core1Power, core2Power, core3Power, core4Power; // 코어별 소비전력 텍스트

	@FXML
	private TextField atText, btText, tqText, groupText; // 입력 텍스트 필드

	@FXML
	private TableView<Process> tableView;// input 테이블
	@FXML
	private TableColumn<Process, String> Pnames, GroupnumberColumn;
	@FXML
	private TableColumn<Process, Integer> ATColumn, BTColumn;

	@FXML
	private TableView<Process> tableView2;// output 테이블
	@FXML
	private TableColumn<Process, String> nameColumn1, GroupnumberColumn1;
	@FXML
	private TableColumn<Process, Integer> ATColumn1, BTColumn1, WTColumn1, TTColumn1;
	@FXML
	private TableColumn<Process, Double> NTTColumn1;

	@FXML
	private RadioButton core1_off, core1_p, core1_e, core2_off, core2_p, core2_e;
	@FXML
	private RadioButton core3_off, core3_p, core3_e, core4_off, core4_p, core4_e;

	@FXML
	private ScrollPane GanttChart;

	Timeline timeline; // timeline
	ObservableList<Process> observableList = FXCollections.observableArrayList();
	ObservableList<Process> obser = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		algorithmBox.getItems().removeAll(algorithmBox.getItems());
		algorithmBox.getItems().addAll("FCFS", "RR", "SPN", "SRTN", "HRRN", "OBG");

		Avgresponsetime.setText("NaN");
		Text corePower[] = new Text[4];
		corePower[0] = core1Power;
		corePower[1] = core2Power;
		corePower[2] = core3Power;
		corePower[3] = core4Power;

		algorithmBox.getSelectionModel().select("FCFS");// 셀 초기화
		tableView.setItems(observableList);

		Label placeholderLabel = new Label("");
		tableView.setPlaceholder(placeholderLabel);
		tableView2.setPlaceholder(placeholderLabel);

		// input table에 입력된 프로세스 값들을 표시
		Pnames.setCellValueFactory(cellData -> cellData.getValue().processNameProperty());
		ATColumn.setCellValueFactory(cellData -> cellData.getValue().arrivalTimeProperty().asObject());
		BTColumn.setCellValueFactory(cellData -> cellData.getValue().burstTimeProperty().asObject());
		GroupnumberColumn.setCellValueFactory(cellData -> cellData.getValue().groupnumberProperty());
		tableView.setItems(observableList);

		// output table에 스케줄링이 완료된 프로세스 값들을 표시
		nameColumn1.setCellValueFactory(cellData -> cellData.getValue().processNameProperty());
		ATColumn1.setCellValueFactory(cellData -> cellData.getValue().arrivalTimeProperty().asObject());
		BTColumn1.setCellValueFactory(cellData -> cellData.getValue().burstTimeProperty().asObject());
		WTColumn1.setCellValueFactory(cellData -> cellData.getValue().waitingTimeProperty().asObject());
		TTColumn1.setCellValueFactory(cellData -> cellData.getValue().turnaroundTimeProperty().asObject());
		NTTColumn1.setCellValueFactory(cellData -> cellData.getValue().NormalizedTTProperty().asObject());
		GroupnumberColumn1.setCellValueFactory(cellData -> cellData.getValue().groupnumberProperty());

		// TQ, GN 입력칸 비활성화
		tqText.setDisable(true);
		groupText.setDisable(true);
		
		// RR선택 시 TQ입력칸 활성화, OBG선택 시 GN입력칸 활성화
		algorithmBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.equals("RR")) {
				tqText.setDisable(false);
			} else {
				tqText.setDisable(true);
			}
			if (newValue.equals("OBG")) {
				observableList.clear();
				groupText.setDisable(false);
				processnumber = 1;
			} else {
				groupText.setDisable(true);
			}

		});

		AddButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() // 표 데이터 입력 마우스
		{
			@Override
			public void handle(MouseEvent arg0) {
				String strPname, strATtext, strBTtext, strGroupnumber;
				strPname = "P" + processnumber;
				strATtext = atText.getText();// AT
				strBTtext = btText.getText();// BT
				strGroupnumber = groupText.getText();

				if (algorithmBox.getValue().equals("OBG")) {
					if (strGroupnumber == null || strGroupnumber.trim().isEmpty()) {
						warning("empty", "grouptext");
						atText.clear();
						btText.clear();
						groupText.clear();
						return;
					}
				}

				if (strATtext == null || strBTtext == null || strATtext.trim().isEmpty()
						|| strBTtext.trim().isEmpty()) {

					warning("empty", "AT or BT");
					atText.clear();
					btText.clear();
					return;
				} // AT 또는 BT가 비어있을 경우

				if ((!isInteger(strATtext) && !strATtext.equalsIgnoreCase("0"))
						|| (!isInteger(strBTtext) && !strBTtext.equalsIgnoreCase("0"))) // 타임쿼터스가 숫자가 아닐 경우 또는 오버플로우
																						// 언더플로우가 일어날 때
				{

					warning("number", "AT or BT");
					atText.clear();
					btText.clear();
					return;
				} // AT 또는 BT가 숫자가 아닐 경우;

				if (Integer.parseInt(strATtext) < 0 || Integer.parseInt(strBTtext) < 0) {

					warning("mnumber", "AT or BT");
					atText.clear();
					btText.clear();
					return;
				}

				if (observableList.size() < 15) { // 프로세스 15개 제한
					if (algorithmBox.getValue().equals("OBG")) {
						tableView.getItems().add(new Process(strPname, Integer.parseInt(strATtext),
								Integer.parseInt(strBTtext), strGroupnumber));
					} else {
						tableView.getItems()
								.add(new Process(strPname, Integer.parseInt(strATtext), Integer.parseInt(strBTtext)));
					}
					processnumber++;
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning!!");
					alert.setHeaderText("full");
					alert.setContentText("table is full.");
					alert.showAndWait();
				}

				atText.clear();
				btText.clear();
			}

		});

		ResetButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() // 표 초기화
		{
			@Override
			public void handle(MouseEvent arg0) {
				observableList.clear();
				obser.clear();
				GanttChart.setContent(null);
				Avgresponsetime.setText("NaN");
				for (Text t : corePower) {
					t.setText("0.0W");
				}
				processnumber = 1;
			}

		});
		
		// run동작 시 모든 버튼 비활성화
		RunButton.setOnAction(event -> {
			AddButton.setDisable(true);
			ResetButton.setDisable(true);
			RunButton.setDisable(true);
			HelpButton.setDisable(true);
			algorithmBox.setDisable(true);
		});

		RunButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() // 프로세스 실행
		{
			@Override
			public void handle(MouseEvent arg0) {
				
				tableView2.setItems(null);
				obser.clear();// obser 초기화
				Avgresponsetime.setText("NaN");
				if (observableList.isEmpty()) // 프로세스 테이블이 비어있을 경우
				{
					warning("empty", "Process Table");
					return;
				}

				HBox hBox = new HBox(); // 1열씩 출력되는 Box
				LinkedList<Process> waitingList = new LinkedList<>();
				LinkedList<Process> endList = new LinkedList<>();
				String str = algorithmBox.getValue(); // 체크박스에 있는 값 불러오기

				for (Text t : corePower) {
					t.setText("0.0W");
				}

				// 각 코어를 off, Pcore, Ecore 중 하나를 선택함
				Core[] coreList = new Core[4];

				if (core1_off.isSelected())
					coreList[0] = null;
				else if (core1_p.isSelected())
					coreList[0] = new Pcore();
				else
					coreList[0] = new Ecore();

				if (core2_off.isSelected())
					coreList[1] = null;
				else if (core2_p.isSelected())
					coreList[1] = new Pcore();
				else
					coreList[1] = new Ecore();

				if (core3_off.isSelected())
					coreList[2] = null;
				else if (core3_p.isSelected())
					coreList[2] = new Pcore();
				else
					coreList[2] = new Ecore();

				if (core4_off.isSelected())
					coreList[3] = null;
				else if (core4_p.isSelected())
					coreList[3] = new Pcore();
				else
					coreList[3] = new Ecore();
				// 각 코어를 off, Pcore, Ecore 중 하나를 선택함

				// 코어가 전부 off이면 경고창
				if (core1_off.isSelected() && core2_off.isSelected() && core3_off.isSelected()
						&& core4_off.isSelected()) {
					warning("noprocesser", null);
					return;
				}

				for (Process p : observableList) 
				{
					obser.add(new Process(p.getProcessName(), p.getArrivalTime(), p.getBurstTime(), p.getgroupnumber()));
				}
				
				Button[] buttons = { AddButton, ResetButton, RunButton, HelpButton };//버튼 배열
				
				switch (str) {
				case "FCFS":
					FCFS fcfs = new FCFS(timeline);
					fcfs.run(coreList, waitingList, endList, obser, observableList, GanttChart, hBox, corePower,
							Avgresponsetime, tableView2, buttons, algorithmBox);
					break;

				case "RR":
					if (isInvalidTimeQ())
						break;
					RR rr = new RR(timeline);
					rr.run(coreList, waitingList, endList, obser, observableList, GanttChart, hBox, tqText, corePower,
							Avgresponsetime, tableView2, buttons,algorithmBox);
					break;

				case "SPN":
					SPN spn = new SPN(timeline);
					spn.run(coreList, waitingList, endList, obser, observableList, GanttChart, hBox, corePower,
							Avgresponsetime, tableView2, buttons,algorithmBox);
					break;

				case "SRTN":
					SRTN srtn = new SRTN(timeline);
					srtn.run(coreList, waitingList, endList, obser, observableList, GanttChart, hBox, corePower,
							Avgresponsetime, tableView2, buttons,algorithmBox);
					break;

				case "HRRN":
					HRRN hrrn = new HRRN(timeline);
					hrrn.run(coreList, waitingList, endList, obser, observableList, GanttChart, hBox, corePower,
							Avgresponsetime, tableView2, buttons,algorithmBox);
					break;
					
				case "OBG":
					OBG obg = new OBG(timeline);
					obg.run(coreList, waitingList, endList, obser, observableList, GanttChart, hBox, corePower, Avgresponsetime, tableView2, buttons,algorithmBox);
					break;
				}
				;
				

			}
		});

		HelpButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				showHelp();
			}
		});

	}

	private void showHelp() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Help");
		alert.setHeaderText("도움말");
		alert.setContentText("이 프로그램은 Process-Scheduling-Simulator(이하 PSS)입니다. "
				+ "이 PSS는 운영체제가 프로세스에게 자원을 할당하는 방법 중 많이 사용되 5가지 알고리즘과 "
				+ "우리 팀만의 알고리즘을 사용해서 프로세스를 스케줄링하는 시뮬레이터 입니다. 작동 방식을 "
				+ "구현하여 작동되는 모습을 간트차트로 시각적으로 표현하고, 작동에 걸리는 시간을 패널에 " + "표시하여 한 화면으로 볼 수 있도록 구현한 프로그램 입니다.\n\n"
				+ "작동 방식은 다음과 같다.\n" 
				+ "1. Scheduling을 선택한다.\n(단, OBG를 선택할 때 그 전 스케줄링에서 입력한 것들을 초기화된다.)\n\n"
				+ "2.AT,BT를 입력하고 add버튼을 눌러 Input Table을 채우세요.\n"
				+ "(AT,BT을 아무것도 입력하지 않고 add할 경우 오류가 발생, 총 15개만 입력 가능)\n\n" + "3.Run버튼을 누릅니다.\n"
				+ "(Input table이 비어있으면 오류, RR일 떄 TimeQ가 비어있을 때 오류)\n\n" + "4.result table에 확인 되고 간트 차트도 확인"); // 도움말 내용
																												// 작성
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.setPrefSize(800, 600);
		dialogPane.setStyle("-fx-font-size: 12pt;");

		alert.showAndWait();
	}

	private boolean isInteger(String str) // 문자열이 순자인지 판별
	{
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	private void warning(String str, String str1) // 위험 알리는 함수
	{
		AddButton.setDisable(false);
		ResetButton.setDisable(false);
		RunButton.setDisable(false);
		HelpButton.setDisable(false);
		algorithmBox.setDisable(false);
		Alert alert = new Alert(AlertType.WARNING);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.setStyle("-fx-font-size: 12pt;");
		if (str == "empty") {

			alert.setTitle("Warning!!");
			alert.setHeaderText("Empty error!!");
			alert.setContentText("The " + str1 + " is Empty.");

		} // 해당 text 또는 list가 비어있을 경우
		
		else if (str == "number") {

			alert.setTitle("Warning!!");
			alert.setHeaderText("NumberFormat error!!");
			alert.setContentText("The " + str1 + " doesn't fit the number format.");

		} // 숫자가 아닐 경우
		
		else if (str == "mnumber") {

			alert.setTitle("Warning!!");
			alert.setHeaderText("MinusNumber error!!");
			alert.setContentText("The " + str1 + " is Minus.");

		} // 음수 일 경우
		
		else if (str == "noprocesser") {
			alert.setTitle("Warning!!");
			alert.setHeaderText("Processer error!!");
			alert.setContentText("Processer doesn't exist.");
			
		}
		alert.showAndWait();
	}

	private boolean isInvalidTimeQ() {

		if (tqText.getText().isEmpty() || tqText.getText() == null) // 타임쿼텀 비어있을 경우
		{
			warning("empty", "TimeQ");
			tqText.clear();
			return true;
		}
		if (!isInteger(tqText.getText())) // 타임쿼터스가 숫자가 아닐 경우
		{
			warning("number", "TimeQ");
			tqText.clear();
			return true;
		}
		if (Integer.parseInt(tqText.getText()) < 0) // 음수 일 경우
		{
			warning("mnumber", "TimeQ");
			tqText.clear();
			return true;
		}
		return false;
	}
}
