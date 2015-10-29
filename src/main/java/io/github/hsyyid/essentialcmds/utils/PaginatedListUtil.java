package io.github.hsyyid.essentialcmds.utils;

public class PaginatedListUtil
{

	// Line number presets
	public static final int BEST_FIT_WITH_HEADER = 7;
	public static final int BEST_FIT_WITH_FOOTER = 7;
	public static final int BEST_FIT_NO_HEADER_OR_FOOTER = 8;
	public static final int BEST_FIT_WITH_HEADER_AND_FOOTER = 6;

	// Line number styles
	public static final String LINE_NUMBER_TYPE_DASH = " - ";
	public static final String LINE_NUMBER_TYPE_PARENTHESIS = ") ";
	public static final String LINE_NUMBER_TYPE_PERIOD = ". ";

	// Pagination styles
	public static final char PAGINATION_TYPE_DASH = '-';
	public static final char PAGINATION_TYPE_UNDERSCORE = '_';
	public static final char PAGINATION_TYPE_TILDA = '~';
	public static final char PAGINATION_TYPE_STAR = '*';
	public static final char PAGINATION_TYPE_HASH = '#';
	public static final char PAGINATION_TYPE_DOUBLELINE = '=';
	public static final char PAGINATION_TYPE_PLUS = '+';
	public static final char PAGINATION_TYPE_NONE = ' ';

	// Pagination links
	public static final String PAGINATION_FIRST = "<<";
	public static final String PAGINATION_BACK = "<";
	public static final String PAGINATION_NEXT = ">";
	public static final String PAGINATION_LAST = ">>";

}
