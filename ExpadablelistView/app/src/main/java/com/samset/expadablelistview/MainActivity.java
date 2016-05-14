package com.samset.expadablelistview;


import android.os.Bundle;
import android.app.ExpandableListActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.samset.expadablelistview.model.Child;
import com.samset.expadablelistview.model.ParentItem;

import java.util.ArrayList;

public class MainActivity extends ExpandableListActivity {
    private static final String STR_CHECKED = " is Checked!";
    private static final String STR_UNCHECKED = " is unChecked!";
    private int ParentClickStatus = -1;
    private int ChildClickStatus = -1;
    private ArrayList<ParentItem> parentItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        getExpandableListView().setGroupIndicator(null);
        //getExpandableListView().setDivider(devider);
       // getExpandableListView().setChildDivider(devider);
        getExpandableListView().setDividerHeight(1);
        registerForContextMenu(getExpandableListView());

        //Creating static data in arraylist
        final ArrayList<ParentItem> dummyList = getParentsData();

        // Adding ArrayList data to ExpandableListView values
        checkData(dummyList);


        getExpandableListView().setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Toast.makeText(getApplicationContext(), "ChildItem :" + childPosition,
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    private ArrayList<ParentItem> getParentsData() {
        ParentItem p, p1, p2, p3;
        ArrayList<ParentItem> groupNames = new ArrayList();
        p = new ParentItem();
        p.setParenttitle("A");
        p.setChildren(new ArrayList<Child>());

        // Create Child class object
        final Child child = new Child();
        child.setChildTitle("Child 0");

        final Child child1 = new Child();
        child1.setChildTitle("Child 1");

        final Child child2 = new Child();
        child2.setChildTitle("Child 2");

        final Child child3 = new Child();
        child3.setChildTitle("Child 3");

        //Add Child class object to parent class object
        p.getChildren().add(child);
        p.getChildren().add(child1);
        p.getChildren().add(child2);
        p.getChildren().add(child3);

        // B group

        p1 = new ParentItem();
        p1.setParenttitle("B");
        p1.setChildren(new ArrayList<Child>());

        // Create Child class object
        final Child childb1 = new Child();
        childb1.setChildTitle("Child 0");
        //Add Child class object to parent class object
        p1.getChildren().add(childb1);

        // C group
        p2 = new ParentItem();
        p2.setParenttitle("C");
        p2.setChildren(new ArrayList<Child>());

        // Create Child class object
        final Child childc1 = new Child();
        childc1.setChildTitle("Child 0");

        final Child childc2 = new Child();
        childc2.setChildTitle("Child 1");

        final Child childc3 = new Child();
        childc3.setChildTitle("Child 2");

        final Child childc4 = new Child();
        childc4.setChildTitle("Child 3");

        final Child childc5 = new Child();
        childc5.setChildTitle("Child 4");

        p2.getChildren().add(childc1);
        p2.getChildren().add(childc2);
        p2.getChildren().add(childc3);
        p2.getChildren().add(childc4);
        p2.getChildren().add(childc5);

        // D group

        p3 = new ParentItem();
        p3.setParenttitle("D");
        p3.setChildren(new ArrayList<Child>());
        final Child childd1 = new Child();
        childd1.setChildTitle("Child 0");

        final Child childd2 = new Child();
        childd2.setChildTitle("Child 1");

        p3.getChildren().add(childd1);
        p3.getChildren().add(childd2);

        //Add Child class object to parent class object

        groupNames.add(p);
        groupNames.add(p1);
        groupNames.add(p2);
        groupNames.add(p3);



       /* Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ParentItem>>() {}.getType();
        String json = gson.toJson(groupNames, type);
        System.out.println(json);*/

        return groupNames;


    }


    private void checkData(final ArrayList<ParentItem> datalist) {
        if (datalist == null)
            return;

        parentItems = datalist;

        // Check for ExpandableListAdapter object
        if (this.getExpandableListAdapter() == null) {
            //Create ExpandableListAdapter Object
            final MyExpandableListAdapter mAdapter = new MyExpandableListAdapter();

            // Set Adapter to ExpandableList Adapter
            this.setListAdapter(mAdapter);
        } else {
            // Refresh ExpandableListView data
            ((MyExpandableListAdapter) getExpandableListAdapter()).notifyDataSetChanged();
        }
    }

    /**
     * A Custom adapter create
     */
    private class MyExpandableListAdapter extends BaseExpandableListAdapter {


        private LayoutInflater inflater;

        public MyExpandableListAdapter() {
            // Create Layout Inflator
            inflater = LayoutInflater.from(MainActivity.this);
        }


        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parentView) {
            final ParentItem parent = parentItems.get(groupPosition);
            convertView = inflater.inflate(R.layout.expandable_list_parent, parentView, false);

            ((TextView) convertView.findViewById(R.id.parent_list_title)).setText(parent.getParenttitle());

            CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            checkbox.setChecked(parent.isChecked());

            checkbox.setOnCheckedChangeListener(new CheckUpdateListener(parent));

            return convertView;
        }


        // This Function used to inflate child rows view
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parentView) {
            final ParentItem parent = parentItems.get(groupPosition);
            final Child child = parent.getChildren().get(childPosition);

            convertView = inflater.inflate(R.layout.expandable_list_child, parentView, false);
            ((TextView) convertView.findViewById(R.id.child_list_item_title)).setText(child.getChildTitle());


            return convertView;
        }


        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return parentItems.get(groupPosition).getChildren().get(childPosition);
        }

        //Call when child row clicked
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            if (ChildClickStatus != childPosition) {
                ChildClickStatus = childPosition;

            }

            return childPosition;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            int size = 0;
            if (parentItems.get(groupPosition).getChildren() != null)
                size = parentItems.get(groupPosition).getChildren().size();
            return size;
        }


        @Override
        public Object getGroup(int groupPosition) {
            Log.i("GroupItem", groupPosition + "=  getGroup ");

            return parentItems.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return parentItems.size();
        }

        //Call when parent row clicked
        @Override
        public long getGroupId(int groupPosition) {
            Log.i("GroupItem", groupPosition + "=  getGroupId " + ParentClickStatus);

            ParentClickStatus = groupPosition;
            if (ParentClickStatus == 0)
                ParentClickStatus = -1;

            return groupPosition;
        }

        @Override
        public void notifyDataSetChanged() {
            // Refresh List rows
            super.notifyDataSetChanged();
        }

        @Override
        public boolean isEmpty() {
            return ((parentItems == null) || parentItems.isEmpty());
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return true;
        }


        /**
         * ----------- Checkbox Checked Change Listener -----------------
         **/

        private final class CheckUpdateListener implements OnCheckedChangeListener {
            private final ParentItem parent;

            private CheckUpdateListener(ParentItem parent) {
                this.parent = parent;
            }

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i("onCheckedChanged", "isChecked: " + isChecked);
                parent.setChecked(isChecked);

                ((MyExpandableListAdapter) getExpandableListAdapter()).notifyDataSetChanged();

                final Boolean checked = parent.isChecked();
                Toast.makeText(getApplicationContext(), "GroupItem : " + parent.getParenttitle() + " " + (checked ? STR_CHECKED : STR_UNCHECKED),
                        Toast.LENGTH_LONG).show();
            }
        }
        /***********************************************************************/


    }


}

