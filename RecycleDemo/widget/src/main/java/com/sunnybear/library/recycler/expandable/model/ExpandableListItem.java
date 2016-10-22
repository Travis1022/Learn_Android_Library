package com.sunnybear.library.recycler.expandable.model;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for implementing required methods in a parent list item.
 */
public abstract class ExpandableListItem implements Serializable {
    public boolean mExpanded = false;//是否可以展开

    /**
     * Getter for the list of this parent list item's child list items.
     *
     * @return A {@link List} of the children of this {@link ExpandableListItem}
     */
    public abstract List<?> getChildItemList();

    /**
     * @return true if expanded, false if not
     */
    public boolean isExpanded() {
        return mExpanded;
    }

    /**
     * set expand state
     *
     * @param isExpanded
     */
    public void setExpanded(boolean isExpanded) {
        this.mExpanded = isExpanded;
    }
}