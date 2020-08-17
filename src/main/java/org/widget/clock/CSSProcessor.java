package org.widget.clock;

import com.steadystate.css.parser.CSSOMParser;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardOpenOption.*;

public class CSSProcessor {
    private final String fileName;

    private CSSStyleSheet stylesheet;
    private final Map<String, String> propertiesToUpdate = new HashMap<>();

    public CSSProcessor(String fileName) {
        this.fileName = fileName;
        try {
            String s = File.separator;
            String stringPath = System.getProperty("user.home") + s + ".Clock" + s + "CSS" + s + fileName;
            InputStream stream = new FileInputStream(stringPath);
            InputSource source = new InputSource(new InputStreamReader(stream));
            CSSOMParser parser = new CSSOMParser();
            this.stylesheet = parser.parseStyleSheet(source, null, null);
        } catch (FileNotFoundException e) {
            Logger.getGlobal().log(Level.SEVERE, "CSS file not found: \"" + fileName + "\"", e);
        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE, "Failed to open CSS file: \"" + fileName + "\"", e);
        }
    }

    public CSSProcessor(String fileName, CSSProcessor cssProcessor) {
        this.fileName = fileName;
        this.stylesheet = cssProcessor.getStylesheet();
    }

    public void updateStylesheet() {
        CSSRuleList ruleList = stylesheet.getCssRules();
        CSSStyleRule styleRule = (CSSStyleRule) ruleList.item(0);
        CSSStyleDeclaration styleDeclaration = styleRule.getStyle();
        for (int i = 0; i < styleDeclaration.getLength(); i++) {
            String property = styleDeclaration.item(i);
            if (propertiesToUpdate.containsKey(property)) {
                styleDeclaration.getPropertyCSSValue(property).setCssText(propertiesToUpdate.get(property));
            }
        }
        clearPropertiesToUpdate();
        writeCSSToDisc();
    }

    public void addPropertyToUpdate(String property, String value) {
        propertiesToUpdate.put(property, value);
    }

    public void clearPropertiesToUpdate() {
        propertiesToUpdate.clear();
    }

    private void writeCSSToDisc() {
        String s = File.separator;
        String stringPath = System.getProperty("user.home") + s + ".Clock" + s + "CSS" + s + fileName;
        Path path = Paths.get(stringPath);
        try {
            Files.writeString(path, generateCSSString(), CREATE, TRUNCATE_EXISTING, WRITE);
        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE, "Failed to write CSS file: \"" + fileName + "\"", e);
        }
    }

    private String generateCSSString() {
        CSSRuleList ruleList = stylesheet.getCssRules();
        CSSStyleRule styleRule = (CSSStyleRule) ruleList.item(0);
        CSSStyleDeclaration styleDeclaration = styleRule.getStyle();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(".label {\n");
        for (int i = 0; i < styleDeclaration.getLength(); i++) {
            String property = styleDeclaration.item(i);
            stringBuilder.append("    ")
                    .append(property)
                    .append(": ")
                    .append(styleDeclaration.getPropertyCSSValue(property).getCssText())
                    .append(";\n");
        }
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }

    public CSSStyleSheet getStylesheet() {
        return stylesheet;
    }
}
