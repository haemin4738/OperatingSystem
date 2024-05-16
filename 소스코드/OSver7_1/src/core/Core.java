package core;

import java.util.LinkedList;

import application.Process;

public class Core {
	private Process process;
	private boolean visited = false;
	private boolean prevUsed = false; // 코어가 쉬고 있었는지 확인
	private double powerConsumption = 0; // 전력 소모량
	
	public Process getProcess() {
		return process;
	}
	public void setProcess(Process process) {
		this.process = process;
	}
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	public boolean isprevUsed() {
		return prevUsed;
	}
	public void setprevUsed(boolean prevUsed) {
		this.prevUsed = prevUsed;
	}
	public double getPowerConsumption() {
        return powerConsumption;
    }
	public void setPowerConsumption(double powerConsumption) {
		this.powerConsumption = powerConsumption;
	}
	
	public void setComponent(int time) {} // 코드 중복 제거. (TT, NTT, WT 설정)
	public void setComponent_p(int time) {} // 코드 중복 제거. (TT, NTT, WT 설정)
	public void emptyCore() {} // core에 빈칸 할당
	public void setPower() {} // 전력 소모량 갱신
	public boolean work(int time) { return false; } // 1초동안 일을 하고, 작업이 끝나면 true, 작업이 남아있으면 false를 반환
	public boolean RRwork(int time, LinkedList<Process> waitingList) { return false; } // RRwork
	public boolean SRTNwork(int time, LinkedList<Process> waitingList) { return false; } //SRTNwork
}
