package hk.example.projectapplication;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SAdapter extends RecyclerView.Adapter<SAdapter.IndexViewHolder> implements Filterable {
    ArrayList<HashMap<String, String>> MapObjectList;      // actual stuff
    ArrayList<HashMap<String, String>> MapObjectListFULL;
    ArrayList<HashMap<String, String>> IndexMapObjectList; // for indexing (all objects)
    ArrayList<HashMap<String, String>> PrevMapObjectList;  // remember the previous searching results

    private OnItemClickListener mListener;
    String language;
    String by;

    private Activity activity;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class IndexViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        TextView Category;
        TextView Address;

        IndexViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            Name = itemView.findViewById(R.id.name);
            Category = itemView.findViewById(R.id.email);
            Address = itemView.findViewById(R.id.address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public SAdapter(Activity activity, ArrayList<HashMap<String, String>> ALL, String lang) {
        this.activity = activity;
        this.MapObjectList = ALL;
        this.MapObjectListFULL = new ArrayList<>(MapObjectList); // just a copy
        this.IndexMapObjectList = School.schoolList;
        this.PrevMapObjectList = ALL;
        this.language = lang;
    }

    @NonNull
    @Override
    public IndexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_layout, parent, false);
        return new IndexViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull IndexViewHolder holder, int position) {
        HashMap<String, String> currentItem = MapObjectList.get(position);
        if (this.language == "en") {
            holder.Name.setText(currentItem.get("English_name"));
            holder.Category.setText(currentItem.get("English_category"));
            holder.Address.setText(currentItem.get("English_address"));
        } else {
            holder.Name.setText(currentItem.get("中文名稱"));
            holder.Category.setText(currentItem.get("中文類別"));
            holder.Address.setText(currentItem.get("中文地址"));
        }
    }

    public void update(ArrayList<HashMap<String, String>> New) {
        MapObjectList = New;
        notifyDataSetChanged();
    }

    public void append(int index) {
        int target = index+100;
        if (target > IndexMapObjectList.size()) target = IndexMapObjectList.size() - index;
        for (int i = index; i < target; i++) {
            MapObjectList.add(IndexMapObjectList.get(i));
        }
        notifyDataSetChanged();
    }
    public void filteringBy(int type, String by, Boolean global) {
        if (!global) {
            if (type == 0) {
                // Category
                ArrayList<HashMap<String, String>> NewList = new ArrayList<>();
                for (int i = 0; i < MapObjectList.size(); i++) {
                    HashMap<String, String> Obj = MapObjectList.get(i);
                    if (Obj.get("English_category").equals(by) || Obj.get("中文類別").equals(by)) {
                        NewList.add(Obj);
                    }
                }
                MapObjectList = NewList;
            } else {
                // DISTRICT
                // Category
                ArrayList<HashMap<String, String>> NewList = new ArrayList<>();
                for (int i = 0; i < MapObjectList.size(); i++) {
                    HashMap<String, String> Obj = MapObjectList.get(i);
                    if (Obj.get("District").equals(by) || Obj.get("分區").equals(by)) {
                        NewList.add(Obj);
                    }
                }
                MapObjectList = NewList;
            }
            notifyDataSetChanged();

        } else {
            // global filtering for all objects
            if (type == 0) {
                // Category
                ArrayList<HashMap<String, String>> NewList = new ArrayList<>();
                for (int i = 0; i < IndexMapObjectList.size(); i++) {
                    HashMap<String, String> Obj = IndexMapObjectList.get(i);
                    if (Obj.get("English_category").equals(by) || Obj.get("中文類別").equals(by)) {
                        NewList.add(Obj);
                    }
                }
                MapObjectList = NewList;
            } else {
                // DISTRICT
                // Category
                ArrayList<HashMap<String, String>> NewList = new ArrayList<>();
                for (int i = 0; i < IndexMapObjectList.size(); i++) {
                    HashMap<String, String> Obj = IndexMapObjectList.get(i);
                    if (Obj.get("District").equals(by) || Obj.get("分區").equals(by)) {
                        NewList.add(Obj);
                    }
                }
                MapObjectList = NewList;
            }
            notifyDataSetChanged();
        }
    }


    public void clear() {
        // clear all data
        ArrayList<HashMap<String, String>> empty = new ArrayList<>();
        MapObjectList = empty;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return MapObjectList.size();
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<HashMap<String, String>> filteredList = new ArrayList<>(); // will only contain filtered items

            if (constraint == null || constraint == "") {
                // Show all results
                filteredList.addAll(MapObjectListFULL);
            } else {
                if (language == "en") {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (HashMap<String, String> obj : IndexMapObjectList) {
                        if (obj.get("English_name").toLowerCase().contains(filterPattern)) {
                            filteredList.add(obj);
                        }
                    }
                } else {
                    // zh
                    String filterPatten = constraint.toString();
                    for (HashMap<String, String> obj : IndexMapObjectList) {
                        if (obj.get("中文名稱").contains(filterPatten)) {
                            filteredList.add(obj);
                        }
                    }
                }
            }

            PrevMapObjectList = filteredList;

            FilterResults results = new FilterResults();

            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            MapObjectList.clear();
            MapObjectList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
