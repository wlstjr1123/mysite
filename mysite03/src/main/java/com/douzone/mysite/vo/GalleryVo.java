package com.douzone.mysite.vo;

public class GalleryVo {
	private long no;
	private String url;
	private String comments;
	
	
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	@Override
	public String toString() {
		return "GalleryVo [no=" + no + ", url=" + url + ", comments=" + comments + "]";
	}
	
	
}
