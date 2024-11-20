# Spring Batch
## 실전 스프링 배치
### 어플리케이션 예제 (1)
- Job-1
  - 기능
    - 파일로 부터 데이터를 읽어서 DB에 적재한다.
  - 세부내용
    - 파일은 매일 새롭게 생성된다.
    - 매일 정해진 시간에 파일을 로드하고 데이터를 DB에 업데이트 한다.
    - 이미 처리한 파일은 다시 읽지 않도록 한다.
- Job-2
  - 기능
     - DB로 부터 데이터를 읽어서 API 서버와 통신한다.
  - 내용
     - Partitioning 기능을 통한 멀티 스레드 구조로 Chunk 기반 프로세스를 구현한다.
     - 제품의 유형에 따라서 서로 다른 API 통신을 하도록 구성한다(ClassifierCompositerltemWriter)
     - 제품내용과 API 통신 결과를 각 파일로 저장한다(FlatFilwWriter 상속)
  
- Scheduler
  - 기능
     - 시간을 설정하면 프로그램을 가동시킨다.
  - 내용
     - 정해진 시간에 주기적으로 Job-1과 Job-2 를 실행 시킨다.
     - Quatz 오픈 소스를 활용한다.

![](https://github.com/dididiri1/TIL/blob/main/Batch/images/16_01.png?raw=true)

> 첫번째 job의 역할은 매일매일에 새롭게 생성된 파일들을 db로 적재하는 역할
> 두번째 job은 itemReader, itemProcessor, itemWrite를 각각 생성하고 job2는 walkek  
> 즉 멀티스레드로 작업을 하도록 한다. 그래서 하나의 스레드가 아닌 단일 스레드가 아닌 멀티스레드  
> 구조로 각각의 itemRead와 itemProcess와 itemWrite를 독립적으로 실행을 시크는 구조로 한다.


### 어플리케이션 예제 (2)
#### quartz 추가 
``` 
implementation 'org.springframework.boot:spring-boot-starter-quartz:3.3.5'
``` 
#### REST 템플릿 푸가
``` 
implementation 'org.apache.httpcomponents:httpclient:4.5.13'
``` 