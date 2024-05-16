/*
 * 프로세스 기본 정보 클래스
 */
package application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Process {
	private SimpleStringProperty processName;
    private SimpleIntegerProperty arrivalTime;
    private SimpleIntegerProperty burstTime;
    private SimpleIntegerProperty waitingTime;
    private SimpleIntegerProperty turnaroundTime;
    private SimpleDoubleProperty NormalizedTT;
    private SimpleIntegerProperty remainBurstTime; //실시간 처리
    private SimpleIntegerProperty TimeQ;
    private SimpleIntegerProperty remainTimeQ;
    private SimpleStringProperty groupnumber;
	
    public Process(String processName, int arrivalTime, int burstTime) {
        this.processName = new SimpleStringProperty(processName);
        this.arrivalTime = new SimpleIntegerProperty(arrivalTime);
        this.burstTime = new SimpleIntegerProperty(burstTime);
        this.remainBurstTime=new SimpleIntegerProperty(burstTime);
        this.waitingTime = new SimpleIntegerProperty(0);
        this.turnaroundTime = new SimpleIntegerProperty(0);
        this.NormalizedTT = new SimpleDoubleProperty(0);
        this.TimeQ = new SimpleIntegerProperty(-1);
        this.remainTimeQ = new SimpleIntegerProperty(-1);
        this.groupnumber=new SimpleStringProperty("-");
    }
    
    public Process(String processName, int arrivalTime, int burstTime,String groupnumber) {
        this.processName = new SimpleStringProperty(processName);
        this.arrivalTime = new SimpleIntegerProperty(arrivalTime);
        this.burstTime = new SimpleIntegerProperty(burstTime);
        this.remainBurstTime=new SimpleIntegerProperty(burstTime);
        this.waitingTime = new SimpleIntegerProperty(0);
        this.turnaroundTime = new SimpleIntegerProperty(0);
        this.NormalizedTT = new SimpleDoubleProperty(0);
        this.TimeQ = new SimpleIntegerProperty(-1);
        this.remainTimeQ = new SimpleIntegerProperty(-1);
        this.groupnumber = new SimpleStringProperty(groupnumber);
    }
    
    public String getProcessName() {
        return processName.get();
    }
    public Integer getArrivalTime() {
        return arrivalTime.get();
    }
    public Integer getBurstTime() {
        return burstTime.get();
    }
    public Integer getWaitingTime() {
    	return waitingTime.get();
    }
    public Integer getturnaroundTime() {
    	return turnaroundTime.get();
    }
    public Integer getNormalizedTT() {
    	return turnaroundTime.get();
    }
    public Integer getremainBurstTime() {
    	return remainBurstTime.get();
    }
    public Integer getTimeQ() {
    	return TimeQ.get();
    }
    public Integer getremainTimeQ() {
    	return remainTimeQ.get();
    }
    public String getgroupnumber() {
        return groupnumber.get();
    }
    
    public void setProcessName(String processName) {
        this.processName.set(processName);
    }
    public void setArrivalTime(Integer arrivalTime) {
        this.arrivalTime.set(arrivalTime);
    }
    public void setBurstTime(Integer burstTime) {
        this.burstTime.set(burstTime);
    }
    public void setWaitingTime(Integer waitingtimes) {
    	this.waitingTime.set(waitingtimes);
    }
    public void setNormalizedTT(double d) {
    	this.NormalizedTT.set(d);
    }
    public void setturnaroundTime(Integer turnaroundTime) {
    	this.turnaroundTime.set(turnaroundTime);
    }
    public void setremainBurstTime(Integer time) {
    	this.remainBurstTime.set(time);
    }
    public void setTimeQ(Integer time) {
    	this.TimeQ.set(time);
    }
    public void setremainTimeQ(Integer time) {
    	this.remainTimeQ.set(time);
    }
    public void setGroupnumber(String groupnumber) {
        this.groupnumber.set(groupnumber);
    }

    public SimpleStringProperty processNameProperty() {
        return processName;
    }
    public SimpleIntegerProperty arrivalTimeProperty() {
        return arrivalTime;
    }
    public SimpleIntegerProperty burstTimeProperty() {
        return burstTime;
    }
    public SimpleIntegerProperty waitingTimeProperty() {
        return waitingTime;
    }
    public SimpleIntegerProperty turnaroundTimeProperty() {
        return turnaroundTime;
    }
    public SimpleDoubleProperty NormalizedTTProperty() {
        return NormalizedTT;
    }
    public SimpleIntegerProperty remainBurstTimeroperty() {
        return remainBurstTime;
    }
    public SimpleIntegerProperty TimeQProperty() {
        return TimeQ;
    }
    public SimpleIntegerProperty remainTimeQProperty() {
        return remainTimeQ;
    }
    public SimpleStringProperty groupnumberProperty() {
        return groupnumber;
    }
}

