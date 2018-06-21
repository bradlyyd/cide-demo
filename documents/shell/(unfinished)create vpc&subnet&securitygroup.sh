aws ecs create-cluster --cluster-name cicd-fargate-cluster_2
aws ecs register-task-definition --cli-input-json file:///home/ec2-user/tasks/cicd-task.json

aws ec2 create-vpc --cidr-block 10.0.0.0/16 > vpc.json

aws ec2 create-subnet --vpc-id vpc-0404d4353d04c00de --cidr-block 10.0.1.0/24



#创建VPC,将返回的vpc json信息输入到vpc文件中
aws ec2 create-vpc --cidr-block 10.0.0.0/16 > vpc.json

#获取vpcID
FILE=vpc.json
currentline=`head -3 ${FILE} | tail -n1`   # 获取第3行内容
VPCID=`echo ${currentline} | cut -d"\"" -f4`  # 获取用户名，以冒号作为分隔符的第4项

#根据获取的vpcID创建子网,将返回的subnet json信息输入到subnet文件中
aws ec2 create-subnet --vpc-id $VPCID --cidr-block 10.0.1.0/24 > subnet.json

#获取subnetID
FILE=subnet.json
currentline=`head -3 ${FILE} | tail -n1`   # 获取第3行内容
SUBNETID=`echo ${currentline} | cut -d"\"" -f4`  # 获取用户名，以冒号作为分隔符的第4

#创建 Internet 网关,将返回的igw json信息输入到igw文件中
aws ec2 create-internet-gateway > igw.json

#获取igwID
FILE=igw.json
currentline=`head -3 ${FILE} | tail -n1`   # 获取第3行内容
IGWID=`echo ${currentline} | cut -d"\"" -f4`  # 获取用户名，以冒号作为分隔符的第4

#将 Internet 网关连接到 VPC
aws ec2 attach-internet-gateway --vpc-id VPCID --internet-gateway-id IGWID

#为 VPC 创建自定义路由表,将返回的rtb json信息输入到rtb文件中
aws ec2 create-route-table --vpc-id VPCID > rtb.json

#获取rtbID
FILE=rtb.json
currentline=`head -3 ${FILE} | tail -n1`   # 获取第3行内容
RTBID=`echo ${currentline} | cut -d"\"" -f4`  # 获取用户名，以冒号作为分隔符的第4

#在路由表中创建一个将所有流量 (0.0.0.0/0) 指向 Internet 网关的路由
aws ec2 create-route --route-table-id RTBID --destination-cidr-block 0.0.0.0/0 --gateway-id IGWID

#将路由表与子网关联
aws ec2 associate-route-table --subnet-id subnet-05c8cac041e115136 --route-table-id rtb-09878b4d63ca49e12
