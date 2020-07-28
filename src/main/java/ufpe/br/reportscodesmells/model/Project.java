package ufpe.br.reportscodesmells.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Project {
	private String name;
	//numbers
	private String total_classes;
	private String referenced_classes;
	private String total_bugs;
	private String total_size;
	private String num_packages;
	private String priority_2;
	private String priority_1;
	private List<CodeSmellsReported> codeSmellReported;
	
	public Project(String name, String total_classes, String referenced_classes, String total_bugs,
			String total_size, String num_packages, String priority_2, String priority_1) {
		super();
		this.name = name;
		this.total_classes = total_classes;
		this.referenced_classes = referenced_classes;
		this.total_bugs = total_bugs;
		this.total_size = total_size;
		this.num_packages = num_packages;
		this.priority_2 = priority_2;
		this.priority_1 = priority_1;
		instanceCodeSmellReported();
	}

	private void instanceCodeSmellReported() {
		this.codeSmellReported = new ArrayList<CodeSmellsReported>();
		this.codeSmellReported.add(new CodeSmellsReported(Detector.EAGER_SMELL, 0, this));
		this.codeSmellReported.add(new CodeSmellsReported(Detector.N1JOINFETCH_SMELL, 0, this));
		this.codeSmellReported.add(new CodeSmellsReported(Detector.ONETOMANYCOLLECTIONS_SMELL, 0, this));
		this.codeSmellReported.add(new CodeSmellsReported(Detector.PROJECTION_SMELL, 0, this));
	}

	@Override
	public String toString() {
		return "Project [name=" + name + ", total_classes=" + total_classes + ", referenced_classes="
				+ referenced_classes + ", total_bugs=" + total_bugs + ", total_size=" + total_size + ", num_packages="
				+ num_packages + ", priority_2=" + priority_2 + ", priority_1=" + priority_1 + ", EAGER_SMELL="
				+ codeSmellReported.get(0).getQtd() + ", N1JOINFETCH_SMELL="
						+ codeSmellReported.get(1).getQtd() + ", ONETOMANYCOLLECTIONS_SMELL="
								+ codeSmellReported.get(2).getQtd() + ", PROJECTION_SMELL="
										+ codeSmellReported.get(3).getQtd() +   "]";
	}
}
