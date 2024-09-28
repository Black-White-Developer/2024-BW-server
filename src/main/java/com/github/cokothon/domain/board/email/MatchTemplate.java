package com.github.cokothon.domain.board.email;

import com.github.cokothon.common.email.EmailTemplate;
import com.github.cokothon.domain.board.schema.Board;
import com.github.cokothon.domain.user.schema.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class MatchTemplate implements EmailTemplate {

	private final Board board;
	private final User user;

	@Override
	public String getTitle() {
		return "[IB] 매치 알림";
	}

	@Override
	public String getHtml() {
		return """
			<div style="max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; font-family: Arial, sans-serif;">
			    <div style="text-align: center; padding-bottom: 20px; border-bottom: 1px solid #dddddd;">
			        <img src="%s" alt="Logo" style="max-width: 150px;">
			    </div>
			    <div style="padding: 20px; text-align: center;">
			        <h1 style="color: #333333;">%s</h1>
			        <p style="font-size: 16px; color: #666666; line-height: 1.5;">%s(%s)님이 매칭을 요청하셨습니다</p>
			    </div>
			</div>""".formatted(user.getAvatar() == null ? "" : user.getAvatar(), board.getTitle(), user.getNickname(), user.getEmail());
	}
}
