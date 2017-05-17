package com.tn.automation.util;

public class CaseResult {
	
	boolean key = true;
	String msg = "OK";
	public boolean getKey() {
		return key;
	}
	public void setKey(boolean key) {
		this.key = key;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public void reset() {
		this.key = true;
		this.msg = "OK";
	}

}
