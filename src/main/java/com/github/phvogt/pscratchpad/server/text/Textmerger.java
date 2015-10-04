// (c) 2015 by Philipp Vogt
package com.github.phvogt.pscratchpad.server.text;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import difflib.Delta;
import difflib.Delta.TYPE;
import difflib.DiffUtils;
import difflib.Patch;

/**
 * Merge text.
 */
public class Textmerger {

    /** logger. */
    private final Logger logger = Logger.getLogger(Textmerger.class.getName());

    /**
     * Merges the revised text to original and marks differences.
     * @param original original
     * @param revised revised
     * @return merged text
     */
    public String mergeText(final String original, final String revised) {
        if ((original == null) || (revised == null)) {
            return "";
        }
        final List<String> splittedOrig = Arrays.asList(StringUtils.split(original, "\n"));
        final List<String> splittedRev = Arrays.asList(StringUtils.split(revised, "\n"));
        final String result = mergeText(splittedOrig, splittedRev);
        return result;
    }

    /**
     * Merges the revised text to original and marks differences.
     * @param original original
     * @param revised revised
     * @return merged text
     */
    String mergeText(final List<String> original, final List<String> revised) {

        final String methodname = "mergeText(): ";

        final Patch<String> patch = DiffUtils.diff(original, revised);
        logger.info(methodname + "patch = " + patch);

        final List<Delta<String>> deltas = patch.getDeltas();

        final StringBuilder merged = new StringBuilder();

        int last = 0;
        for (final Delta<String> delta : deltas) {
            if (last + 1 < delta.getOriginal().getPosition()) {
                for (int i = last + 1; i < delta.getOriginal().getPosition(); i++) {
                    merged.append(original.get(i) + "\n");
                }
            }
            if (delta.getType() == TYPE.INSERT) {
                final List<String> re = delta.getRevised().getLines();
                merged.append(l2b("+", re) + "\n");
            }
            if (delta.getType() == TYPE.CHANGE) {
                final List<String> or = delta.getOriginal().getLines();
                merged.append(l2b("-", or) + "\n");
                final List<String> re = delta.getRevised().getLines();
                merged.append(l2b("+", re) + "\n");
            }
            if (delta.getType() == TYPE.DELETE) {
                final List<String> or = delta.getOriginal().getLines();
                merged.append(l2b("-", or) + "\n");
            }
            last = delta.getOriginal().last();
        }
        if (last + 1 < original.size()) {
            for (int i = last + 1; i < original.size(); i++) {
                merged.append(original.get(i) + "\n");
            }
        }
        final String result = merged.toString();

        return result;
    }

    StringBuilder l2b(final String prefix, final List<?> l) {
        final StringBuilder sb = new StringBuilder();
        for (final Object object : l) {
            sb.append(prefix + object + "\n");
        }
        if (sb.length() >= 1)
            sb.deleteCharAt(sb.length() - 1); // last "\n"
        return sb;
    }

}
