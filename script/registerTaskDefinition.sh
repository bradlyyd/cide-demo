Cur_Dir=$(pwd)
aws ecs register-task-definition --cli-input-json file://$Cur_Dir/script/taskdefinition.json
