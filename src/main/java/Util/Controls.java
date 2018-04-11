package Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controls {
	public String validateName(String x){
        String a="";
        Pattern pattern=Pattern.compile("[a-zA-Z]+");
        Matcher matcher=pattern.matcher(x);
        if (x.length()==0){
            a="(*)Required Field!";
            
        }
        else if (matcher.find() && matcher.group().equals(x)){
            
        }else {
            a="(*)This field accept only letters";
           
        }
        return a;
    }
}
