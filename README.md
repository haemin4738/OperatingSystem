# OperatingSystem
운영체제 수업

cpu 스케줄링 시뮬레이터
FCFS, RR, SPN, SRTN, HRRN, OBG 6가지 알고리즘 적용

 # FCFS (First Come First Served)
특징
 - 비선점 스케줄링
     - 이미 할당된 CPU를 다른 프로세스가 강제로 빼앗아 사용할 수 없는 스케줄링 기법
     - 짧은 작업이 긴 작업을 기다리는 경우가 발생할 수 있음
     - 프로세스 응답 시간의 예측이 용이하며, 일괄 처리 방식 (Batch Processing) 에 적합 -Batch Processing : 여러 개의 프로그램을 읽어놓고, 한 번에 하나의 프로그램만 실행함
     - 먼저 도착한 프로세스가 먼저 CPU를 선점함
단점
  - Convoy Effect : 실행시간이 짧은 프로세스들이 실행시간이 긴 프로세스를 계속해서 기다리면서 효율성이 저하됨


# RR(Round - Robin)
 특징
  - 각 프로세스는 동일한 할당 시간 (Time Quantum) 을 가짐
  - CPU를 할당 받고 할당 시간이 지나면 Ready 상태로 돌아가 Ready Queue의 Tail로 들어감
  - 프로세스들이 작업을 완료할 때까지 계속해서 순회함
 장점
  - Response time이 빨라짐
      - n 개의 프로세스가 ready queue 에 있고 할당시간이 q(time quantum)인 경우 각 프로세스는 q 단위로 CPU 시간의 1/n 을 얻는다. 즉, 어떤 프로세스도 (n-1)q time unit 이상 기다리지 않음.
  - 모든 프로세스가 공정하게 CPU를 할당받을 수 있음을 보장
주의할 점
 - 설정한 Time Quantuim이 너무 커지면 FCFS와 동일한 결과가 나옴
 - 너무 작아지면 COntext Switching으로 인한 OverHead가 증가


# SPN (Shortest Process Next)
특징 
 - 비선점 스케줄링
    - 실행시간이 가장 짧은 프로세스부터 실행함
    - 이미 긴 프로세스가 실행중이라면 새로 도착한 짧은 프로세스는 기다려야 함
단점 
 - Starvation : 실행시간이 긴 프로세스가 영원히 CPU를 할당받을 수 없게됨

# SRTN(Shortest Remaining Time Next)
 - SPN의 변형
 - 선점형 스케줄링
     - 잔여 실행 시간이 더 적은 프로세스가 ready 상태과 되면 선점됨
 장점
  - SPN의 장점 극대화
 단점
  - 프로세스 생성시, 총 실행 시간 예측이 필요함
  - 잔여 실행을 계속추적해야 함 -> overhead 증가
  - 구현 및 사용이 비현실적

# HRRN(High Response Ratio Next)
 - SPN의 변형
 - Aging concepts
    - 프로세스의 대기 시간(WT)을 고려하여 기회를 제공
 - 스케줄링 기준(Criteria)
    - Response ratio가 높은 프로세스 우선
 - Response ratio = (WT + BT) / BT (응답률)
     - SPN의 장점 + Starvation 방지
     - 실행 시간 예측 기법 필요(overhead 발생)
