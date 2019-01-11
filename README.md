#### git clone
```$xslt
git clone https://github.com/kimscott/msa-sample-kafka-multi-pubsub.git
cd msa-sample-kafka-multi-pubsub

cd jobProduce
mvn clean package -B 
docker build -t <username>:jobProduce:v1 .
docker push <username>:jobProduce:v1

cd ..
cd jobConsume
mvn clean package -B 
docker build -t <username>:jobConsume:v1 .
docker push <username>:jobConsume:v1

```

#### helm 을 이용한 한방 배포
이미지를 변경한 경우에는
helm/deployjobs/templates/deployment.yaml 에서  
이미지 부분을 수정하여 주어야 한다.  

```
cd ../helm/deployjobs/
helm dependency update
helm init

-- install  
helm install --dry-run --debug --name kafkapubsub .
helm install --name kafkapubsub .

-- 삭제  
helm delete kafkapubsub --purge

-- 값을 업데이트  
helm install --name kafkapubsub --set consumer.replicas=2,producer.replicas=2 .
```

#### 시나리오
jobProduce 프로젝트는 카프카로 데이터를 쏘는 역할을 한다.  
이 app 은 kubernates의 cronjob 의해서 컨트롤이 될 것이다.

jobConsume 은 이 받아온 데이터를 출력하는 역할을 함.

1. 컨슈머가 1개일때 큐에서 데이터를 모두 소화 못시킬때는 토픽의 파티션을 늘려주고,  
늘어난 만큼 pod 의 replicas 를 늘여주면 된다.

2. 프로듀서가 크론잡에 의해서 실행 될적에, 다음 스케쥴 시작시간 보다, 끝나는 시간이 길다면,  
역시나 스케쥴러를 여러개 실행을 시킨다.  

이 시나리오를 실행시키기 위한 코드가 들어가 있음

