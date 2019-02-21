### 로컬 테스트 - 로컬 카프카 실행 : localhost:9092

zkserver start
/usr/local/bin/kafka-server-start /usr/local/etc/kafka/server.properties

-- 토픽리스트 보기  
/usr/local/bin/kafka-topics --zookeeper localhost:2181 --list

-- 토픽에 대한 값 보기  
/usr/local/bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic consumer --from-beginning

-- 컨슈머와 오프셋 확인  
/usr/local/bin/kafka-consumer-groups --bootstrap-server localhost:9092 --group foo --describe


/usr/local/bin/kafka-topics --zookeeper localhost:2181 --topic consumerPartition --describe

-- create topic  
/usr/local/bin/kafka-topics --zookeeper localhost:2181 --topic consumerPartition --create --partitions 2 --replication-factor 1


-- alter topic  
/usr/local/bin/kafka-topics --zookeeper localhost:2181 --topic consumerPartition --alter --partitions 3 


### 쿠버니츠 안에서 테스트
kubectl -n kafka exec -ti my-kafka-0 -- /usr/bin/kafka-topics --zookeeper my-kafka-zookeeper:2181 --topic consumerPartition --alter --partitions 3

### docker build
```
# 파티션 없이 데이터를 가져오는 것을 v1
mvn clean package -B
docker build -t sanaloveyou/jobconsume:v1 .
docker push sanaloveyou/jobconsume:v1
# 파티션 을 통하여 데이터를 가져오는 것을 v2
mvn clean package -B
docker build -t sanaloveyou/jobconsume:v2 .
docker push sanaloveyou/jobconsume:v2
```

#### 토픽의 partition 을 변경한다  
-- 토픽 리스트 보기  
kubectl -n kafka exec my-kafka-0 --	/usr/bin/kafka-topics --zookeeper my-kafka-zookeeper:2181 --list

-- 토픽 생성  
kubectl -n kafka exec my-kafka-0 -- /usr/bin/kafka-topics --zookeeper my-kafka-zookeeper:2181 --topic cloudInstance --create --partitions 3 --replication-factor 1

-- 토픽 수정(이미 있는 경우)  
kubectl -n kafka exec my-kafka-0 -- /usr/bin/kafka-topics --zookeeper my-kafka-zookeeper:2181 --topic cloudInstance --alter --partitions 3

-- 토픽 삭제
kubectl -n kafka exec my-kafka-0 -- /usr/bin/kafka-topics --zookeeper my-kafka-zookeeper:2181 --topic cloudInstance --delete

#### 카프카 사용시 주의사항
groupId 와 topic 이 같은 메서드를 두개 @KafkaListener 로 등록하여 놓는다면,  
둘중 하나만 작동한다.

