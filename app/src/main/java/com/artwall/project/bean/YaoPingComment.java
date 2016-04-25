package com.artwall.project.bean;

public class YaoPingComment {

	// id 评论/回复编码
	// reMemberId 回复人编码
	// memberName 评论人名称
	// memberId 评论人编码
	// replyContent 评论回复内容
	// questionId 问题编码
	// content 内容
	// createDate 创建时间
	// reMemberName 回复人名称
	// photo 会员头像

	private String id;
	private String rememberId;
	private String memberName;
	private String memberId;
	private String replyContent;
	private String questionId;
	private String content;
	private String createDate;
	private String reMemberName;
	private String photo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRememberId() {
		return rememberId;
	}

	public void setRememberId(String rememberId) {
		this.rememberId = rememberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getReMemberName() {
		return reMemberName;
	}

	public void setReMemberName(String reMemberName) {
		this.reMemberName = reMemberName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
