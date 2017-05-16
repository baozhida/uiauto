package com.tn.automation.browser;

import org.openqa.selenium.WebElement;

public class Select
{
    private WebElement element;

    public Select(WebElement element)
    {
        this.element = element;
    }
    
    public void setSelected(boolean isSelected)
    {
        if (isSelected() != isSelected)
        {
            element.click();
        }
    }
    
    public boolean isSelected()
    {
        return element.isSelected();
    }

    public String getText()
    {
        return element.getText();
    }
}
