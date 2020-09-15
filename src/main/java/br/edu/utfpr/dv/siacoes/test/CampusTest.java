package br.edu.utfpr.dv.siacoes.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import br.edu.utfpr.dv.siacoes.model.Campus;

public class CampusTest {
	
	private final Campus camp = new Campus();
	
	@Test
	void testGetSet() {
		camp.setName("CP");
		assertEquals("CP", camp.getName());
	}
	
	@Test
	void testBoolean() {
		camp.setActive(false);
		assertFalse(camp.isActive());
	}

}
