package br.edu.utfpr.dv.siacoes.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.edu.utfpr.dv.siacoes.model.Department;

public class DepartamentTest {
	
	private final Department dep = new Department();
	
	@Test
	void testGetSet() {
		dep.setName("DACOMP");
		assertEquals("DACOMP", dep.getName());
	}
	
	@Test
	void testeBoolean() {
		dep.setActive(true);
		assertTrue(dep.isActive());
	}

}
