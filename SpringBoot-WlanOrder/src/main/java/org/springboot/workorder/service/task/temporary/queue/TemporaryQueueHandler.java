package org.springboot.workorder.service.task.temporary.queue;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springboot.workorder.dao.res.PrmHotspotDao;
import org.springboot.workorder.dao.res.TemporaryBakDao;
import org.springboot.workorder.dao.res.WorkOrderDao;
import org.springboot.workorder.util.delayQueue.DelayTask;
import org.springboot.workorder.util.delayQueue.QueueHandler;
import org.springboot.workorder.util.sender.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bjx
 * @Description handle queue task,try change status and check result,if not
 *              succeeded try to handle it again 60 seconds later,but if try
 *              more than 60 times will exit
 * 
 */
@Service
public class TemporaryQueueHandler extends QueueHandler {
	Logger logger = Logger.getLogger(getClass());

	@Autowired
	DelayTask delayTask;
	@Autowired
	PrmHotspotDao prmHotspotDao;
	@Autowired
	TemporaryBakDao temporaryBakDao;
	@Autowired
	WorkOrderDao workOrderDao;
	@Autowired
	Receipt receipt;

	private Map<String, Object> map;

	@Override
	public void run() {
		try {
			delayTask = queue.take();
			map = delayTask.getMap();
			Shield();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void Shield() throws InterruptedException {
		int projectstatus = (Integer) map.get("PROJECTSTATUS");
		for (int i = 1; i <= 60; i++) {
			prmHotspotDao.changeStatus(map);
			logger.info(String.format("Temporary ShieldTask try [%d] times workjob_id [%s] to projectstatus [%d]", i,
					(String) map.get("WORKJOB_ID"), projectstatus));
			Thread.sleep(60000);
			if (prmHotspotDao.findStatus((String) map.get("HOTSTPOTID")) == projectstatus) {
				temporaryBakDao.deleteRecord(map);
				if (projectstatus == 4) {
					receipt.Send((String) map.get("WORKJOB_ID"), "成功", "");
					map.put("STARTDATE", map.get("ENDDATE"));
					map.put("PROJECTSTATUS", 1);
					temporaryBakDao.insertRecord(map);
					delayTask.setExecuteTime(((Date) map.get("STARTDATE")).getTime());
					delayTask.setMap(map);
					Queue.queue.offer(delayTask);
				}
				break;
			}
		}
		logger.error(String.format(
				"Temporary ShieldTask Failed for [Try more than 60 times] workjob_id [%s] to projectstatus [%d]",
				(String) map.get("WORKJOB_ID"), projectstatus));
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

}
