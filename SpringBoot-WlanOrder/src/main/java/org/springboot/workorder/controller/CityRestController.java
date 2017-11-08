package org.springboot.workorder.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springboot.workorder.dao.res.FileOrderDao;
import org.springboot.workorder.dao.res.PrmHotspotDao;
import org.springboot.workorder.dao.res.WorkOrderDao;
import org.springboot.workorder.service.templateParser.BasicParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * just for test
 */
@RestController
public class CityRestController {

    @Autowired
    private FileOrderDao fileOrderDao;
    
    
    @Autowired
    PrmHotspotDao prmHotspotDao;

     
    @RequestMapping(value = "/api/test", method = RequestMethod.GET)
    public List<Map<String,Object>> getTestString() throws Exception, JsonMappingException, IOException {
    	 ObjectMapper mapper = new ObjectMapper(); 
    	 Map map=mapper.readValue("", Map.class);
    	return fileOrderDao.findUnhandled();
    }
    
    @RequestMapping(value = "/api/test2", method = RequestMethod.GET)
    public String getString() {
    	return prmHotspotDao.findIdbyNasId("12313243");
    }
    
    @RequestMapping(value = "/api/map", method = RequestMethod.GET)
    public HashMap<String, Object> getTestMap() throws IOException {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("hotspotid", prmHotspotDao.findIdbyNasId("1234545"));
    	return map;
    }
}
