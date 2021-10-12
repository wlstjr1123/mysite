package com.douzone.mysite.mvc.guestbook;

import com.douzone.mysite.mvc.main.MainAction;
import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class GuestBookActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if ("list".equals(actionName)) {
			action = new GuestBookAction();
		} else if ("add".equals(actionName)) {
			action = new GuestBookAddAction();
		} else if ("deleteform".equals(actionName)) {
			action = new GuestBookDeleteFormAction();
		} else if ("delete".equals(actionName)) {
			action = new GuestBookDeleteAction();
		}
		else {
			action = new MainAction();
		}
		
		return action;
	}

}
