package pl.edu.agh.mabics.platform

import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.edu.agh.mabics.platform.model.PlatformRequest
import pl.edu.agh.mabics.platform.model.PlatformResponse
import pl.edu.agh.mabics.platform.converters.*

@Service
class JSONHelper {

    private Point2DConverter point2DConverter;
    private VectorConverter vectorConverter;
    private ConverterUtil converterUtil;
    private MoveConverter moveConverter;
    private MoveReverseConverter moveReverseConverter;
    private RobotConverter robotConverter;



    @Autowired
    void setMoveReverseConverter(MoveReverseConverter moveReverseConverter) {
        this.moveReverseConverter = moveReverseConverter
    }

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
//        jsonObj.each {id, data -> println id + data}
        def request = new PlatformRequest();
        request.setId(jsonObj.id);
        request.setSpeed(jsonObj.speed);
        request.setVelocity(vectorConverter.convert(jsonObj.velocity));
        request.setPosition(point2DConverter.convert(jsonObj.position));
        request.setRobots(converterUtil.convertList(jsonObj.robots, robotConverter));
        request.setAllowedMoves(converterUtil.convertList(jsonObj.allowedMoves, moveConverter))         //TODO brakuje
        // speed?
        request.setDestination(converterUtil.convertList(jsonObj.destination, point2DConverter))
        return request;
    }

    public String responseToJSON(PlatformResponse response) {

        def builder = new groovy.json.JsonBuilder()
        def jsonMove = moveReverseConverter.convert(response.getMove());

        builder {
            move jsonMove.get(0).get(0), jsonMove.get(0).get(1)
            speed response.getSpeed()
            velocity jsonMove.get(1).get(0), jsonMove.get(1).get(1)
        }
        print builder.toString() + "\n"
        return builder.toString();
    }

    @Autowired
    void setRobotConverter(RobotConverter robotConverter) {
        this.robotConverter = robotConverter
    }
}
