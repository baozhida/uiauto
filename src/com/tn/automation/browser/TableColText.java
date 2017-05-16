package com.tn.automation.browser;

import com.tn.automation.util.MatchMode;

public class TableColText
{
    private int colIndex;
    
    private String text;
    
    private MatchMode mode;
    
    private TableColText(int colIndex, String text, MatchMode mode)
    {
        this.colIndex = colIndex;
        this.text = text;
        this.mode = mode;
    }

    public static TableColText colText(int colIndex, String text, MatchMode mode)
    {
        return new TableColText(colIndex, text, mode);
    }

    public static TableColText colText(int colIndex, String text)
    {
        return new TableColText(colIndex, text, MatchMode.PartialMatch);
    }

    public int getColIndex()
    {
        return colIndex;
    }

    public void setColIndex(int colIndex)
    {
        this.colIndex = colIndex;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public MatchMode getMode()
    {
        return mode;
    }

    public void setMode(MatchMode mode)
    {
        this.mode = mode;
    }
}