package pl.edu.agh.mabics.platform

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 01.12.12
 * Time: 23:14
 * To change this template use File | Settings | File Templates.
 */
class PlatformResponse {

    def String getJSON(){
        def builder = new groovy.json.JsonBuilder()
        def response = builder{move 3, 0
            speed 1
            velocity 1, 1}
        print builder.toString()
        return builder.toString();
    }
}
