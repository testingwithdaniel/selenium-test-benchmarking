package utils.common;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebpageElements {

    private String elementName;
    private String elementIdentifier;

    public WebpageElements(String elementName, String elementIdentifier) {
        this.setElementName(elementName);
        this.setElementIdentifier(elementIdentifier);
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getElementIdentifier() {
        return elementIdentifier;
    }

    public void setElementIdentifier(String elementIdentifier) {
        this.elementIdentifier = elementIdentifier;
    }

    public WebpageElements format(Object... substitutions){
        String elementValue = this.elementIdentifier;
        Pattern typePattern = Pattern.compile("[=,]''[^\\]]*''");
        Matcher typeMatcher = typePattern.matcher(elementValue);
        if (typeMatcher.find()){
            elementValue = MessageFormat.format(elementValue, substitutions);
        }else{
            Pattern pattern = Pattern.compile("([{][0-9]+[}])");
            Matcher matcher = pattern.matcher(elementValue);
            int count = 0;
            while (matcher.find()) {
                count++;
            }
            for (int i = 0; i < count; i++){
                pattern = Pattern.compile("([{]" + i + "[}])");
                matcher = pattern.matcher(elementValue);
                elementValue = matcher.replaceAll(substitutions[i].toString());
            }
        }
        WebpageElements formattedLocator = fixXpath(elementValue);
        return formattedLocator;
    }

    private WebpageElements fixXpath(String elementValue){
        Pattern replacePattern = Pattern.compile("[=,]'[^']*(['][\\w\\s!@#$%^&*-;:.™/]*)+'");
        Matcher replaceMatcher = replacePattern.matcher(elementValue);
        while (replaceMatcher.find()){
            String matchValue = replaceMatcher.group();
            matchValue = matchValue.replace("='", "=\"");
            matchValue = matchValue.replace(",'", ",\"");
            matchValue = matchValue.substring(0, matchValue.length() - 1) + "\"";
            elementValue = elementValue.replace(replaceMatcher.group(), matchValue);
        }
        WebpageElements element = new WebpageElements(this.elementName, elementValue);
        return element;
    }
}
