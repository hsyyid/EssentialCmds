/*
 * This file is part of EssentialCmds, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2015 - 2015 HassanS6000
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
