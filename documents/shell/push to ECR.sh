IMAGENAME=$1
REPOSITORYURI=$2
REPOSITORYNAME=$3
$(aws ecr get-login --no-include-email --region us-east-1)
docker tag ${IMAGENAME} ${REPOSITORYURI}/${REPOSITORYNAME}
docker push ${REPOSITORYURI}/${REPOSITORYNAME}
