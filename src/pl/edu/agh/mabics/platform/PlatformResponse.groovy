package pl.edu.agh.mabics.platform

import org.springframework.beans.factory.annotation.Autowired
import pl.edu.agh.mabics.platform.converters.MoveReverseConverter
import org.springframework.stereotype.Service
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.beans.factory.BeanFactory
import pl.edu.agh.mabics.agents.RandomAgent
import org.springframework.beans.factory.annotation.Configurable

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 01.12.12
 * Time: 23:14
 * To change this template use File | Settings | File Templates.
 */
@Configurable
class PlatformResponse {

    private Move move;
    private int speed;

    public PlatformResponse(move, speed){
        this.move = move;
        this.speed = speed;
    }

    @Autowired
    void setMoveReverseConverter(MoveReverseConverter moveReverseConverter) {
        this.moveReverseConverter = moveReverseConverter
    }

    Move getMove() {
        return move
    }

    void setMove(Move move) {
        this.move = move
    }

    int getSpeed() {
        return speed
    }

    void setSpeed(int speed) {
        this.speed = speed
    }

    def String getJSON(moveReverseConverter){
        def builder = new groovy.json.JsonBuilder()
        def jsonMove = moveReverseConverter.convert(move);

        def response = builder{move jsonMove.get(0).get(0),jsonMove.get(0).get(1)
            speed getSpeed()
            velocity jsonMove.get(1).get(0),jsonMove.get(1).get(1)}
        print builder.toString()
        return builder.toString();
    }
}

