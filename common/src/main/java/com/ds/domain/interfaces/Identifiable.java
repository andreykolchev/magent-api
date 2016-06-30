package com.ds.domain.interfaces;

import java.io.Serializable;

public interface Identifiable<ID extends Serializable> {

    ID getId();

    void setId(ID id);

}
