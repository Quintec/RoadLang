import java.io.*;
import java.util.*;

public class Interpreter
{
	private static Scanner in = new Scanner(System.in);

	public static void main(String[] args)
	{
		Parser p = new Parser();
		p.addPrefix("ayy_bossman", 3); //condition true block false block
		p.addDelimiters("init_bruv", "yeah");
		p.addInfix("is", 2);
		p.addInfix("be", 2);
		p.addInfix("n", 2);
		p.addInfix("o", 2);
		p.addInfix("x", 2);
		p.addInfix("ova", 2);
		p.addInfix("leftova", 2);
		p.addInfix("witout", 2);
		p.addInfix("lickler_den", 2);
		p.addInfix("bigger_den", 2);
		p.addPrefix("man_say", 1);
		p.addPrefix("not", 1);
		p.addPrefix("rip_dat_bong_till", 2); //condition block
		p.addDelimiters("n_dat", "mmm");
		p.addDelimiters("pass_me", "from_asda_lad"); //input
		p.addPrefix("3up_my_man", 1);
		p.addDelimiters("wagwan_my_slime", "chat_wit_u_later_fam");
		p.addDelimiters("\"", "\"");
		p.addDelimiters("(", ")");

		State s = new State();
		String prog;
		if (args.length > 0)
			prog = load(args[0]);
		else
			prog = in.nextLine();
		if (!prog.startsWith("wagwan my slime")) {
			System.err.println("Aight the lad's lost the plot");
			System.exit(1);
		}
		if (!prog.strip().endsWith("chat wit u later fam")) {
			System.err.println("Aight the lad's lost the plot");
			System.exit(1);
		}
		
		prog = prog.replace("rip dat bong till", "rip_dat_bong_till");
		prog = prog.replace("pass me", "pass_me");
		prog = prog.replace("from asda lad", "from_asda_lad");
		prog = prog.replace("n dat", "n_dat");
		prog = prog.replace("init bruv", "init_bruv");
		prog = prog.replace("3up my guy", "3up_my_guy");
		prog = prog.replace("ayy bossman", "ayy_bossman");
		prog = prog.replace("lickler den", "lickler_den");
		prog = prog.replace("bigger den", "bigger_den");
		prog = prog.replace("beehive", "5");
		prog = prog.replace("bice", "2");
		prog = prog.replace("bar", "1");
		prog = prog.replace("bender", "6");
		prog = prog.replace("nuttin", "0");
		prog = prog.replace("wagwan my slime", "wagwan_my_slime");
		prog = prog.replace("chat wit u later fam", "chat_wit_u_later_fam");
		prog = prog.replace("man say", "man_say");
		prog = prog.trim();
		
		Object program = p.parse(prog);
		//System.out.println(program);
		eval(program, s);
	}

	public static Object eval(Object exp, State s)
	{
		if (exp instanceof Integer || exp instanceof Double)
		{
			//the value of an integer is itself
			return exp;
		}
		else if (exp instanceof String)
		{
			String varName = (String)exp;
			if (varName.equals("peng"))
				return true;
			if (varName.equals("bawbag"))
				return false;
			
			return s.getVariableValue(varName);
		}
		else
		{
			//must be a List
			List list = (List)exp;

			if (list.get(0).equals("man_say"))  // print EXP
			{
				Object argument = list.get(1);
				System.out.println(eval(argument, s));
				return "OK";
			}
			
			if (list.get(0).equals("3up_my_man")) {
				String varName = (String)list.get(1);
				s.setVariableValue(varName, null);
				return "OK";
			}

			if (list.get(0).equals("("))  // EXP + EXP
			{
				return eval(list.get(1), s);
			}
			
			if (list.get(0).equals("\"")) {
				String val = "";
				for (int i = 1; i < list.size() - 1; i++) {
					val += list.get(i);
					val += " ";
				}
				return val.substring(0, val.length() - 1);
			}

			if (list.get(0).equals("init_bruv") || list.get(0).equals("n_dat") || list.get(0).equals("wagwan_my_slime"))  // EXP + EXP
			{
				for (int i = 1; i < list.size() - 1; i++) {
					eval(list.get(i), s);
				}
				return "OK";
			}
			
			if (list.get(0).equals("pass_me")) {
				String inp = in.nextLine();
				Object val = null;
				if (inp.matches("-?\\d+"))
					val = Integer.parseInt(inp);
				else if (inp.matches("-?\\d+\\.\\d+"))
					val = Double.parseDouble(inp);
				else
					val = inp;
				s.setVariableValue((String)list.get(1), val);
				return "OK";
			}
			
			if (list.get(0).equals("ayy_bossman")) {
				boolean cond = (Boolean)eval(list.get(1), s);
				if (cond)
					eval(list.get(2), s);
				else
					eval(list.get(3), s);
				return "OK";
			}
			
			if (list.get(0).equals("rip_dat_bong_till")) {
				boolean cond = (Boolean)eval(list.get(1), s);
				while (!cond) {
					eval(list.get(2), s);
					cond = (Boolean)eval(list.get(1), s);
				}
				return "OK";
			}

			if (list.get(1).equals("is")) 
			{
				String argument1 = (String)list.get(0);
				Object argument2 = list.get(2);
				s.setVariableValue(argument1, eval(argument2, s));
				return "OK";
			}
			
			if (list.get(1).equals("be")) {
				Object argument1 = eval(list.get(0), s);
				Object argument2 = eval(list.get(2), s);
				if (argument1 instanceof Number && argument2 instanceof Number)
					return Double.parseDouble(argument1.toString()) == Double.parseDouble(argument2.toString());
				return argument1.equals(argument2);
			}
			
			if (list.get(0).equals("not")) {
				Object argument1 = eval(list.get(0), s);
				return !(Boolean)argument1;
			}

			if (list.get(1).equals("n")) 
			{
				Object argument1 = eval(list.get(0), s);
				Object argument2 = eval(list.get(2), s);
				if (argument1 instanceof Integer && argument2 instanceof Integer)
					return ((Integer)argument1 + (Integer)argument2);
				else if (argument1 instanceof Double && argument2 instanceof Number || 
						argument2 instanceof Double && argument1 instanceof Number)
					return (Double.parseDouble(argument1.toString()) + Double.parseDouble(argument2.toString()));
				else if (argument1 instanceof Boolean && argument2 instanceof Boolean)
					return ((Boolean) argument1 && (Boolean) argument2);
				else {
					return argument1.toString() + argument2.toString();
				}
			}
			
			if (list.get(1).equals("o")) {
				Object argument1 = eval(list.get(0), s);
				Object argument2 = eval(list.get(2), s);
				return (Boolean) argument1 || (Boolean) argument2;
			}
			
			if (list.get(1).equals("bigger_den")) {
				Object argument1 = eval(list.get(0), s);
				Object argument2 = eval(list.get(2), s);
				return Double.parseDouble(argument1.toString()) > Double.parseDouble(argument2.toString());
			}
			
			if (list.get(1).equals("lickler_den")) {
				Object argument1 = eval(list.get(0), s);
				Object argument2 = eval(list.get(2), s);
				return Double.parseDouble(argument1.toString()) < Double.parseDouble(argument2.toString());
			}

			if (list.get(1).equals("ova")) 
			{
				Object argument1 = eval(list.get(0), s);
				Object argument2 = eval(list.get(2), s);
				Double res = (Double.parseDouble(argument1.toString()) * 1.0 / Double.parseDouble(argument2.toString()));
				if (res % 1 == 0)
					return res.intValue();
				return res;
			}
			
			if (list.get(1).equals("x")) {
				Object argument1 = eval(list.get(0), s);
				Object argument2 = eval(list.get(2), s);
				if ((argument1 instanceof Integer) && (argument2 instanceof Integer))
					return (Integer)(argument1) * (Integer)(argument2);
				return (Double)(argument1) * (Double)(argument2);
			}
			
			if (list.get(1).equals("leftova")) {
				Object argument1 = eval(list.get(0), s);
				Object argument2 = eval(list.get(2), s);
				if ((argument1 instanceof Integer) && (argument2 instanceof Integer))
					return (Integer)(argument1) % (Integer)(argument2);
				else if (argument2 instanceof Integer)
					return (Double)(argument1) % (Integer)(argument2);
				return (Double)(argument1) % (Double)(argument2);
			}
			
			if (list.get(1).equals("witout")) 
			{
				Object argument1 = eval(list.get(0), s);
				Object argument2 = eval(list.get(2), s);
				if (argument1 instanceof Integer && argument2 instanceof Integer)
					return ((Integer)argument1 - (Integer)argument2);
				return (Double.parseDouble(argument1.toString()) - Double.parseDouble(argument2.toString()));
			}

			System.err.println("Aight the lad's lost the plot");
			System.exit(1);
			return null;
		}
	}

	public static String input()
	{
		return in.nextLine();
	}

	public static String load(String fileName)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null)
			{
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			br.close();
			return sb.toString();
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}