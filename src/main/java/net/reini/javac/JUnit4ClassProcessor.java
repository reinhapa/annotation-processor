/**
 * File Name: JUnit4ClassProcessor.java
 * 
 * Copyright (c) 2016 BISON Schweiz AG, All Rights Reserved.
 */

package net.reini.javac;

import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

/**
 * @author Patrick Reinhart
 */
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class JUnit4ClassProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if (!roundEnv.processingOver()) {
			for (Element element : roundEnv.getRootElements()) {
				String className = element.getSimpleName().toString();
				if (element.getKind() == ElementKind.CLASS && className.endsWith("Test")) {
					processTestClass(className, element, roundEnv);
				}
			}
		}
		return false;
	}

	void processTestClass(String className, Element classElement, RoundEnvironment roundEnv) {
		for (Element element : classElement.getEnclosedElements()) {
			if (element.getKind() == ElementKind.METHOD && element.getSimpleName().toString().startsWith("test")) {
				processTestMethod(className, element, roundEnv);
			}
		}

	}

	void processTestMethod(String className, Element testMethod, RoundEnvironment roundEnv) {
		// test method without junit annotation
		if (containsTestAnnotation(testMethod.getAnnotationMirrors())) {
			if (!testMethod.getModifiers().contains(Modifier.PUBLIC)) {
				processingEnv.getMessager().printMessage(Kind.WARNING, className + "." + testMethod + " is not public");
			}
		} else if (testMethod.getModifiers().contains(Modifier.PUBLIC)) {
			processingEnv.getMessager().printMessage(Kind.WARNING,
					className + "." + testMethod + " has no @Test annotation");
		} else {
			processingEnv.getMessager().printMessage(Kind.NOTE, className + "." + testMethod
					+ "  could be a possible test method but it is not public and has no @Test annotation");
		}
	}

	boolean containsTestAnnotation(List<? extends AnnotationMirror> list) {
		return list.stream().anyMatch(a -> a.toString().equals("@org.junit.Test"));
	}

}
