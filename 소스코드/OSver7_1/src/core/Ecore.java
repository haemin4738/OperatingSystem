package core;

import java.util.LinkedList;

import application.Process;

public class Ecore extends Core{
	private static final int excuteTime = 1;
	
	// 코드 중복 제거. (TT, NTT, WT 설정)
	@Override
	public void setComponent(int time) {
		this.getProcess().setturnaroundTime(time - this.getProcess().getArrivalTime() + 1);
		double NTT = Math.round((double)this.getProcess().getturnaroundTime()/this.getProcess().getBurstTime() * 100) / 100.0; // NTT 소수 둘째자리까지 반올림
		this.getProcess().setNormalizedTT(NTT);
		this.getProcess().setWaitingTime(this.getProcess().getturnaroundTime() - this.getProcess().getBurstTime());
	}
	
	@Override
	public void setComponent_p(int time) {
		this.getProcess().setturnaroundTime(time - this.getProcess().getArrivalTime() + 1);
		double NTT = Math.round((double)this.getProcess().getturnaroundTime()/this.getProcess().getBurstTime() * 100) / 100.0; // NTT 소수 둘째자리까지 반올림
		this.getProcess().setNormalizedTT(NTT);
	}
	
	// core 빈칸 할당
	@Override
	public void emptyCore() {
		this.setProcess(null);
		this.setVisited(false);
	}
	
	// 전력 소모량 갱신
	@Override
	public void setPower() {
		 if (!this.isprevUsed()) {
	        	setPowerConsumption(getPowerConsumption() + 0.1);
	            this.setprevUsed(true);
	        }
		 setPowerConsumption(getPowerConsumption() + 1);
	}
	
	@Override
	public boolean work(int time) { // 1초동안 일을 하고, 작업이 끝나면 true, 작업이 남아있으면 false를 반환
		setPower();
		this.getProcess().setremainBurstTime(this.getProcess().getremainBurstTime()-excuteTime);
		if(this.getProcess().getremainBurstTime() <= 0) {
			setComponent(time);
			emptyCore();
			return true;
		}
		else return false;
	}
	
	/**
	 *  RR시 timeQ 처리
	 */
	@Override      
	public boolean RRwork(int time, LinkedList<Process> waitingList) { // 1초동안 일을 하고, 작업이 끝나면 true, 작업이 남아있으면 false를 반환
		setPower();
		this.getProcess().setremainBurstTime(this.getProcess().getremainBurstTime()-excuteTime);
		this.getProcess().setremainTimeQ(this.getProcess().getremainTimeQ() - 1); // 남은 timeQ 1초 감소
		if(this.getProcess().getremainTimeQ() == 0) { // timeQ 만큼 작업 한 경우
			if(this.getProcess().getremainBurstTime() <= 0) { // BT만큼 일을 다 한 경우
				setComponent_p(time);
				emptyCore();
				return true;
			}
			else { // 아직 일이 남은 경우
				this.getProcess().setremainTimeQ(this.getProcess().getTimeQ()); // 남은 timeQ 재설정
				waitingList.add(this.getProcess()); // 다시 waitingList의 마지막에 추가
				emptyCore();
				return false;
			}
		}
		else {
			if(this.getProcess().getremainBurstTime() <= 0) { // BT만큼 일을 다 한 경우
				setComponent_p(time);
				emptyCore();
				return true;
			}
		}
		return false;
	}
	
	/**
	 *  SRTB 처리
	 */
	@Override      
	public boolean SRTNwork(int time, LinkedList<Process> waitingList) { 
		setPower();
		this.getProcess().setremainBurstTime(this.getProcess().getremainBurstTime()-excuteTime);
		if(this.getProcess().getremainBurstTime() <= 0) { // BT만큼 일을 다 한 경우
			setComponent_p(time);
			emptyCore();
			return true;
		}
		else {
			waitingList.add(this.getProcess()); // 다시 waitingList의 마지막에 추가
			emptyCore();
			return false;
		}
	}
}
	



