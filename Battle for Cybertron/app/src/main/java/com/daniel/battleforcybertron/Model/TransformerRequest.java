package com.daniel.battleforcybertron.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Pojo that represents a Request body for the API calls
 */
public class TransformerRequest {
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

    public TransformerRequest() {
    }

    public TransformerRequest(Transformer base) {
        this.id = base.getId();
        this.name = base.getName();
        this.team = base.getTeam();
        this.strength = base.getStrength();
        this.intelligence = base.getIntelligence();
        this.speed = base.getSpeed();
        this.endurance = base.getEndurance();
        this.rank = base.getRank();
        this.courage = base.getCourage();
        this.firepower = base.getFirepower();
        this.skill = base.getSkill();
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

    @Override
    public String toString() {
        return "TransformerRequest{" +
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
                '}';
    }
}
