package com.samset.expadablelistview.model;

import java.util.ArrayList;

public class ParentItem
{

	private String parenttitle;

	private String checkedtype;
	
	private boolean checked;
	private ArrayList<Child> children;

	public String getParenttitle()
	{
		return parenttitle;
	}
	
	public void setParenttitle(String parenttitle)
	{
		this.parenttitle = parenttitle;
	}

	public String getCheckedType()
	{
		return checkedtype;
	}
	
	public void setCheckedType(String checkedtype)
	{
		this.checkedtype = checkedtype;
	}
	
	
	public boolean isChecked()
	{
		return checked;
	}
	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}
	
	public ArrayList<Child> getChildren()
	{
		return children;
	}
	
	public void setChildren(ArrayList<Child> children)
	{
		this.children = children;
	}
}
