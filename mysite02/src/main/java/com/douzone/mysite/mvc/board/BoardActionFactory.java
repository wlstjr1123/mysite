package com.douzone.mysite.mvc.board;

import com.douzone.mysite.mvc.main.MainAction;
import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if ("list".equals(actionName)) {
			action = new BoardListAction();
		} else if ("write".equals(actionName)) {
			action = new BoardWriteAction();
		} else if ("register".equals(actionName)) {
			action = new BoardRegisterAction();
		} else if ("view".equals(actionName)) {
			action = new BoardViewAction();
		} else if ("answer".equals(actionName)) {
			action = new BoardAnswerAction();
		} else if ("delete".equals(actionName)) {
			action = new BoardDeleteAction();
		} else if ("modifyReg".equals(actionName)) {
			action = new BoardModifyRegAction();
		} else if ("modify".equals(actionName)) {
			action = new BoardModifyAction();
		} 
		else {
			action = new MainAction();
		}
		
		return action;
	}

}
