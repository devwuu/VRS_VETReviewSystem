package com.bb.user.dto;

public class Page {

	int startPage;
	int lastPage;
	int maxPage;
	int thisPage;
	boolean prev;
	boolean next;

	
	

	public int getThisPage() {
		return thisPage;
	}

	public void setThisPage(int thisPage) {
		this.thisPage = thisPage;
	}

	public Page(int thisPage, int maxPage) {
		
		this.thisPage = thisPage;
		
		this.maxPage = maxPage;
		
		this.lastPage = (int)Math.ceil((thisPage/5.0))*5;
				
		this.startPage = lastPage - 4;
		
		if(maxPage>lastPage) {
			next = true;
		}else if(maxPage < lastPage) {
			this.lastPage = this.maxPage;
		}
		
		if(startPage>1) {
			prev = true;
		}else if(startPage<1) {
			this.startPage = 1;
		}

		
	}
	
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getLastPage() {
		return lastPage;
	}
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	
	
	
	
}
