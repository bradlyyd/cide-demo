FROM centos:7
MAINTAINER salo

RUN yum install -y docker \
	&& yum install initscripts -y \
	&& curl -O https://bootstrap.pypa.io/get-pip.py \
	&& python get-pip.py --user \
	&& export PATH=~/.local/bin:$PATH \
	&& source ~/.bash_profile \
	&& pip install awscli --upgrade --user \
	&& aws configure \
	&& AKIAIWCJ43EA2UKSMT5A \
	&& s1plWjnBg82hEH5KQR5itdOT68oowJwQzwkA9XUs \
	&& us-east-1 \
	&& json \
	&& $(aws ecr get-login --no-include-email --region us-east-1)


yum install -y docker && yum install initscripts -y && curl -O https://bootstrap.pypa.io/get-pip.py && python get-pip.py --user && export PATH=~/.local/bin:$PATH && source ~/.bash_profile && pip install awscli --upgrade --user && aws configure && AKIAIWCJ43EA2UKSMT5A && s1plWjnBg82hEH5KQR5itdOT68oowJwQzwkA9XUs && us-east-1 && json && $(aws ecr get-login --no-include-email --region us-east-1)

echo ""