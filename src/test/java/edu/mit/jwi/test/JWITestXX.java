package edu.mit.jwi.test;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class JWITestXX
{
	// the test involves new is_caused_by
	@Test public void mainTestXX_new_rel() throws IOException
	{
		String wnHome = System.getenv("WNHOMEXX" /* + File.separator + "dict" */);
		boolean result = JWI.run(wnHome, "spread");
		assertTrue(result);
	}

	// the test involves adj
	@Test public void mainTestXX_adj_sat() throws IOException
	{
		String wnHome = System.getenv("WNHOMEXX" /* + File.separator + "dict" */);
		boolean result = JWI.run(wnHome, "small");
		assertTrue(result);
	}

	// the test involves Young (n) and adj
	@Test public void mainTestXX_case() throws IOException
	{
		String wnHome = System.getenv("WNHOMEXX" /* + File.separator + "dict" */);
		boolean result = JWI.run(wnHome, "young");
		assertTrue(result);
	}

	// the test involves galore (a)
	@Test public void mainTestXX_marker() throws IOException
	{
		String wnHome = System.getenv("WNHOMEXX" /* + File.separator + "dict" */);
		boolean result = JWI.run(wnHome, "galore");
		assertTrue(result);
	}
}