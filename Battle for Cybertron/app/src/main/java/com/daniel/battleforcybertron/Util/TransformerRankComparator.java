package com.daniel.battleforcybertron.Util;

import com.daniel.battleforcybertron.Model.Transformer;

import java.util.Comparator;

public class TransformerRankComparator implements Comparator<Transformer> {
    @Override
    public int compare(Transformer transformer1, Transformer transformer2) {
        return Integer.compare(transformer1.getRank(),transformer2.getRank());
    }

}
