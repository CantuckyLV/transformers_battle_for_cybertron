package com.daniel.battleforcybertron.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/**
 * Pojo that represents a transformer. COntains name and stats as well as team
 */

public class Transformer implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("team")
    private String team;
    @SerializedName("strength")
    private int strength;
    @SerializedName("intelligence")
    private int intelligence;
    @SerializedName("speed")
    private int speed;
    @SerializedName("endurance")
    private int endurance;
    @SerializedName("rank")
    private int rank;
    @SerializedName("courage")
    private int courage;
    @SerializedName("firepower")
    private int firepower;
    @SerializedName("skill")
    private int skill;
    @SerializedName("team_icon")
    private String team_icon;

    public Transformer() {
    }

    public Transformer(String id, String name, String team, int strength, int intelligence, int speed, int endurance, int rank, int courage, int firepower, int skill, String team_icon) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.strength = strength;
        this.intelligence = intelligence;
        this.speed = speed;
        this.endurance = endurance;
        this.rank = rank;
        this.courage = courage;
        this.firepower = firepower;
        this.skill = skill;
        this.team_icon = team_icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getCourage() {
        return courage;
    }

    public void setCourage(int courage) {
        this.courage = courage;
    }

    public int getFirepower() {
        return firepower;
    }

    public void setFirepower(int firepower) {
        this.firepower = firepower;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public String getTeam_icon() {
        return team_icon;
    }

    public void setTeam_icon(String team_icon) {
        this.team_icon = team_icon;
    }

    @Override
    public String toString() {
        return "Transformer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", team='" + team + '\'' +
                ", strength=" + strength +
                ", intelligence=" + intelligence +
                ", speed=" + speed +
                ", endurance=" + endurance +
                ", rank=" + rank +
                ", courage=" + courage +
                ", firepower=" + firepower +
                ", skill=" + skill +
                ", team_icon='" + team_icon + '\'' +
                '}';
    }
}
