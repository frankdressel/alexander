package de.moduliertersingvogel.alexander.stanford;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

@Path("/parser")
public class ParserEndpoint {
	private StanfordCoreNLP pipeline;
	private Logger logger=LogManager.getLogger("alexander");

	public ParserEndpoint() {
		// creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER,
		// parsing, and coreference resolution
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse");
		props.setProperty("tokenize.language", "de");
		props.setProperty("ner.model", "edu\\stanford\\nlp\\models\\ner\\german.conll.hgc_175m_600.crf.ser.gz");
		props.setProperty("pos.model",
				this.getClass().getClassLoader().getResource("edu/stanford/nlp/models/pos-tagger/german/german-fast.tagger").toString());
		props.setProperty("parse.model", "edu\\stanford\\nlp\\models\\lexparser\\germanPCFG.ser.gz");
		props.setProperty("ner.useSUTime", "0");
		this.pipeline = new StanfordCoreNLP(props);
	}

	@POST
	@Path("ner")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<String> getNamedEntities(String text) {
		logger.traceEntry(text);
		List<String> result = new ArrayList<>();

		Annotation document = new Annotation(text);

		pipeline.annotate(document);

		List<CoreMap> sentences = document.get(SentencesAnnotation.class);

		for (CoreMap sentence : sentences) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				String word = token.get(TextAnnotation.class);
				String ne = token.get(NamedEntityTagAnnotation.class);

				if (ne.equalsIgnoreCase("i-loc")) {
					result.add(word);
				}
			}
		}

		logger.traceExit(result.toString());
		return result;
	}

	@POST
	@Path("tree")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<String> getTree(String text) {
		logger.traceEntry(text);
		List<String> result = new ArrayList<>();

		Annotation document = new Annotation(text);

		pipeline.annotate(document);

		List<CoreMap> sentences = document.get(SentencesAnnotation.class);

		for (CoreMap sentence : sentences) {
			// this is the parse tree of the current sentence
			result.add(sentence.get(TreeAnnotation.class).toString());
		}

		logger.traceExit(result.toString());
		return result;
	}
}
