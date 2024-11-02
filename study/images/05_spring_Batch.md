# 섹션 5 스프링 배치 실행 - Job

1. 배치 초기화 설정
2. Job and Step
3. Job and Flow
4. @JobScope /  @StepScope

## 1. 배치 초기화 설정

1. JobLauncherApplicationRunner
   • Spring Batch 작업을 시작하는 ApplicationRunner 로서 BatchAutoConfiguration 에서 생성됨
   • 스프링 부트에서 제공하는 ApplicationRunner 의 구현체로 어플리케이션이 정상적으로 구동되자 마다 실행됨
   • 기본적으로 빈으로 등록된 모든 job 을 실행시킨다.

2. BatchProperties
   • Spring Batch 의 환경 설정 클래스
   • Job 이름, 스키마 초기화 설정, 테이블 Prefix 등의 값을 설정할 수 있다.
   • application.properties or application.yml 파일에 설정함
       • batch:
         job:
           names: ${job.name:NONE}
           initialize-schema: NEVER
           tablePrefix: SYSTEM
3. Job 실행 옵션
   • 지정한 Batch Job만 실행하도록 할 수 있음
   • spring.batch.job.names: ${job.name:NONE}
   • 어플리케이션 실행시 Program arguments 로 job 이름 입력한다
     • --job.name=helloJob
     • --job.name=helloJob,simpleJob (하나 이상의 job 을 실행 할 경우 쉼표로 구분해서 입력함)

## 2. Job and Step
### JobBuilderFactory 

1. 스프링배치는 Job과Step을 쉽게 생성 및 설정할 수 있도록 util성격의 빌더 클래스들을 제공함
2. JobBuilderFactory
   • JobBuilder 를 생성하는 팩토리 클래스로서 get(String name) 메서드 제공
   • jobBuilderFactory.get(“jobName")
      • “jobName” 은 스프링 배치가 Job 을 실행시킬 때 참조하는 Job 의 이름
3. JobBuilder
   • Job을 구성하는 설정조건에 따라 두개의 하위 빌더클래스를 생성하고 실제Job생성을 위임한다
      • SimpleJobBuilder
      • SimpleJob 을 생성하는 Builder 클래스
   • Job 실행과 관련된 여러 설정 API를 제공한다
   • FlowJobBuilder
      • FlowJob 을 생성하는 Builder 클래스
      • 내부적으로 FlowBuilder 를 반환함으로써 Flow 실행과 관련된 여러 설정 API 를 제공한다

### Flow
``` 
package com.example.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;

import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob2() {
        return (Job) jobBuilderFactory.get("batchJob2")
                .start(flow()) // 이제 올바르게 인식됩니다
                .next(step5())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step1 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step2 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step3 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step4() {
        return stepBuilderFactory.get("step4")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step4 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step5() {
        return stepBuilderFactory.get("step5")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step5 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Flow flow() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");
        flowBuilder.start(step3())
                .next(step4())
                .end();

        return flowBuilder.build();
    }

}

```