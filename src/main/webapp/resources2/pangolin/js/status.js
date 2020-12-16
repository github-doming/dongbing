function singlestaus(obj){			//工单状态
	var one_type =['待分配','待处理','升级','延时','处理中','已处理','已关闭','已归档'];
	return one_type[obj];
}

function priority(obj){			//优先级
	var two_type =['低','中','高'];
	return two_type[obj];
}

function warning(obj){			//报障来源
	var three_type =['事件','任务'];
	return three_type[obj];
}

function singletype(obj){			//工单类型
	var four_type =['POC','现场支持','远程支持','维护','报错'];
	return four_type[obj];
}