package pl.edu.agh.mabics.platform

import java.util.List;

import groovy.json.JsonSlurper

class JSONParser {

	static def parseList(list, Class clazz){
		def parsedList = new ArrayList();
		for (element in list){
			parsedList.add(clazz.newInstance(element));
		}
		return parsedList;
	}
	
	static Request parse(String content){
		def jsonObj = new JsonSlurper().parseText(content)
		jsonObj.each{id,data -> println id + data}
		def request = new Request();
		request.setId(jsonObj.id);
		request.setSpeed(jsonObj.speed);
		request.setVelocity(new java.util.Vector(jsonObj.velocity));
		request.setPosition(new Point2D(jsonObj.position));
		request.setRobots(parseList(jsonObj.robots, Point2D.class));
		return request;
//		List<AllowableMove> allowedMoves;
//		List<Point2D> destination;
		
		
//		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
//		Request request = null;
//		try {
//			request = mapper.readValue(content, Request.class);
//		} catch (JsonParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return request;
	}
}
