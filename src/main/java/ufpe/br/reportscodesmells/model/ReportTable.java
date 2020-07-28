package ufpe.br.reportscodesmells.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReportTable {
	private Detector smell;
	private Integer totalInstances;
	private Integer affectedProjects;
	private Integer min;
	private Integer med;
	private Integer max;
	
	public ReportTable(Detector smell, Integer totalInstances, Integer affectedProjects, Integer min, Integer med,
			Integer max) {
		super();
		this.smell = smell;
		this.totalInstances = totalInstances;
		this.affectedProjects = affectedProjects;
		this.min = min;
		this.med = med;
		this.max = max;
	}
	
	public void addTotalInstances(Integer qtd) {
		this.totalInstances = this.totalInstances+qtd;
	}
	
	public void addaffectedProjects() {
		this.affectedProjects++;
	}

	@Override
	public String toString() {
		return "ReportTable [smell=" + smell + ", totalInstances=" + totalInstances + ", affectedProjects="
				+ affectedProjects + ", min=" + min + ", med=" + med + ", max=" + max + "]";
	}
	
	
}
