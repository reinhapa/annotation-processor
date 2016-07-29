/**
 * File Name: JUnit4ClassProcessorTest.java
 * 
 * Copyright (c) 2016 BISON Schweiz AG, All Rights Reserved.
 */

package net.reini.javac;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Patrick Reinhart
 */
@SuppressWarnings("boxing")
public class JUnit4ClassProcessorTest {
	private ProcessingEnvironment processingEnv;
	private RoundEnvironment roundEnv;
	private Set<? extends TypeElement> annotations;
	private JUnit4ClassProcessor processor;

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		processingEnv = createMock(ProcessingEnvironment.class);
		roundEnv = createMock(RoundEnvironment.class);
		annotations = new HashSet<>();

		processor = new JUnit4ClassProcessor();
		processor.init(processingEnv);
	}

	/**
	 * Test method for
	 * {@link JUnit4ClassProcessor#process(Set, RoundEnvironment)}.
	 */
	@Test
	public void testProcessSetOfQextendsTypeElementRoundEnvironment_processingOver() {
		expect(roundEnv.processingOver()).andReturn(true);

		replay(processingEnv, roundEnv);
		assertFalse(processor.process(annotations, roundEnv));
		verify(processingEnv, roundEnv);
	}

	/**
	 * Test method for
	 * {@link JUnit4ClassProcessor#process(Set, RoundEnvironment)}.
	 */
	@Test
	public void testProcessSetOfQextendsTypeElementRoundEnvironment() {
		expect(roundEnv.processingOver()).andReturn(false);
		expect(roundEnv.getRootElements()).andReturn(new HashSet<>());

		replay(processingEnv, roundEnv);
		assertFalse(processor.process(annotations, roundEnv));
		verify(processingEnv, roundEnv);
	}

	/**
	 * Test method for
	 * {@link JUnit4ClassProcessor#processTestClass(String, Element, RoundEnvironment)}.
	 */
	@Test
	@Ignore
	public void testProcessTestClass() {
		replay(processingEnv, roundEnv);
		fail("Not yet implemented");
		verify(processingEnv, roundEnv);
	}

	/**
	 * Test method for
	 * {@link JUnit4ClassProcessor#processTestMethod(String, Element, RoundEnvironment)}.
	 */
	@Test
	@Ignore
	public void testProcessTestMethod() {
		replay(processingEnv, roundEnv);
		fail("Not yet implemented");
		verify(processingEnv, roundEnv);
	}

	/**
	 * Test method for
	 * {@link JUnit4ClassProcessor#containsTestAnnotation(List)}.
	 */
	@Test
	@Ignore
	public void testContainsTestAnnotation() {
		replay(processingEnv, roundEnv);
		fail("Not yet implemented");
		verify(processingEnv, roundEnv);
	}
}
