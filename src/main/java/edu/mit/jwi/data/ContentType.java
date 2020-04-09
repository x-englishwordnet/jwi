/* ******************************************************************************
 * Java Wordnet Interface Library (JWI) v2.4.0
 * Copyright (c) 2007-2015 Mark A. Finlayson
 *
 * JWI is distributed under the terms of the Creative Commons Attribution 4.0
 * International Public License, which means it may be freely used for all
 * purposes, as long as proper acknowledgment is made.  See the license file
 * included with this distribution for more details.
 *******************************************************************************/

package edu.mit.jwi.data;

import edu.mit.jwi.data.compare.*;
import edu.mit.jwi.item.*;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.*;

/**
 * A concrete implementation of the {@code IContentType} interface. This class
 * provides the content types necessary for Wordnet in the form of static
 * fields. It is not implemented as an {@code Enum} so that clients may add
 * their own content types by instantiating this class.
 *
 * @param <T> the type of object for the content type
 * @author Mark A. Finlayson
 * @version 2.4.0
 * @since JWI 2.0.0
 */
public class ContentType<T> implements IContentType<T>
{

	public static final ContentType<IIndexWord> INDEX_NOUN = new ContentType<>(DataType.INDEX, POS.NOUN, IndexLineComparator.getInstance());
	public static final ContentType<IIndexWord> INDEX_VERB = new ContentType<>(DataType.INDEX, POS.VERB, IndexLineComparator.getInstance());
	public static final ContentType<IIndexWord> INDEX_ADVERB = new ContentType<>(DataType.INDEX, POS.ADVERB, IndexLineComparator.getInstance());
	public static final ContentType<IIndexWord> INDEX_ADJECTIVE = new ContentType<>(DataType.INDEX, POS.ADJECTIVE, IndexLineComparator.getInstance());
	public static final ContentType<ISynset> DATA_NOUN = new ContentType<>(DataType.DATA, POS.NOUN, DataLineComparator.getInstance());
	public static final ContentType<ISynset> DATA_VERB = new ContentType<>(DataType.DATA, POS.VERB, DataLineComparator.getInstance());
	public static final ContentType<ISynset> DATA_ADVERB = new ContentType<>(DataType.DATA, POS.ADVERB, DataLineComparator.getInstance());
	public static final ContentType<ISynset> DATA_ADJECTIVE = new ContentType<>(DataType.DATA, POS.ADJECTIVE, DataLineComparator.getInstance());
	public static final ContentType<IExceptionEntryProxy> EXCEPTION_NOUN = new ContentType<>(DataType.EXCEPTION, POS.NOUN,
			ExceptionLineComparator.getInstance());
	public static final ContentType<IExceptionEntryProxy> EXCEPTION_VERB = new ContentType<>(DataType.EXCEPTION, POS.VERB,
			ExceptionLineComparator.getInstance());
	public static final ContentType<IExceptionEntryProxy> EXCEPTION_ADVERB = new ContentType<>(DataType.EXCEPTION, POS.ADVERB,
			ExceptionLineComparator.getInstance());
	public static final ContentType<IExceptionEntryProxy> EXCEPTION_ADJECTIVE = new ContentType<>(DataType.EXCEPTION, POS.ADJECTIVE,
			ExceptionLineComparator.getInstance());
	public static final ContentType<ISenseEntry> SENSE = new ContentType<>(DataType.SENSE, null, SenseKeyLineComparator.getInstance());

	// fields set on construction
	private final IDataType<T> fType;
	private final POS fPOS;
	private final ILineComparator fComparator;
	private final String fString;
	private final Charset fCharset;

	/**
	 * Constructs a new ContentType
	 *
	 * @param type       the data type for this content type, may not be
	 *                   <code>null</code>
	 * @param pos        the part of speech for this content type; may be null if the
	 *                   content type has no natural part of speech
	 * @param comparator the line comparator for this content type; may be
	 *                   <code>null</code> if the lines are not ordered
	 * @since JWI 2.0.0
	 */
	public ContentType(IDataType<T> type, POS pos, ILineComparator comparator)
	{
		// for 2.4.0 we introduce a redirect for end-users of the original constructor
		this(type, pos, comparator, null);
	}

	/**
	 * Constructs a new ContentType
	 *
	 * @param type       the data type for this content type, may not be
	 *                   <code>null</code>
	 * @param pos        the part of speech for this content type; may be null if the
	 *                   content type has no natural part of speech
	 * @param comparator the line comparator for this content type; may be
	 *                   <code>null</code> if the lines are not ordered
	 * @param charset    the character set for this content type, may be
	 *                   <code>null</code>
	 * @since JWI 2.3.4
	 */
	public ContentType(IDataType<T> type, POS pos, ILineComparator comparator, Charset charset)
	{
		if (type == null)
			throw new NullPointerException();

		fType = type;
		fPOS = pos;
		fComparator = comparator;
		fCharset = charset;

		if (pos != null)
		{
			fString = "[ContentType: " + fType.toString() + "/" + fPOS.toString() + "]";
		}
		else
		{
			fString = "[ContentType: " + fType.toString() + "]";
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see edu.mit.jwi.data.IContentType#getDataType()
	 */
	public IDataType<T> getDataType()
	{
		return fType;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see edu.mit.jwi.item.IHasPOS#getPOS()
	 */
	public POS getPOS()
	{
		return fPOS;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see edu.mit.jwi.data.IContentType#getLineComparator()
	 */
	public ILineComparator getLineComparator()
	{
		return fComparator;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see edu.mit.jwi.data.IHasCharset#getCharset()
	 */
	public Charset getCharset()
	{
		return fCharset;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return fString;
	}

	// set of all content types implemented in this class
	private static final Set<ContentType<?>> contentTypes;

	// initialization for static content type set
	static
	{

		// get all the fields containing ContentType
		Field[] fields = ContentType.class.getFields();
		List<Field> instanceFields = new ArrayList<>();
		for (Field field : fields)
			if (field.getType() == ContentType.class)
				instanceFields.add(field);

		// this is the backing set
		Set<ContentType<?>> hidden = new LinkedHashSet<>(instanceFields.size());

		// fill in the backing set
		ContentType<?> contentType;
		for (Field field : instanceFields)
		{
			try
			{
				contentType = (ContentType<?>) field.get(null);
				if (contentType == null)
					continue;
				hidden.add(contentType);
			}
			catch (IllegalAccessException e)
			{
				// Ignore
			}
		}

		// make the value set unmodifiable
		contentTypes = Collections.unmodifiableSet(hidden);
	}

	/**
	 * Emulates the Enum.values() function.
	 *
	 * @return all the static ContentType instances listed in the class, in the
	 * order they are declared.
	 * @since JWI 2.0.0
	 */
	public static Collection<ContentType<?>> values()
	{
		return contentTypes;
	}

	/**
	 * Use this convenience method to retrieve the appropriate
	 * {@code IIndexWord} content type for the specified POS.
	 *
	 * @param pos the part of speech for the content type, may not be
	 *            <code>null</code>
	 * @return the index content type for the specified part of speech
	 * @throws NullPointerException if the specified part of speech is <code>null</code>
	 * @since JWI 2.0.0
	 */
	public static IContentType<IIndexWord> getIndexContentType(POS pos)
	{
		if (pos == null)
			throw new NullPointerException();
		switch (pos)
		{
			case NOUN:
				return INDEX_NOUN;
			case VERB:
				return INDEX_VERB;
			case ADVERB:
				return INDEX_ADVERB;
			case ADJECTIVE:
				return INDEX_ADJECTIVE;
		}
		throw new IllegalStateException("This should not happen.");
	}

	/**
	 * Use this convenience method to retrieve the appropriate
	 * {@code ISynset} content type for the specified POS.
	 *
	 * @param pos the part of speech for the content type, may not be
	 *            <code>null</code>
	 * @return the index content type for the specified part of speech
	 * @throws NullPointerException if the specified part of speech is <code>null</code>
	 * @since JWI 2.0.0
	 */
	public static IContentType<ISynset> getDataContentType(POS pos)
	{
		if (pos == null)
			throw new NullPointerException();
		switch (pos)
		{
			case NOUN:
				return DATA_NOUN;
			case VERB:
				return DATA_VERB;
			case ADVERB:
				return DATA_ADVERB;
			case ADJECTIVE:
				return DATA_ADJECTIVE;
		}
		throw new IllegalStateException("How in the world did we get here?");
	}

	/**
	 * Use this convenience method to retrieve the appropriate
	 * {@code IExceptionEntryProxy} content type for the specified POS.
	 *
	 * @param pos the part of speech for the content type, may not be
	 *            <code>null</code>
	 * @return the index content type for the specified part of speech
	 * @throws NullPointerException if the specified part of speech is <code>null</code>
	 * @since JWI 2.0.0
	 */
	public static IContentType<IExceptionEntryProxy> getExceptionContentType(POS pos)
	{
		if (pos == null)
			throw new NullPointerException();
		switch (pos)
		{
			case NOUN:
				return EXCEPTION_NOUN;
			case VERB:
				return EXCEPTION_VERB;
			case ADVERB:
				return EXCEPTION_ADVERB;
			case ADJECTIVE:
				return EXCEPTION_ADJECTIVE;
		}
		throw new IllegalStateException("Great Scott, there's been a rupture in the space-time continuum!");
	}

}