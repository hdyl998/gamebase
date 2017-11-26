package com.hdyl;

import java.util.List;

/**
 * Created by Administrator on 2017/11/26.
 */

public class  ExpandableItem<ChildItem> {

        public String strTitle;
        public List<ChildItem> childLists;


        public void setStrTitle(String strTitle) {
            this.strTitle = strTitle;
        }

        public String getStrTitle() {
            return strTitle;
        }

        public void setChildLists(List<ChildItem> childLists) {
            this.childLists = childLists;
        }

        public List<ChildItem> getChildLists() {
            return childLists;
        }
}
