import java.util.*;

public class Parser
{
	private Map<String, Integer> prefixMap;
	private Map<String, Integer> infixMap;
	private Map<String, String> delimiterMap;
	private Tokenizer tokenizer;
	private String token;

	public Parser()
	{
		prefixMap = new TreeMap<String, Integer>();
		infixMap = new TreeMap<String, Integer>();
		delimiterMap = new TreeMap<String, String>();
	}

	public void addPrefix(String key, int numArgs)
	{
		prefixMap.put(key, numArgs);
	}

	public void addInfix(String key, int numArgs)
	{
		infixMap.put(key, numArgs);
	}

	public void addDelimiters(String open, String close)
	{
		delimiterMap.put(open, close);
	}

	public Object parse(String text)
	{
		tokenizer = new Tokenizer(text);
		token = tokenizer.next();
		return parse();
	}

	private void eat(String s)
	{
		if (token.equals(s))
			token = tokenizer.next();
		else
			throw new RuntimeException("expected " + s + " but found " + token);
	}

	private Object parse()
	{
		// System.out.println(token);
		Object parsed = atom();
		if (infixMap.containsKey(token))
		{
			int numArgs = infixMap.get(token) - 1;
			ArrayList<Object> list = new ArrayList<Object>();
			list.add(parsed);
			list.add(token);
			eat(token);
			while (numArgs > 0)
			{
				list.add(parse());
				numArgs--;
			}
			parsed = list;
		}
		return parsed;
	}

	private Object atom()
	{
		if (delimiterMap.containsKey(token))
			return parseList(token, delimiterMap.get(token));

		if (prefixMap.containsKey(token))
		{
			int numArgs = prefixMap.get(token);
			ArrayList<Object> list = new ArrayList<Object>();
			list.add(token);
			eat(token);
			while (numArgs > 0)
			{
				list.add(parse());
				numArgs--;
			}
			return list;
		}
		String literal = token;
		eat(literal);
		try
		{
			return Integer.parseInt(literal);
		}
		catch(NumberFormatException e)
		{
			return literal;
		}
	}

	private ArrayList<Object> parseList(String open, String close)
	{
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(open);
		eat(open);
		while (!token.equals(close)) {
			list.add(parse());
		}
		list.add(close);
		eat(close);
		return list;
	}
}