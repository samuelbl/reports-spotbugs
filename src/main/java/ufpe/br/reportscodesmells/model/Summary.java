package ufpe.br.reportscodesmells.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Summary {
	private Integer total_projects;
	private Integer total_projects_with_bugs;
	private Integer total_classes;
	private Integer referenced_classes;
	private Integer total_bugs;
	private Integer total_size;
	private Integer num_packages;
	private Integer priority_2;
	private Integer priority_1;
	
	public Summary(Integer total_projects, Integer total_projects_with_bugs, Integer total_classes,
			Integer referenced_classes, Integer total_bugs, Integer total_size, Integer num_packages,
			Integer priority_2, Integer priority_1) {
		super();
		this.total_projects = total_projects;
		this.total_projects_with_bugs = total_projects_with_bugs;
		this.total_classes = total_classes;
		this.referenced_classes = referenced_classes;
		this.total_bugs = total_bugs;
		this.total_size = total_size;
		this.num_packages = num_packages;
		this.priority_2 = priority_2;
		this.priority_1 = priority_1;
	}
	
	public Summary() {
		this.total_projects_with_bugs = 0;
		this.total_projects = 0;
		this.total_classes = 0;
		this.referenced_classes = 0;
		this.total_bugs = 0;
		this.total_size = 0;
		this.num_packages = 0;
		this.priority_2 = 0;
		this.priority_1 = 0;
	}
	
	public void addTotais(Boolean projectWithBugs, Integer total_classes,
			Integer referenced_classes, Integer total_bugs, Integer total_size, Integer num_packages,
			Integer priority_2, Integer priority_1) {
		if (!projectWithBugs) {
			this.total_projects_with_bugs++;
		}
		this.total_projects++;
		this.total_classes= this.total_classes+total_classes;
		this.referenced_classes= this.referenced_classes+referenced_classes;
		this.total_bugs = this.total_bugs+total_bugs;
		this.num_packages = this.num_packages+num_packages;
		this.priority_1 = this.priority_1+priority_1;
		this.priority_2 = this.priority_2+priority_2;
	}

	@Override
	public String toString() {
		return "Summary [total_projects=" + total_projects + ", total_projects_with_bugs=" + total_projects_with_bugs
				+ ", total_classes=" + total_classes + ", referenced_classes=" + referenced_classes + ", total_bugs="
				+ total_bugs + ", total_size=" + total_size + ", num_packages=" + num_packages + ", priority_2="
				+ priority_2 + ", priority_1=" + priority_1 + "]";
	}
	
	
}
