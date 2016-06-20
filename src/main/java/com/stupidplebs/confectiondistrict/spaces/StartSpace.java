package com.stupidplebs.confectiondistrict.spaces;

import java.util.Objects;

public class StartSpace implements Space {
    public StartSpace() {
    }

    @Override
    public boolean equals(final Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        return true;

    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }

    @Override
    public String toString() {
        return "StartSpace{}";
    }

}
