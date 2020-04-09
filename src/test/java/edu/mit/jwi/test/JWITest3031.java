package edu.mit.jwi.test;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class JWITest3031
{
	@Test public void mainTest30() throws IOException
	{
		String wnHome = System.getenv("WNHOME30" /* + File.separator + "dict" */);
		boolean result = JWI.run(wnHome, "spread");
		assertTrue(result);
	}

	@Test public void mainTest31() throws IOException
	{
		String wnHome = System.getenv("WNHOME31" /* + File.separator + "dict" */);
		boolean result = JWI.run(wnHome, "spread");
		assertTrue(result);
	}
}