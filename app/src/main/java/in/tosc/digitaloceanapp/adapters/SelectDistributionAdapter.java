package in.tosc.digitaloceanapp.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import in.tosc.digitaloceanapp.R;
import in.tosc.doandroidlib.objects.Image;

public class SelectDistributionAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Image> dataset = null;

    public SelectDistributionAdapter() {
    }

    public SelectDistributionAdapter(Context context, ArrayList<Image> dataSet) {
        this.context = context;
        this.dataset = dataSet;
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
        String text = String.format("%s %s", dataset.get(i).getDistribution(), dataset.get(i).getName());
        holder.textView.setText(text);
        return view;
    }

    class ViewHolder {
        TextView textView;
    }
}
