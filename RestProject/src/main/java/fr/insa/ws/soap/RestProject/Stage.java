package fr.insa.ws.soap.RestProject;

public class Stage {
	private int evaluation;
	private String competences;
	private int année;
	
	public int getEvaluation() {
		return evaluation;
	}
	public void SetEvaluation(int evaluation) {
		this.evaluation=evaluation;
	}
	public String getCompetence() {
		return competences;
	}
	public void SetCompetence(String competences) {
		this.competences=competences;
	}
	public int getAnnée() {
		return année;
	}
	public void SetAnnée(int année) {
		this.année=année;
	}
}
