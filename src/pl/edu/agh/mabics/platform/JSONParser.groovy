package pl.edu.agh.mabics.platform

import groovy.json.JsonSlurper

import pl.edu.agh.mabics.platform.converters.Point2DConverter
import pl.edu.agh.mabics.platform.converters.VectorConverter
import pl.edu.agh.mabics.platform.converters.ConverterUtil
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import pl.edu.agh.mabics.platform.converters.MoveConverter

@Service
class JSONParser {

    private Point2DConverter point2DConverter;
    private VectorConverter vectorConverter;
    private ConverterUtil converterUtil;
    private MoveConverter moveConverter;

    @Autowired
    void setPoint2DConverter(Point2DConverter point2DConverter) {
        this.point2DConverter = point2DConverter
    }

    @Autowired
    void setVectorConverter(VectorConverter vectorConverter) {
        this.vectorConverter = vectorConverter
    }

    @Autowired
    void setMoveConverter(MoveConverter moveConverter) {
        this.moveConverter = moveConverter
    }

    @Autowired
    void setConverterUtil(ConverterUtil converterUtil) {
        this.converterUtil = converterUtil
    }

    public PlatformRequest parseRequest(String content) {
        def jsonObj = new JsonSlurper().parseText(content)
        jsonObj.each {id, data -> println id + data}
        def request = new PlatformRequest();
        request.setId(jsonObj.id);
        request.setSpeed(jsonObj.speed);
        request.setVelocity(vectorConverter.convert(jsonObj.velocity));
        request.setPosition(point2DConverter.convert(jsonObj.position));
        request.setRobots(converterUtil.convertList(jsonObj.robots, point2DConverter));
        request.setAllowedMoves(converterUtil.convertList(jsonObj.allowedMoves, moveConverter))
        request.setDestination(converterUtil.convertList(jsonObj.destination, point2DConverter))


        return request;
    }
}
