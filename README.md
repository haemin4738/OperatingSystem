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
