package cz.marianjanik.ekurz;

import java.util.Comparator;

public class StandardRateComparator implements Comparator<VatState> {
    @Override
    public int compare(VatState first, VatState second) {
        return first.getStandardRate()- second.getStandardRate();
    }
}
