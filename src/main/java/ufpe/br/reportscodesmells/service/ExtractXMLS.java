package ufpe.br.reportscodesmells.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import ufpe.br.reportscodesmells.model.CodeSmellsReported;
import ufpe.br.reportscodesmells.model.Detector;
import ufpe.br.reportscodesmells.model.Project;
import ufpe.br.reportscodesmells.model.ReportTable;
import ufpe.br.reportscodesmells.util.Util;

@Service
@DependsOn("util")
public class ExtractXMLS {

//	@Autowired
//	private Util util;

	public ExtractXMLS() throws XPathExpressionException {
		Util util = new Util();
		String urlfolder = "path";
		List<ReportTable> listReportTable = contructListOfReportTable();
		ArrayList<Project> projects = new ArrayList<Project>();
		File folder = new File(urlfolder);
	    File[] listOfFiles = folder.listFiles();
	    for(int i = 0; i < listOfFiles.length; i++){
	        String url = listOfFiles[i].getPath();
	        if(url.endsWith(".xml")||url.endsWith(".XML")) {
	        	projects.add(parseXMLtoProject(util, url));	
	        }
	    }
		for (Project project : projects) {
			for (CodeSmellsReported codeSmellReported : project.getCodeSmellReported()) {
				for (ReportTable reportTable : listReportTable) {
					if (reportTable.getSmell().equals(codeSmellReported.getDetector()) && codeSmellReported.getQtd()!=0) {
						reportTable.addTotalInstances(codeSmellReported.getQtd());
						reportTable.addaffectedProjects();
					}
				}
			}
		}
		System.out.println(listReportTable);
	}


	private List<ReportTable> contructListOfReportTable() {
		ArrayList<ReportTable> listReportTable = new ArrayList<ReportTable>();
		listReportTable.add(new ReportTable(Detector.EAGER_SMELL, 0, 0, 0, 0, 0));
		listReportTable.add(new ReportTable(Detector.N1JOINFETCH_SMELL, 0, 0, 0, 0, 0));
		listReportTable.add(new ReportTable(Detector.ONETOMANYCOLLECTIONS_SMELL, 0, 0, 0, 0, 0));
		listReportTable.add(new ReportTable(Detector.PROJECTION_SMELL, 0, 0, 0, 0, 0));
		return listReportTable;
	}

	private Project parseXMLtoProject(Util util, String url) throws XPathExpressionException {
		// Efetua download do XML
		try {
			util.baixaDocumentoXML(url);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			System.out.println("erro");
		}
		Project project = new Project(util.buscaNoXML("BugCollection/Project/@projectName").orElse("nulo"), util.buscaNoXML("BugCollection/FindBugsSummary/@total_classes").orElse(null), util.buscaNoXML("BugCollection/FindBugsSummary/@referenced_classes").orElse(null), util.buscaNoXML("BugCollection/FindBugsSummary/@total_bugs").orElse(null), util.buscaNoXML("BugCollection/FindBugsSummary/@total_size").orElse(null), util.buscaNoXML("BugCollection/FindBugsSummary/@num_packages").orElse(null), util.buscaNoXML("BugCollection/FindBugsSummary/@priority_2").orElse(null), util.buscaNoXML("BugCollection/FindBugsSummary/@priority_1").orElse(null));
		Stream<Node> nodeStream = util.buscaNoXMLList("BugCollection/BugInstance");
		nodeStream.forEach((node)-> {
			try {
				switch (util.buscaNoXML("@type",node).orElse("NULO")) {
				case "EAGER_SMELL":
					project.getCodeSmellReported().get(0).addQtd();
					break;
				case "N1JOINFETCH_SMELL":
					project.getCodeSmellReported().get(1).addQtd();
					break;
				case "ONETOMANYCOLLECTIONS_SMELL":
					project.getCodeSmellReported().get(2).addQtd();
					break;
				case "PROJECTION_SMELL":
					project.getCodeSmellReported().get(3).addQtd();
					break;
				}
			} catch (XPathExpressionException e) {
				e.printStackTrace();
				System.out.println("Erro ao ler o XML dos itens, favor tentar novamente");
			}
		});
		return project;
	}

}
