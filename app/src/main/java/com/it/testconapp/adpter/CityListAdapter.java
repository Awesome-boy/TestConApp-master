package com.it.testconapp.adpter;

import android.support.v7.widget.RecyclerView;

import com.it.testconapp.javabean.City;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by zt on 2019/5/16.
 */
public abstract class CityListAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

        private ArrayList<City> items = new ArrayList<City>();

  public CityListAdapter() {
            setHasStableIds(true);
        }

        public void add(City object) {
            items.add(object);
            notifyDataSetChanged();
        }

        public void add(int index, City object) {
            items.add(index, object);
            notifyDataSetChanged();
        }

        public void addAll(Collection<? extends City> collection) {
            if (collection != null) {
                items.addAll(collection);
                notifyDataSetChanged();
            }
        }

        public void addAll(City... items) {
            addAll(Arrays.asList(items));
        }

        public void clear() {
            items.clear();
            notifyDataSetChanged();
        }

        public void remove(String object) {
            items.remove(object);
            notifyDataSetChanged();
        }

        public City getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).hashCode();
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
}
