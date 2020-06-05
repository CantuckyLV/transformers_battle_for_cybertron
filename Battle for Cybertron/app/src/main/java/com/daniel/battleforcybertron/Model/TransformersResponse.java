package com.daniel.battleforcybertron.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class TransformersResponse implements Serializable {
    @SerializedName("transformers")
    private ArrayList<Transformer> transformers;
}
