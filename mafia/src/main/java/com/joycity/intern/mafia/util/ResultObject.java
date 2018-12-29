package com.joycity.intern.mafia.util;

import lombok.Data;

@Data
public class ResultObject {
	String result;	//value is only success or fail
	Object body;
	
	public ResultObject() {
		
	}
	
	public ResultObject(Builder builder) {
		this.result = builder.result;
		this.body = builder.body;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	
	public static class Builder {
		String result;
		Object body;
		
		public Builder setResult(String result) {
			this.result = result;
			return this;
		}
		
		public Builder setBody(Object body) {
			this.body = body;
			return this;
		}
		
		public ResultObject build() {
			return new ResultObject(this);
		}
		
	}
	
	
}
