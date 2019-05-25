package com.mycompany.centrumkszalcenia.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailValidator {

    private Pattern regexPattern;
    private Matcher regMatcher;

    public boolean validateEmailAddress(String emailAddress) {

        regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
        regMatcher = regexPattern.matcher(emailAddress);
        if (regMatcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
