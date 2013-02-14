package home.maximv.utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.language.Soundex;

/**
* encode strings using soundex
* @author Greg Milette &#60;<a href="mailto:gregorym@gmail.com">gregorym@gmail.com</a>&#62;
*/
public class SoundsLikeWordMatcher extends WordMatcher
{
    protected static Soundex soundex;
    
    static
    {
        soundex = new Soundex();
    }
    
    public SoundsLikeWordMatcher(String... wordsIn)
    {
        this(Arrays.asList(wordsIn));
    }

    public SoundsLikeWordMatcher(List<String> wordsIn)
    {
        super(encode(wordsIn));
    }
    
    @Override
    public boolean isIn(String word)
    {
        return super.isIn(encode(word));
    }
    
    protected static List<String> encode(List<String> input)
    {
        List<String> encoded = new ArrayList<String>();
        for (String in : input)
        {
            encoded.add(encode(in));
        }
        return encoded;
    }

    private static String encode(String in)
    {
        String encoded = in;
        try
        {
            encoded = soundex.encode(in);
        }
        catch (IllegalArgumentException e)
        {
            //for weird characters that soundex doesn't understand
            
        }
        return encoded;
    }
}

