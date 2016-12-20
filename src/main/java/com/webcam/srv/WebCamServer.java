package com.webcam.srv;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.eclipse.jetty.websocket.server.WebSocketHandler;

public class WebCamServer {

    public static void main(String[] args) throws Exception {

        Server server = new Server(8123);

        WebSocketHandler wsHandler = new WebSocketHandler() {
            @Override
            public void configure(WebSocketServletFactory factory) {
                factory.register(com.webcam.srv.WebSocketHandler.class);
            }
        };

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});
        resourceHandler.setResourceBase("src/main/webapp/");

        ContextHandler staticContext = new ContextHandler();
        staticContext.setContextPath("/webcam");
        staticContext.setHandler(resourceHandler);


        ContextHandler socketContext = new ContextHandler();
        socketContext.setContextPath("/webcam/socket");
        socketContext.setHandler(wsHandler);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {socketContext, staticContext});

        server.setHandler(handlers);
        server.start();
        server.join();
    }

}
