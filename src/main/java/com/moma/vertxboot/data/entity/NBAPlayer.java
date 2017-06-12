/**  
 * @Title: NBAPlayer.java
 * @Package: com.moma.vertxboot.data
 * @author: Ivan
 * @date: Jun 12, 2017 2:34:40 PM
 * @version: V1.0  
 */

package com.moma.vertxboot.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * Company: itic
 * </p>
 * 
 * @author: Ivan
 * @date: Jun 12, 2017 2:34:40 PM
 * @version: V1.0
 */
@Entity
@Table(name = "NBAPlayer")
public class NBAPlayer implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7595489252329014078L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@JsonProperty("id")
	private Long id;

	@Column
	@JsonProperty("first_name")
	private String firstName;

	@Column
	@JsonProperty("last_name")
	private String lastName;

	@Column
	@JsonProperty("team")
	private String team;

	@Column
	@JsonProperty("play_no")
	private String playNo;

	@Column
	@JsonProperty("rating")
	private String rating;

	@SuppressWarnings("unused")
	private NBAPlayer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NBAPlayer(Long id, String firstName, String lastName, String team, String playNo, String rating) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.team = team;
		this.playNo = playNo;
		this.rating = rating;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getPlayNo() {
		return playNo;
	}

	public void setPlayNo(String playNo) {
		this.playNo = playNo;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

}
