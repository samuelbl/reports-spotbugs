package ufpe.br.reportscodesmells.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.swing.plaf.synth.SynthSeparatorUI;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Classe responsável por métodos utilitários inerentes a leitura do XML
 * @author samuel, eliandro
 *
 */

@Service(value = "util")
public class Util {
	
	private DocumentBuilderFactory factory; 
	private XPath xpath;
	Document document; 
	
	public Util() {
		//inicia variáveis no construtor
		factory = DocumentBuilderFactory.newInstance();
		xpath = XPathFactory.newInstance().newXPath();
	}

	/**
	 * Realiza o dowload do documento conforme url passada e retorna um Document
	 * @param url
	 * @return
	 * @throws ParserConfigurationException
	 * @throws MalformedURLException
	 * @throws SAXException
	 * @throws IOException
	 */
	public Document baixaDocumentoXML(String url) throws ParserConfigurationException, MalformedURLException, SAXException, IOException {
		DocumentBuilder builder = factory.newDocumentBuilder();
		document = builder.parse(new File(url));
		return document;
	}

	/**
	 * Busca dados no XML através do xpath
	 * @param string
	 * @return
	 * @throws XPathExpressionException
	 */
	public Optional<String> buscaNoXML(String string) throws XPathExpressionException {
		return Optional.of(xpath.evaluate(string, document));
	}
	
	/**
	 * Busca dados no XML através do xpath mas utilizando um node
	 * @param string
	 * @param node
	 * @return
	 * @throws XPathExpressionException
	 */
	public Optional<String> buscaNoXML(String string, Node node) throws XPathExpressionException {
		return Optional.of((String)xpath.evaluate(string, node, XPathConstants.STRING));
	}
	
	 /**
	 * Retorna um Stream de node a partir de um caminho do xml
	 * @param string
	 * @return
	 * @throws XPathExpressionException
	 */
	public Stream<Node> buscaNoXMLList(String string) throws XPathExpressionException {
		NodeList nodeList = (NodeList) xpath.evaluate(string, document, XPathConstants.NODESET);
		Stream<Node> nodeStream = IntStream.range(0, nodeList.getLength())
                .mapToObj(nodeList::item);
		return nodeStream;
	}
	
	/**
	 * Método para baixar arquivos no computador
	 * @param stringUrl
	 * @param pathLocal
	 * @param nomeArquivoLocal
	 * @return
	 */
	public File baixarArquivos(String stringUrl, String pathLocal, String nomeArquivoLocal) {
		String fileName = stringUrl.substring( stringUrl.lastIndexOf('/')+1, stringUrl.length() );
		try (FileOutputStream fos = new FileOutputStream(pathLocal+"/"+fileName)) {
			URL url = new URL(stringUrl);
			//Cria streams de leitura e de escrita
			InputStream is = url.openStream();
			//Le e grava 	
			int umByte = 0;
			while ((umByte = is.read()) != -1){
				fos.write(umByte);
			}
			return new File(pathLocal+"/"+fileName);
		} catch (Exception e) {
			System.out.println("Ocorreu um erro ao baixar o arquivo = " +nomeArquivoLocal + "favor tentar novamente");
		}
		return null;
	}
	

	/**
	 * Método para validação do formato de entrada de data
	 * @param format
	 * @param value
	 * @param locale
	 */
	public boolean isValidFormat(String value) {
		DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
	    if (value ==null){
	    	return false;
	    }
	    try {
	    	LocalDate ld = LocalDate.parse(value, fomatter);
	        String result = ld.format(fomatter);
	        return result.equals(value);
	    } catch (DateTimeParseException e) {
	       System.out.println("Formato inválido, digite a data no formato dd/mm/yyyy");
	    }
	    return false;
		
	}
	
	/**
	 * Método para conversão de uma String de data em Instant
	 * @param value
	 * @param inicial
	 * @return
	 */
	public Instant convertDateToInstant(String value, boolean inicial) {
		Instant dataConvertida = null;
	    DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
	    try {
	    	LocalDate ld = LocalDate.parse(value, fomatter);
	    	//Verifica se é a data inicial ou final, para utilizar as primeiras ou últimas horas do dia
	    	if (inicial)
	    		dataConvertida = Instant.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
	    	else
	    		dataConvertida = Instant.from(ld.atTime(LocalTime.MAX).toInstant(OffsetDateTime.now().getOffset()));
	    } catch (DateTimeParseException e) {
	       System.out.println("Ocorreu erro na conversão de datas");
	    }
		return dataConvertida;
	}
	
	/**
	 * Método utilizado para converter Optional<String> para Instant
	 * @param dataString
	 * @return Instant
	 */
	public Instant converteStringToInstant(Optional<String> dataString) {
		return DateTimeFormatter.RFC_1123_DATE_TIME.parse(dataString.orElse("Sun, 01 Jan 0000 00:00:00 GMT"),Instant::from);
	}


	
	
}
