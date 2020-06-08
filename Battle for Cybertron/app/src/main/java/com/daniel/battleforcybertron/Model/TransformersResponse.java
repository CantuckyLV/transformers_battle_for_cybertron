package com.daniel.battleforcybertron.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Pojo that represents a Response body for the API calls
 */
public class TransformersResponse implements Serializable {
    @SerializedName("transformers")
    private ArrayList<Transformer> transformers;

    public ArrayList<Transformer> getTransformers() {
        return transformers;
    }

    public void setTransformers(ArrayList<Transformer> transformers) {
        this.transformers = transformers;
    }

    @Override
    public String toString() {
        return "TransformersResponse{" +
                "transformers=" + transformers +
                '}';
    }
}
