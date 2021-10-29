package com.douzone.mysite.vo;

public class AdminVo {
	private Long no;
	private String title;
	private String welome;
	private String profile;
	private String description;
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWelome() {
		return welome;
	}
	public void setWelome(String welome) {
		this.welome = welome;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "AdminVo [no=" + no + ", title=" + title + ", welome=" + welome + ", profile=" + profile
				+ ", description=" + description + "]";
	}
	
	
}
