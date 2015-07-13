package org.ram.rhino.examples;
import static org.junit.Assert.assertEquals;
import org.junit.*;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import java.nio.file.*;
import java.nio.charset.*;
import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.*;

public class TestSimpleExample {
	private Scriptable scope ;
	private Context context;
	@Test
	public void addStringToList()
	{
		List list= new ArrayList();
		Object[] args = new Object[1];
		args[0] = list;
		Function func = (Function)this.scope.get("addToList", this.scope);
		func.call(this.context, this.scope, this.scope, args);
		assertEquals("List size should be 1 " , 1, list.size());
		assertEquals("Element must be James ", "James", list.get(0));
	}
	@Before
	public void RhinoInitialization  () throws IOException
	{
		String path = "example.js";
		URL url = this.getClass().getClassLoader().getResource(path);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		StringBuffer sb = new StringBuffer();
		String inputLine;
		while ((inputLine = in.readLine()) != null)
		{
		            sb.append(inputLine);
		            sb.append(System.getProperty("line.separator"));
		}
        in.close();
        String sourceCode = sb.toString();
        this.context = Context.enter();
        this.scope = this.context.initStandardObjects(null);
        try {
				this.context.evaluateString(this.scope, sourceCode, "<cmd",1, null);
			} catch (JavaScriptException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
		}
	}
}