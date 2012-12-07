package pl.edu.agh.mabics.agents

import org.springframework.stereotype.Service
import pl.edu.agh.mabics.platform.Move
import org.mortbay.jetty.Request
import pl.edu.agh.mabics.platform.PlatformRequest
import pl.edu.agh.mabics.platform.PlatformResponse

@Service
class RandomAgent extends AbstractAgent {

    private Random random = new Random();

    @Override
    PlatformResponse getNextMove(PlatformRequest request) {
        def move = request.getAllowedMoves().get(Math.abs(random.nextInt()) % request.getAllowedMoves().size());
        Thread.currentThread().sleep(1000);
        return new PlatformResponse(move, 1);
    }
}
