package Util;

import tn.esprit.infinity_server.persistence.User;

public class Session {

	private static User user;

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		Session.user = user;
	}

}
