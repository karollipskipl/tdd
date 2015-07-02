package com.pl.karollipski.template;

import com.pl.karollipski.common.ThrowableCaptor;
import com.pl.karollipski.template.exception.MissingValueException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestTemplate {

    private Template template;

    @Before
    public void setUp() throws Exception {
        template = new Template("${one}, ${two}, ${three}");
        template.set("one", "1");
        template.set("two", "2");
        template.set("three", "3");
    }

    @Test
    public void multipleVariables() throws Exception {
        assertTamplateEvaluatesTo("1, 2, 3");
    }

    @Test
    public void unknownVariablesAreIgnored() throws Exception {
        template.set("doesnotexist", "Hi");
        assertTamplateEvaluatesTo("1, 2, 3");
    }

    @Test
    public void missingValueRaisesException() throws Exception {
        template = new Template("${foo}");
        Throwable expected = ThrowableCaptor.captureThrowable(() -> template.evaluate());
        assertEquals("No value for ${foo}", expected.getMessage());
    }

    @Test
    public void variablesGetProcessedJustOnce() throws Exception {
        template.set("one", "${one}");
        template.set("two", "${three}");
        template.set("three", "${two}");
        assertTamplateEvaluatesTo("${one}, ${three}, ${two}");
    }

    private void assertTamplateEvaluatesTo(String expected) {
        assertEquals(expected, template.evaluate());
    }
}
