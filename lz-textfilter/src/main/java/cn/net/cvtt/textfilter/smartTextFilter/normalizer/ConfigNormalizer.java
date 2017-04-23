package cn.net.cvtt.textfilter.smartTextFilter.normalizer;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ConfigNormalizer {
	
	//private static final Logger logger = LoggerFactory.getLogger(ConfigNormalizer.class);

    private Reader reader;
    
    //private String content;

    public ConfigNormalizer(String content)
    {
        reader = new StringReader(content);
    	//this.content = content;
    }

    public List<List<Character>> read() throws IOException
    {
        List<List<Character>> retr = new ArrayList<List<Character>>();

        int flag = 0;
        char[] charr = ContentNormalizer.prepareCharForNormalize();

			for (int c = reader.read(); c != -1; c = reader.read())
			{
			    if (c == '[' && flag++ == 0)//错
			        continue;

			    if (c == ']' && --flag == 0)
			        return retr;

			    int count = ContentNormalizer.normalize(c, charr);
			    if (count > 0)
			    {
			        List<Character> chain = new ArrayList<Character>(count);
			        for (int i = 0; i < count; ++i)
			            chain.add(charr[i]);
			        retr.add(chain);
			    }

			    if (flag == 0)
			        break;
			}
        return retr.size() > 0 ? retr : null;
    }
}
