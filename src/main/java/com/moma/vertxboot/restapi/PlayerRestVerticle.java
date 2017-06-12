/**  
 * @Title: PlayerRestAPI.java
 * @Package: com.moma.vertxboot.restapi
 * @author: Ivan
 * @date: Jun 12, 2017 2:48:16 PM
 * @version: V1.0  
 */

package com.moma.vertxboot.restapi;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.moma.vertxboot.data.DAO.NBAPlayerDAO;
import com.moma.vertxboot.data.entity.NBAPlayer;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * <p>Company: itic</p>
 * 
 * @author: Ivan
 * @date: Jun 12, 2017 2:48:16 PM
 * @version: V1.0
 */
public class PlayerRestVerticle {
    @Autowired
    private NBAPlayerDAO dao;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private Vertx        vertx;

    /**
     * Set up the Vert.x Web routes and start the HTTP server
     * 
     * @throws Exception
     */
    @PostConstruct
    public void start()
            throws Exception {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.get("/v1/player/:id").produces("application/json").blockingHandler(this::getPlayerById);
        router.get("/v1/player").produces("application/json").blockingHandler(this::getAllPlayers);
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }

    /**
     * get Player by id
     * 
     * @param rc
     */
    private void getPlayerById(RoutingContext rc) {
        Long id = Long.parseLong(rc.request().getParam("id"));
        try {
            NBAPlayer player = dao.findOne(id);
            if (player == null) {
                rc.response().setStatusMessage("Not Found").setStatusCode(404).end("Not Found");
            } else {
                rc.response().setStatusMessage("OK").setStatusCode(200).end(mapper.writeValueAsString(player));
            }
        } catch (JsonProcessingException jpe) {
            rc.response().setStatusMessage("Server Error").setStatusCode(500).end("Server Error");
        }
    }

    private void getAllPlayers(RoutingContext rc) {
        List<NBAPlayer> players = StreamSupport.stream(dao.findAll().spliterator(), false).collect(Collectors.toList());
        try {
            rc.response().setStatusMessage("OK").setStatusCode(200).end(mapper.writeValueAsString(players));
        } catch (JsonProcessingException jpe) {
            rc.response().setStatusMessage("Server Error").setStatusCode(500).end("Server Error");
            // log.error("Server error", jpe);
        }
    }
}
