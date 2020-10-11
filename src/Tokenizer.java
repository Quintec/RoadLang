public class Tokenizer
{
    private String text;
    private int index;  //index of char to process next

    public Tokenizer(String text)
    {
        this.text = text;
        index = 0;
    }

    public String next()
    {
        skipWhitespace();

        if (index == text.length())
            return "";  //indicates end of program

        String token = "";

        if (isAlphaNum(text.charAt(index)))
        {
            //consists of a-z, A-Z, 0 - 9, or _.
            //this code assumes any string of such characters is a single token.
            while (index < text.length() && isAlphaNum(text.charAt(index)))
            {
                token += text.charAt(index);
                index++;
            }
        }
        else
        {
            //not alphanumeric.  therefore, this symbol is assumed to be a one character token.
            token += text.charAt(index);
            index++;
        }

        skipWhitespace();
        return token;
    }

    private void skipWhitespace()
    {
        while (index < text.length() && isWhitespace(text.charAt(index)))
            index++;
    }

    private boolean isWhitespace(char ch)
    {
        return ch == ' ' || ch == '\n' || ch == '\t' || ch == '\r';
    }

    //true if 0-9, a-z, A-Z, or _
    private boolean isAlphaNum(char ch)
    {
        return '0' <= ch && ch <= '9'
        || 'a' <= ch && ch <= 'z'
        || 'A' <= ch && ch <= 'Z'
        || ch == '_';
    }
}
