package pl.edu.agh.mabics.agents;

import org.mortbay.jetty.Request;
import org.mortbay.jetty.Response;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.platform.JSONParser;
import pl.edu.agh.mabics.platform.Move;
import pl.edu.agh.mabics.platform.PlatformRequest;
import pl.edu.agh.mabics.platform.PlatformResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
abstract class AbstractAgent extends AbstractHandler {

    private JSONParser jsonParser;
    private PlatformResponse platformResponse = new PlatformResponse();

    @Autowired
    public void setJsonParser(JSONParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    public abstract Move getNextMove(Request request);

    public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, int dispatch) throws IOException,
			ServletException {
		String content = request.getReader().readLine();
		PlatformRequest parsedRequest = jsonParser.parseRequest(content);
		response.setContentType("text/plain");
        response.setHeader("Content-type", "text/plain");
        response.setStatus(200);
		response.setStatus(HttpServletResponse.SC_OK);
        //response.setContentLength(21);
        Response r = (Response) response;
        response.getOutputStream().print((new PlatformResponse()).getJSON());
//        response.getOutputStream().flush();
//        response.getOutputStream().close();
//        response.flushBuffer();
//        Request jRequest = (Request) request;
//        jRequest.getConnection().commitResponse(true);
//        ((Response) response).complete();
		((Request) request).setHandled(true);
	}

	public AbstractAgent(){

		Server server = new Server(8080);
		server.setHandler(this);
		try {
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}