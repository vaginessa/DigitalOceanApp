package in.tosc.digitaloceanapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import in.tosc.digitaloceanapp.R;
import in.tosc.doandroidlib.objects.Region;

public class SelectRegionAdapter extends BaseAdapter {

    private final ArrayList<Region> dataset;
    private final Context context;

    public SelectRegionAdapter(Context context, ArrayList<Region> dataSet) {
        this.context=context;
        this.dataset=dataSet;
    }

    @Override
    public int getCount() {
        return dataset.size();
    }

    @Override
    public Object getItem(int i) {
        return dataset.get(i);
    }

    @Override
    public long getItemId(int i) {
        return dataset.get(i).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dropdown_list_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) view.findViewById(R.id.tview_dropdown_list_item);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textView.setText(dataset.get(i).getName());
        return view;
    }

    class ViewHolder {
        TextView textView;
    }
}
