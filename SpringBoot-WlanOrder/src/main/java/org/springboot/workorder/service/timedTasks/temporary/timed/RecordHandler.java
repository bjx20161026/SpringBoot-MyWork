package org.springboot.workorder.service.timedTasks.temporary.timed;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springboot.workorder.dao.res.PrmHotspotDao;
import org.springboot.workorder.dao.res.TemporaryBakDao;
import org.springboot.workorder.dao.res.WorkOrderDao;
import org.springboot.workorder.service.timedTasks.temporary.queue.Queue;
import org.springboot.workorder.util.delayQueue.DelayTask;
import org.springboot.workorder.util.sender.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RecordHandler {
	Logger logger = Logger.getLogger(getClass());
	@Autowired
	WorkOrderDao workOrderDao;
	@Autowired
	PrmHotspotDao prmHotspotDao;
	@Autowired
	TemporaryBakDao temporaryBakDao;
	@Autowired
	Receipt receipt;
	@Autowired
	DelayTask delayTask;
	
	@Scheduled(fixedRate = 60000)
	public void scan() {
		logger.info("scan order table to find tempprary record that waiting to be handled start ...");
		List<Map<String,Object>> list = workOrderDao.findByStateAndType("待处理", "临时退服");
		for(Map<String, Object> map:list) {
			handle(map);
		}
	}
	
	public void handle(Map<String, Object> map) {
		map.put("HOTSTPOTID", prmHotspotDao.findIdbyNasId((String) map.get("NASID")));
		if(map.get("HOTSTPOTID")==null) {
			receipt.SendReceipt((String)map.get("WORKJOB_ID"), "失败", "临时退服对应热点未找到");	
			workOrderDao.updateFailById("校验失败", "临时退服对应热点未找到", "SYSTEM", (String)map.get("WORKJOB_ID"));
			logger.info("临时退服对应热点未找到 ["+(String)map.get("WORKJOB_ID")+"]");
		}
		map.put("STARTDATE", map.get("SHIELD_START"));
		map.put("ENDDATE", map.get("SHIELD_END"));
		map.put("PROJECTSTATUS", 4);
		workOrderDao.updateStateById("处理中", (String)map.get("WORKJOB_ID"));
		temporaryBakDao.insertRecord(map);
		delayTask.setExecuteTime(((Date)map.get("SHIELD_START")).getTime());
		delayTask.setMap(map);
		Queue.queue.offer(delayTask);
	}
}
