package com.example.test.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FSAEstablishmentList {

    @JsonProperty("establishments")
    private List<FSAEstablishment> fsaEstablishmentList;

    public FSAEstablishmentList() {
        //blank required for jackson
    }

    public FSAEstablishmentList(List<FSAEstablishment> fsaEstablishmentList) {
        this.fsaEstablishmentList = fsaEstablishmentList;
    }

    public List<FSAEstablishment> getFSAEstablishmentList() {
        return fsaEstablishmentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FSAEstablishmentList that = (FSAEstablishmentList) o;

        return fsaEstablishmentList != null ? fsaEstablishmentList.equals(that.fsaEstablishmentList) : that.fsaEstablishmentList == null;
    }

    @Override
    public int hashCode() {
        return fsaEstablishmentList != null ? fsaEstablishmentList.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "FSAEstablishmentList{" +
                "FSAEstablishmentList=" + fsaEstablishmentList +
                '}';
    }
}
