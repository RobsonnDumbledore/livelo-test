package br.com.codart.domain.utils;

import com.ibm.icu.text.RuleBasedNumberFormat;

import java.util.Locale;

public class NumberToWordsConverter {

    private static final RuleBasedNumberFormat numberFormat;
    private static final RuleBasedNumberFormat currencyFormat;

    static {
        Locale brazil = new Locale("pt", "BR");
        numberFormat = new RuleBasedNumberFormat(brazil, RuleBasedNumberFormat.SPELLOUT);
        currencyFormat = new RuleBasedNumberFormat(brazil, RuleBasedNumberFormat.SPELLOUT);
    }

    public static String convertNumberToWords(long number) {
        return numberFormat.format(number);
    }

    public static String convertCurrencyToWords(double amount) {
        long reais = (long) amount;
        int centavos = (int) Math.round((amount - reais) * 100);

        String reaisInWords = numberFormat.format(reais) + (reais == 1 ? " real" : " reais");
        String centavosInWords = centavos > 0 ? " e " + numberFormat.format(centavos) + (centavos == 1 ? " centavo" : " centavos") : "";

        return reaisInWords + centavosInWords;
    }

}
