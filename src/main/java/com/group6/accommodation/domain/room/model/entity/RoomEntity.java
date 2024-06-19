package com.group6.accommodation.domain.room.model.entity;

import com.group6.accommodation.domain.accommodation.model.entity.AccommodationEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "room")
public class RoomEntity {
	@EmbeddedId
	private RoomId id;

	@MapsId("accommodationId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "accommodation_id", nullable = false)
	private AccommodationEntity accommodation;

	@Column(name = "room_title", nullable = false, length = 32)
	private String roomTitle;

	@Column(name = "room_size", nullable = false)
	private Integer roomSize;

	@Column(name = "room_count", nullable = false)
	private Integer roomCount;

	@Column(name = "room_base_count", nullable = false)
	private Integer roomBaseCount;

	@Column(name = "room_max_count", nullable = false)
	private Integer roomMaxCount;

	@Column(name = "room_offseason_minfee1")
	private Integer roomOffseasonMinfee1;

	@Column(name = "room_offseason_minfee2")
	private Integer roomOffseasonMinfee2;

	@Column(name = "room_peakseason_minfee1")
	private Integer roomPeakseasonMinfee1;

	@Column(name = "room_peakseason_minfee2")
	private Integer roomPeakseasonMinfee2;

	@Lob
	@Column(name = "room_intro")
	private String roomIntro;

	@Column(name = "room_bath", length = 32)
	private String roomBath;

	@Column(name = "room_hometheater", length = 32)
	private String roomHometheater;

	@Column(name = "room_aircondition", length = 32)
	private String roomAircondition;

	@Column(name = "room_tv", length = 32)
	private String roomTv;

	@Column(name = "room_pc", length = 32)
	private String roomPc;

	@Column(name = "room_cable", length = 32)
	private String roomCable;

	@Column(name = "room_internet", length = 32)
	private String roomInternet;

	@Column(name = "room_refrigerator", length = 32)
	private String roomRefrigerator;

	@Column(name = "room_toiletries", length = 32)
	private String roomToiletries;

	@Column(name = "room_sofa", length = 32)
	private String roomSofa;

	@Column(name = "room_cook", length = 32)
	private String roomCook;

	@Column(name = "room_table", length = 32)
	private String roomTable;

	@Column(name = "room_hairdryer", length = 32)
	private String roomHairdryer;

	@Column(name = "room_img1", length = 300)
	private String roomImg1;

	@Column(name = "room_img2", length = 300)
	private String roomImg2;

	@Column(name = "room_img3", length = 300)
	private String roomImg3;

	@Column(name = "room_img4", length = 300)
	private String roomImg4;

	@Column(name = "room_img5", length = 300)
	private String roomImg5;

	@Column(name = "check_in", nullable = false)
	private Instant checkIn;

	@Column(name = "check_out", nullable = false)
	private Instant checkOut;

}
