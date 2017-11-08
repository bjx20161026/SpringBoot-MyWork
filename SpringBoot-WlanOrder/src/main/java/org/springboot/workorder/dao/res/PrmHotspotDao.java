package org.springboot.workorder.dao.res;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PrmHotspotDao {
	
	String findIdbyNasId(@Param("nasId") String nasid);
	
	int findStatus(@Param("hotspot_id") String hotspot_id);
	
	void changeStatus(@Param("map") Map map);

}
