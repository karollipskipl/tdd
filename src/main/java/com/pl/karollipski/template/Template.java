package com.pl.karollipski.template;

import com.pl.karollipski.template.exception.MissingValueException;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Template {

    private Map<String, String> variables;
    private String templateText;

    public Template(String templateText) {
        this.variables = new HashMap<>();
        this.templateText = templateText;
    }

    public void set(String variable, String value) {
        this.variables.put(variable, value);
    }

    public String evaluate() {
        String result = replaceVariables();
        checkForMissingValue(result);
        return result;
    }

    private String replaceVariables() {
        String result = templateText;
        for(Entry<String, String> entry : variables.entrySet()) {
            String regex = "\\$\\{" + entry.getKey() + "\\}";
            result = result.replaceAll(regex, entry.getValue());
        }
        return result;
    }

    private void checkForMissingValue(String result) {
        Matcher matcher = Pattern.compile("\\$\\{.+\\}").matcher(result);
        if(matcher.find()) {
            throw new MissingValueException("No value for " + matcher.group());
        }
    }
}
