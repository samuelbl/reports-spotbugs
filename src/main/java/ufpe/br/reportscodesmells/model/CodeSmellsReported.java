package ufpe.br.reportscodesmells.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CodeSmellsReported {
	
	private Detector detector;
	private Integer qtd;
	private Project project;
	
	public CodeSmellsReported(Detector detector, Integer qtd, Project project) {
		super();
		this.detector = detector;
		this.qtd = qtd;
		this.project = project;
	}
	
	public void addQtd() {
		this.qtd++;
	}
}
