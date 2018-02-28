package esprit.tn.Infinity_client;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import tn.esprit.infinity_server.interfaces.AddressRemote;
import tn.esprit.infinity_server.interfaces.NewsArticleRemote;
import tn.esprit.infinity_server.interfaces.NewsSourceRemote;
import tn.esprit.infinity_server.persistence.*;

public class Main {
	/**
	 * @param args
	 * @throws NamingException
	 */
	public static void main(String[] args) throws NamingException {
		Properties jndiProps = new Properties(); //l
		jndiProps.put(Context.PROVIDER_URL,"http-remoting://localhost:8080");
		String jndiName="infinity_server-ear/infinity_server-ejb/ServiceNewsSource!tn.esprit.infinity_server.interfaces.NewsSourceRemote";
		Context context=new InitialContext(jndiProps);
		NewsSourceRemote proxy=(NewsSourceRemote)context.lookup(jndiName);
		NewsSource ns = new NewsSource();
		ns.setId(1);
		ns.setDescription("aze");
		ns.setUrl("aze");
		ns.setImage("aze");
		proxy.addNewsSource(ns);
		List<NewsSource> s = proxy.getAllNewsSource();
		System.out.println("result: ");
		System.out.println(s);
	}

}
