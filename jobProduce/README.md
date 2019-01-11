### 스프링 카프카를 이용한 퍼블리싱 예제

### docker build
```
mvn clean package -B
docker build -t sanaloveyou/jobproduce:v1 .
docker push sanaloveyou/jobproduce:v1
```

### kubernetes 에 job 배포