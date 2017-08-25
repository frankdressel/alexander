package de.moduliertersingvogel.alexander.stanford;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ParserEndpointTest {
	private ParserEndpoint out;

	@Before
	public void setUp() {
		this.out=new ParserEndpoint();
	}

	@Test
	public void getNamedEntities() {
		List<String> result=out.getNamedEntities("Wie wird das Wetter in Dresden?");
		assertEquals(1, result.size());
		assertEquals("Dresden", result.get(0));
	}
	
	@Test
	public void getTree() {
		List<String> result=out.getTree("Wie wird das Wetter in Dresden?");
		assertEquals(1, result.size());
		assertEquals("(ROOT (S (PWAV Wie) (VAFIN wird) (NP (ART das) (NN Wetter) (PP (APPR in) (NE Dresden))) ($. ?)))", result.get(0));
	}
}
