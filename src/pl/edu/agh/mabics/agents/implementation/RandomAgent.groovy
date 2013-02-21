package pl.edu.agh.mabics.agents.implementation

import org.springframework.stereotype.Service
import pl.edu.agh.mabics.agents.AbstractAgent
import pl.edu.agh.mabics.platform.model.PlatformRequest
import pl.edu.agh.mabics.platform.model.PlatformResponse

//TODO shouldn't it be Bean instead of Service?
@Service
class RandomAgent extends AbstractAgent {

    private Random random = new Random();

    @Override
    PlatformResponse getNextMove(PlatformRequest request) {
        def move = request.getAllowedMoves().get(Math.abs(random.nextInt()) % request.getAllowedMoves().size());
        Thread.currentThread().sleep(100);
        return new PlatformResponse(move, 1);
    }

    @Override
    void onComplete() {
        //TODO complete

    }
}
