package in.tosc.digitaloceanapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;

import in.tosc.digitaloceanapp.R;
import in.tosc.doandroidlib.objects.Size;

public class SelectImageSizeAdapter extends BaseAdapter {

    private final ArrayList<Size> dataset;
    private final Context context;

    public SelectImageSizeAdapter(Context context, ArrayList<Size> dataset) {
        this.context = context;
        this.dataset = dataset;
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
            view = inflater.inflate(R.layout.imagesize_list_item, null);
            holder = new ViewHolder();
            holder.diskspace = (TextView) view.findViewById(R.id.tview_diskspace_imagesize_list_item);
            holder.memory = (TextView) view.findViewById(R.id.tview_ram_imagesize_list_item);
            holder.transfer = (TextView) view.findViewById(R.id.tview_transfer_imagesize_list_item);
            holder.price = (TextView) view.findViewById(R.id.tview_price_imagesize_list_item);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Size itemSize = dataset.get(i);
        if(itemSize.getDiskSize() !=0 && itemSize.getPriceHourly() != BigDecimal.ZERO) {
            holder.diskspace.setText(String.format(Locale.getDefault(), "%dGB Disk Space", itemSize.getDiskSize()));
            holder.memory.setText(String.format(Locale.getDefault(), "%dMB RAM", itemSize.getMemorySizeInMb()));
            holder.transfer.setText(String.format(Locale.getDefault(), "%dGB transfer", itemSize.getTransfer().intValue()));
            holder.price.setText(String.format(Locale.getDefault(),
                    "$%d per month", itemSize.getPriceMonthly().intValue()));
        } else {
            // unable to fetch data. show err msg
            holder.diskspace.setText(R.string.data_unavailable);
            holder.price.setVisibility(View.GONE);
            holder.memory.setVisibility(View.GONE);
            holder.transfer.setVisibility(View.GONE);
        }
        return view;
    }

    class ViewHolder {
        TextView diskspace, memory, transfer, price;
    }
}
