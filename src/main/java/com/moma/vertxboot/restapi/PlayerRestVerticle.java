/**  
 * @Title: PlayerRestAPI.java
 * @Package: com.moma.vertxboot.restapi
 * @author: Ivan
 * @date: Jun 12, 2017 2:48:16 PM
 * @version: V1.0  
 */

package com.moma.vertxboot.restapi;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moma.vertxboot.data.DAO.NBAPlayerDAO;
import com.moma.vertxboot.data.entity.NBAPlayer;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Vertx Verticle as Controller
 * - Define Route
 * - Map handle Method
 * - Create Server on define port
 * 
 * <p>
 * Company: itic
 * </p>
 * 
 * @author: Ivan
 * @date: Jun 12, 2017 2:48:16 PM
 * @version: V1.0
 */
@Component
public class PlayerRestVerticle {
	// Port No.
	private final Integer port = 8080;

	@Autowired
	private NBAPlayerDAO dao;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private Vertx vertx;

	/**
	 * Set up the Vert.x Web routes and start the HTTP server
	 * 
	 * @throws Exception
	 */
	@PostConstruct
	public void start() throws Exception {
		// Add some test data
		NBAPlayer kobe = new NBAPlayer(1L, "Kobe", "Bryant", "LA Lakers", "24", "S++");
		NBAPlayer kg = new NBAPlayer(2L, "Kevin", "Garnett", "Minnesota Timberwolves", "21", "S++");
		dao.save(kobe);
		dao.save(kg);
		//

		Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());
		router.get("/player/:id").produces("application/json").blockingHandler(this::getPlayerById);
		router.get("/players").produces("application/json").blockingHandler(this::getAllPlayers);
		router.post("/player").consumes("application/json").produces("application/json")
				.blockingHandler(this::addPlayer);
		vertx.createHttpServer().requestHandler(router::accept).listen(this.port);
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

	/**
	 * get all players
	 * 
	 * @param rc
	 */
	private void getAllPlayers(RoutingContext rc) {
		List<NBAPlayer> players = StreamSupport.stream(dao.findAll().spliterator(), false).collect(Collectors.toList());
		try {
			rc.response().setStatusMessage("OK").setStatusCode(200).end(mapper.writeValueAsString(players));
		} catch (JsonProcessingException jpe) {
			rc.response().setStatusMessage("Server Error").setStatusCode(500).end("Server Error");
			// log.error("Server error", jpe);
		}
	}

	/**
	 * add/update a player
	 * 
	 * @param rc
	 */
	private void addPlayer(RoutingContext rc) {
		try {
			String body = rc.getBodyAsString();
			NBAPlayer player = mapper.readValue(body, NBAPlayer.class);
			NBAPlayer saved = dao.save(player);
			if (saved != null) {
				rc.response().setStatusMessage("Accepted").setStatusCode(202).end(mapper.writeValueAsString(saved));
			} else {
				rc.response().setStatusMessage("Bad Request").setStatusCode(400).end("Bad Request");
			}
		} catch (IOException e) {
			rc.response().setStatusMessage("Server Error").setStatusCode(500).end("Server Error");
		}
	}
}
