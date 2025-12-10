package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = ProviderTeamAllotedWards.TABLE_NAME)
public class ProviderTeamAllotedWards {
    public static final String TABLE_NAME = "backend_providerteamallotedwards";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "monday", nullable = false)
    private Boolean monday;

    @Column(name = "tuesday", nullable = false)
    private Boolean tuesday;

    @Column(name = "wednesday", nullable = false)
    private Boolean wednesday;

    @Column(name = "thursday", nullable = false)
    private Boolean thursday;

    @Column(name = "friday", nullable = false)
    private Boolean friday;

    @Column(name = "saturday", nullable = false)
    private Boolean saturday;

    @Column(name = "sunday", nullable = false)
    private Boolean sunday;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private ProviderTeam team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id", nullable = false)
    private Ward ward;

	public ProviderTeamAllotedWards(Long id, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday,
			Boolean friday, Boolean saturday, Boolean sunday, LocalDateTime createdAt, ProviderTeam team, Ward ward) {
		super();
		this.id = id;
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;
		this.createdAt = createdAt;
		this.team = team;
		this.ward = ward;
	}
    public ProviderTeamAllotedWards() {
    	
    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getMonday() {
		return monday;
	}
	public void setMonday(Boolean monday) {
		this.monday = monday;
	}
	public Boolean getTuesday() {
		return tuesday;
	}
	public void setTuesday(Boolean tuesday) {
		this.tuesday = tuesday;
	}
	public Boolean getWednesday() {
		return wednesday;
	}
	public void setWednesday(Boolean wednesday) {
		this.wednesday = wednesday;
	}
	public Boolean getThursday() {
		return thursday;
	}
	public void setThursday(Boolean thursday) {
		this.thursday = thursday;
	}
	public Boolean getFriday() {
		return friday;
	}
	public void setFriday(Boolean friday) {
		this.friday = friday;
	}
	public Boolean getSaturday() {
		return saturday;
	}
	public void setSaturday(Boolean saturday) {
		this.saturday = saturday;
	}
	public Boolean getSunday() {
		return sunday;
	}
	public void setSunday(Boolean sunday) {
		this.sunday = sunday;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public ProviderTeam getTeam() {
		return team;
	}
	public void setTeam(ProviderTeam team) {
		this.team = team;
	}
	public Ward getWard() {
		return ward;
	}
	public void setWard(Ward ward) {
		this.ward = ward;
	}
    
    
    
}
