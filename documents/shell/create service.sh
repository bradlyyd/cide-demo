# 集群为cicd-fargate-cluster
# 服务名为cicd-service_Ch1
# 任务定义为cicd-task_Ch:1
# 子网[subnet-9dfb2dfa,subnet-c4be6aea]安全组[sg-07cb97cd2b50b2c3a]，对应vpcID vpc-0f9f9bfca5ea7aa7c
# 负载平衡targetGroupArn=arn:aws:elasticloadbalancing:us-east-1:995807247853:targetgroup/notknown/665267fbe910d1c0,containerName=cicd-web,containerPort=8080

aws ecs create-service --cluster cicd-fargate-cluster --service-name cicd-service_Ch1 --task-definition cicd-task_Ch:1 --desired-count 1 --launch-type "FARGATE" --network-configuration "awsvpcConfiguration={subnets=[subnet-9dfb2dfa,subnet-c4be6aea],securityGroups=[sg-07cb97cd2b50b2c3a],assignPublicIp=ENABLED}" --load-balancers targetGroupArn=arn:aws:elasticloadbalancing:us-east-1:995807247853:targetgroup/notknown/665267fbe910d1c0,containerName=cicd-web,containerPort=8080
