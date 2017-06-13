package com.moma.vertxboot.restapi;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moma.vertxboot.data.DAO.NBAPlayerDAO;
import com.moma.vertxboot.data.entity.NBAPlayer;

@RestController
public class PlayerRestController {
	@Autowired
	private NBAPlayerDAO dao;

	@RequestMapping("/r/players")
	public List<NBAPlayer> getPlayers() {
		List<NBAPlayer> players = StreamSupport.stream(dao.findAll().spliterator(), false).collect(Collectors.toList());
		return players;
	}

}
