package io.github.hsyyid.essentialcmds.utils;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextBuilder;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandMessageFormatting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaginatedList
{

	private List<Text> items = new ArrayList<>();
	private Text header;
	private Text footer;
	private String command;
	private int itemsPerPage = PaginatedListUtil.BEST_FIT_WITH_HEADER;

	// Style options - pagination
	private char paginationType = PaginatedListUtil.PAGINATION_TYPE_DASH;
	private TextColor clickableLinkColor = TextColors.AQUA;
	private TextColor nonClickableLinkColor = TextColors.DARK_GRAY;
	private TextColor pageNumberColor = TextColors.GOLD;
	private TextColor paginationColor = TextColors.WHITE;

	// Style options - line numbers
	private boolean displayLineNumbers = true;
	private String lineNumberType = PaginatedListUtil.LINE_NUMBER_TYPE_PARENTHESIS;
	private TextColor lineNumberColor = TextColors.WHITE;

	public boolean add(Text text)
	{
		return this.items.add(text);
	}

	public boolean add(Text... texts)
	{
		List<Text> temp = new ArrayList<>();
		Collections.addAll(temp, texts);
		return this.addAll(temp);
	}

	public boolean addAll(List<Text> texts)
	{
		return this.items.addAll(texts);
	}

	public boolean remove(Text text)
	{
		return this.items.remove(text);
	}

	public boolean remove(Text... texts)
	{
		List<Text> temp = new ArrayList<>();
		Collections.addAll(temp, texts);
		return this.items.removeAll(temp);
	}

	public boolean removeAll(List<Text> texts)
	{
		return this.removeAll(texts);
	}

	public void clear()
	{
		this.items.clear();
	}

	public int size()
	{
		return this.items.size();
	}

	public boolean contains(Text text)
	{
		return this.items.contains(text);
	}

	public Text get(int index)
	{
		return this.items.get(index);
	}

	public List<Text> subList(int fromIndex, int toIndex)
	{
		int from = (this.size() > fromIndex) ? fromIndex : this.size();
		int to = (this.size() > toIndex) ? toIndex : this.size();
		return this.items.subList(from, to);
	}

	public int indexOf(Text text)
	{
		return this.items.indexOf(text);
	}

	public Text getHeader()
	{
		return this.header;
	}

	public void setHeader(Text header)
	{
		this.header = header;
	}

	public Text getFooter()
	{
		return this.footer;
	}

	public void setFooter(Text footer)
	{
		this.footer = footer;
	}

	public String getCommand()
	{
		return this.command;
	}

	public void setCommand(String command)
	{
		this.command = command;
	}

	public void setItemsPerPage(int itemsPerPage)
	{
		this.itemsPerPage = itemsPerPage;
	}

	public int getItemsPerPage()
	{
		return this.itemsPerPage;
	}

	public int getTotalPages()
	{
		return (int) Math.ceil((double) this.size() / (double) this.getItemsPerPage());
	}

	public char getPaginationType()
	{
		return this.paginationType;
	}

	public void setPaginationType(char type)
	{
		this.paginationType = type;
	}

	public TextColor getClickableLinkColor()
	{
		return this.clickableLinkColor;
	}

	public void setClickableLinkColor(TextColor color)
	{
		this.clickableLinkColor = color;
	}

	public TextColor getNonClickableLinkColor()
	{
		return this.nonClickableLinkColor;
	}

	public void setNonClickableLinkColor(TextColor color)
	{
		this.nonClickableLinkColor = color;
	}

	public TextColor getPageNumberColor()
	{
		return this.pageNumberColor;
	}

	public void setPageNumberColor(TextColor color)
	{
		this.pageNumberColor = color;
	}

	public TextColor getPaginationColor()
	{
		return this.paginationColor;
	}

	public void setPaginationColor(TextColor color)
	{
		this.paginationColor = color;
	}

	public void displayLineNumbers(boolean display)
	{
		this.displayLineNumbers = display;
	}

	public boolean displayLineNumbers()
	{
		return this.displayLineNumbers;
	}

	public String getLineNumberType()
	{
		return this.lineNumberType;
	}

	public void setLineNumberType(String type)
	{
		this.lineNumberType = type;
	}

	public TextColor getLineNumberColor()
	{
		return this.lineNumberColor;
	}

	public void setLineNumberColor(TextColor color)
	{
		this.lineNumberColor = color;
	}

	/**
	 * Returns a Text object that contains a paginated list
	 *
	 * @param pageNumber int
	 * @return Text
	 */
	public Text getPage(int pageNumber)
	{

		int currentPage = (pageNumber <= this.getTotalPages()) ? pageNumber : this.getTotalPages();
		int startIndex = (currentPage - 1) * this.itemsPerPage;
		int itemIndex = startIndex + 1;

		TextBuilder list = Texts.builder();
		List<Text> items = this.subList(startIndex, startIndex + this.itemsPerPage);

		if (this.header != null)
		{
			list.append(this.header);
		}

		list.append(CommandMessageFormatting.NEWLINE_TEXT);

		for (Text item : items)
		{

			if (this.displayLineNumbers)
			{
				list.append(Texts.of(this.lineNumberColor, (itemIndex < 10) ?
					"0" + itemIndex + this.lineNumberType :
					Integer.toString(itemIndex) + this.lineNumberType));
			}

			list.append(item);
			list.append(CommandMessageFormatting.NEWLINE_TEXT);

			itemIndex++;
		}

		list.append(CommandMessageFormatting.NEWLINE_TEXT);

		list.append(Texts.of(this.paginationColor, fill(18, this.paginationType)));
		list.append(getPrevLinks(currentPage));
		list.append(Texts.of(this.pageNumberColor, " " + currentPage + " "));
		list.append(getNextLinks(currentPage));
		list.append(Texts.of(this.paginationColor, fill(18, this.paginationType)));

		if (this.footer != null)
		{
			list.append(footer);
		}

		return list.build();
	}

	/**
	 * @param command String
	 */
	public PaginatedList(String command)
	{
		this.items = new ArrayList<>();
		this.command = command;
	}

	/**
	 * @param command      String
	 * @param itemsPerPage int
	 */
	public PaginatedList(String command, int itemsPerPage)
	{
		this.items = new ArrayList<>();
		this.command = command;
		this.itemsPerPage = itemsPerPage;
	}

	private Text getPrevLinks(int currentPage)
	{

		TextBuilder paginationPrev = Texts.builder();

		paginationPrev.append(Texts.of(" "));

		if (currentPage > 2)
		{
			paginationPrev.append(Texts.of(" "), getLink(PaginatedListUtil.PAGINATION_FIRST, 1), Texts.of(" "));
		}
		else
		{
			paginationPrev.append(Texts.of(this.nonClickableLinkColor, " " + PaginatedListUtil.PAGINATION_FIRST + " "));
		}

		if (currentPage > 1)
		{
			paginationPrev.append(Texts.of(" "), getLink(PaginatedListUtil.PAGINATION_BACK, (currentPage - 1)), Texts.of(" "));
		}
		else
		{
			paginationPrev.append(Texts.of(this.nonClickableLinkColor, " " + PaginatedListUtil.PAGINATION_BACK + " "));
		}

		return paginationPrev.build();

	}

	private Text getNextLinks(int currentPage)
	{

		TextBuilder paginationNext = Texts.builder();

		if (currentPage < this.getTotalPages())
		{
			paginationNext.append(Texts.of(" "), getLink(PaginatedListUtil.PAGINATION_NEXT, (currentPage + 1)), Texts.of(" "));
		}
		else
		{
			paginationNext.append(Texts.of(this.nonClickableLinkColor, " " + PaginatedListUtil.PAGINATION_NEXT + " "));
		}

		if (currentPage < (this.getTotalPages() - 1))
		{
			paginationNext.append(Texts.of(" "), getLink(PaginatedListUtil.PAGINATION_LAST, this.getTotalPages()), Texts.of(" "));
		}
		else
		{
			paginationNext.append(Texts.of(this.nonClickableLinkColor, " " + PaginatedListUtil.PAGINATION_LAST + " "));
		}

		return paginationNext.build();

	}

	private Text getLink(String preview, int page)
	{

		return Texts.builder(preview)
			.onClick(TextActions.runCommand(this.command + " " + page))
			.onHover(TextActions.showText(Texts.of(TextColors.WHITE, "Go to page ", TextColors.GOLD, page)))
			.color(this.clickableLinkColor)
			.build();

	}

	private String fill(int length, char character)
	{
		return new String(new char[length]).replace('\0', character);
	}

}
