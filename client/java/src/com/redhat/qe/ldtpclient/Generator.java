package com.redhat.qe.ldtpclient;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * @author jweiss
 *
 *	Generates an xmlrpc client for python LDTP xmlrpc server.  This allows you
 *	to remotely control GTK (and java and other UIs) apps.  If the LDTP server
 *  API changes, you can make changes to the the API list here and regenerate
 *  the client.
 *  
 *  9/27/10 - currently not using this class, decided to just write the java by
 *  hand instead of generating it, at this point it doesn't seem like generating 
 *  it will save enough effort to be worth it.  But i'll keep what i've done so far 
 *  just in case. - jweiss
 */
public class Generator {

	public class APIMethod {
		public String name;
		public Class<?> returnType;
		public APIParameter[] parameters;
		public APIMethod(String name, Class<?> returnType) {
			super();
			this.name = name;
			this.returnType = returnType;
		}
		public APIMethod(String name, Class<?> returnType, String[] paramNames, Class<?>... paramTypes){
			this(name, returnType);
			this.parameters = new APIParameter[paramNames.length];
			for (int i=0; i<paramNames.length; i++){
				this.parameters[i] = new APIParameter(paramNames[i], paramTypes[i]);
			}
		}

		public APIMethod(String name, Class<?> returnType, String param, Class<?> paramType){
			this(name, returnType);
			this.parameters = new APIParameter[1];
			parameters[0] = new APIParameter(param, paramType);
		}

		public APIMethod(String name, Class<?> returnType, String param, Class<?> paramType, String param2, Class<?> param2Type){
			this(name, returnType);
			this.parameters = new APIParameter[2];
			parameters[0] = new APIParameter(param, paramType);
			parameters[1] = new APIParameter(param2, param2Type);
		}
		public APIMethod(String name, Class<?> returnType, String param, Class<?> paramType, String param2, Class<?> param2Type, String param3, Class<?> param3Type){
			this(name, returnType);
			this.parameters = new APIParameter[3];
			parameters[0] = new APIParameter(param, paramType);
			parameters[1] = new APIParameter(param2, param2Type);
			parameters[1] = new APIParameter(param3, param3Type);
		}

	}
	
	public class APIParameter {
		public String name;
		public Class<?> type;
		public APIParameter(String name, Class<?> type) {
			super();
			this.name = name;
			this.type = type;
		}
	}
	public static void main(String... args) throws Exception{
		Generator g = new Generator();
		Set<Generator.APIMethod> api = new HashSet<Generator.APIMethod>();
		api.add(g.new APIMethod("launchapp", Integer.class, "args", String[].class));
		api.add(g.new APIMethod("click", Integer.class, "windowName", String.class, "componentName", String.class));
		
		Velocity.init();
		VelocityContext context = new VelocityContext();
	
		context.put( "name", new String("Velocity") );
	
		Template template = null;
	
		try
		{
		   template = Velocity.getTemplate("mytemplate.vm");
		}
		catch( ResourceNotFoundException rnfe )
		{
		   // couldn't find the template
		}
		catch( ParseErrorException pee )
		{
		  // syntax error: problem parsing the template
		}
		catch( MethodInvocationException mie )
		{
		  // something invoked in the template
		  // threw an exception
		}
		catch( Exception e )
		{}
	
		StringWriter sw = new StringWriter();
	
		template.merge( context, sw );
	}
}
