package com.ibm.old.v1.common.zjj.quertz.eg3;

import org.doming.core.tools.DateTool;
import org.quartz.SimpleTrigger;

import java.util.Date;

import static org.quartz.TriggerBuilder.newTrigger;

public class QuertzTest {
	
	public static void main(String[] args) {

		String timeStr="15:05:00";
		Date time= DateTool.getTime(timeStr);
		
		SimpleTrigger trigger = (SimpleTrigger) newTrigger() 
		        .withIdentity("trigger1", "group1")
		        .startAt(time)                     // some Date 
		        .forJob("job1", "group1")                 // identify job with name, group strings
		        .build();
		
		
	}

}
