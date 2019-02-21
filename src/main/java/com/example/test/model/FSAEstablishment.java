package com.example.test.model;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * A single establishment item.
 */
public class FSAEstablishment {

    @JsonProperty("LocalAuthorityBusinessID")
    private String name;
    @JsonProperty("RatingValue")
    private String value;
    @JsonProperty("SchemeType")
    private String schemetype;


    public FSAEstablishment() {
        //blank required for jackson
    }

    public FSAEstablishment(String name, String value, String schemetype) {
        this.name = name;
        this.value = value;
        this.schemetype = schemetype;
    }

    /**
     * The ID of the establishment
     * @return the ID of the establishment
     */
    public String getName() {
        return name;
    }

    /**
     * The rating of the establishment
     * @return the rating of this establishment
     */
    public String getValue() {
        return value;
    }

    /**
     * The Scheme type
     * @return the scheme type of this establishment
     */
    public String getSchemeType() {
        return schemetype;
    }

    @Override
    public String toString() {
        return "FSAEstablishment{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", schemetype=" + schemetype +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FSAEstablishment that = (FSAEstablishment) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = value != null ? value.hashCode() : 0;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
