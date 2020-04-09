package edu.mit.jwi.test;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class JWITestXX_nonascii
{
	@Test public void mainTestXX_contrib() throws IOException
	{
		final String wnHome = System.getenv("WNHOMEXX_contrib" /* + File.separator + "dict" */);
		final URL url = new File(wnHome).toURI().toURL();

		final IDictionary dict = new Dictionary(url);
		dict.setCharset(StandardCharsets.UTF_8);
		dict.open();

		final IIndexWord idx = dict.getIndexWord("Wałęsa", POS.NOUN);
		final List<IWordID> senseids = idx.getWordIDs();
		for (final IWordID senseid : senseids) // synset id, sense number, and lemma
		{
			// sense
			final IWord sense = dict.getWord(senseid);
			System.out.println("● sense = " + sense.toString() + " sensekey=" + sense.getSenseKey());

			// synset
			final ISynsetID synsetid = senseid.getSynsetID();
			final ISynset synset = dict.getSynset(synsetid);
			final String members = getMembers(synset);
			System.out.println("● synset = " + members + synset.getGloss());
			assertTrue(members.contains("Wałęsa"));
			assertTrue(members.contains("Lech_Wałęsa"));
			assertTrue(members.contains("Walesa"));
			assertTrue(members.contains("Lech_Walesa"));
		}
	}

	private String getMembers(final ISynset synset)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append('{');
		boolean first = true;
		for (final IWord sense : synset.getWords())
		{
			if (first)
			{
				first = false;
			}
			else
			{
				sb.append(' ');
			}
			sb.append(sense.getLemma());
		}
		sb.append('}');
		sb.append(' ');
		return sb.toString();
	}
}