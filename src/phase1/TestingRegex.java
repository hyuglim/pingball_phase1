package phase1;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TestingRegex {
	
	public static void main(String[] args) {
		String test1="       board name = sampleBoard1 gravity=20.0 friction1=0.020 friction2=0.020";
		/*String test2="board name=ExampleB gravity = 10.0";
		String test3="squareBumper name=Square x=0 y=10";
		String test4="rightFlipper name=FlipR x=12 y=7 orientation=0";
		String test5="fire trigger=Square action=FlipL";
		String test6="circleBumper name=Circle12 x=12 y=6";
		
		int counter=0;
		String word= "\\s*([a-zA-Z0-9]+\\s*=\\s*[a-zA-Z0-9\\.]+)\\s*";
		Pattern wordpat=Pattern.compile(word);
		Matcher matcher = wordpat.matcher(test1);
		while(matcher.find()){
		    int count = matcher.groupCount();
		    counter+=count;
		    System.out.println("group count is "+count);
		    for(int i=0;i<count;i++){
		    	String s=matcher.group(i).replaceAll("\\s+", "").split("=")[1];
		        System.out.println(s);
		    }
		}
		System.out.println(counter);
		String id="";
		String firstWord= "\\s*([a-zA-Z0-9]+)\\s+";
		Pattern firstWordpat=Pattern.compile(firstWord);
		Matcher firstMatcher = firstWordpat.matcher(test1);
		if(firstMatcher.find()){
			id=firstMatcher.group(1);
		}
		System.out.println(id);*/
	}
	

}
