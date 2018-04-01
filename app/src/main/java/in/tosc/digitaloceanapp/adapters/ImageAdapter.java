package in.tosc.digitaloceanapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import in.tosc.digitaloceanapp.R;
import in.tosc.doandroidlib.objects.Image;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    public static final String TAG = "ImageAdapter";
    /*private static int selectedPosition = -1;
    private final List<Image> imageList;
    private ViewHolder prevholder = null;
    private int position;*/
    private Context context;

    public ImageAdapter(List<Image> items, Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_image_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        /*holder.imageName.setText(imageList.get(position).getName());
        this.position = holder.getAdapterPosition();
        holder.imageDistribution.setText(imageList.get(position).getDistribution());*/

        /*Image dropletImage = DropletCreateActivity.getDroplet().getImage();
        if (dropletImage == null) {
            deselectImage(position, holder);
        } else if (dropletImage.getDistribution().equals(imageList.get(position).getDistribution()) &&
                dropletImage.getName().equals(imageList.get(position).getName())) {
            selectImage(position, holder);
            prevholder = holder;
            selectedPosition = position;
        } else {
            deselectImage(position, holder);
        }

        holder.imageCard.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if ((Boolean) holder.imageImage.getTag()) {
                    deselectImage(position, holder);
                    prevholder = null;
                    selectedPosition = -1;
                    DropletCreateActivity.getDroplet().setImage(null);
                } else {
                    if (prevholder != null) {
                        deselectImage(selectedPosition, prevholder);
                    }
                    DropletCreateActivity.getDroplet().setImage(imageList.get(position));
                    selectImage(position, holder);
                    prevholder = holder;
                    selectedPosition = position;
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /*
        private void selectImage(int position, ViewHolder holder) {
            int selectorImage = 0;
            switch (imageList.get(position).getDistribution()) {
                case "CoreOS":
                    selectorImage = R.drawable.coreos_selected;
                    break;
                case "FreeBSD":
                    selectorImage = R.drawable.freebsd_selected;
                    break;
                case "Fedora":
                case "Fedora Atomic":
                    selectorImage = R.drawable.fedora_selected;
                    break;
                case "Debian":
                    selectorImage = R.drawable.debian_selected;
                    break;
                case "CentOS":
                    selectorImage = R.drawable.centos_selected;
                    break;
                case "Ubuntu":
                    selectorImage = R.drawable.ubuntu_selected;
                    break;
                case "RancherOS":
                    selectorImage = R.drawable.ic_droplet;
                    break;

            }
            Picasso.with(context).load(selectorImage).into(holder.imageImage);//.setBackground(ContextCompat.getDrawable(context, selectorImage));
            Log.i("OnClick", imageList.get(position).getDistribution());
            holder.imageImage.setTag(true);
        }

        private void deselectImage(int position, ViewHolder holder) {
            switch (imageList.get(position).getDistribution()) {
                case "CoreOS":
                    Picasso.with(context).load(R.drawable.coreos).into(holder.imageImage);
                    //holder.imageImage.setBackground(ContextCompat.getDrawable(context, R.drawable.coreos));
                    break;
                case "FreeBSD":
                    Picasso.with(context).load(R.drawable.freebsd).into(holder.imageImage);
                    //holder.imageImage.setBackground(ContextCompat.getDrawable(context, R.drawable.freebsd));
                    break;
                case "Fedora":
                case "Fedora Atomic":
                    Picasso.with(context).load(R.drawable.fedora).into(holder.imageImage);
                    //holder.imageImage.setBackground(ContextCompat.getDrawable(context, R.drawable.fedora));
                    break;
                case "Debian":
                    Picasso.with(context).load(R.drawable.debian).into(holder.imageImage);
                    //holder.imageImage.setBackground(ContextCompat.getDrawable(context, R.drawable.debian));
                    break;
                case "CentOS":
                    Picasso.with(context).load(R.drawable.centos).into(holder.imageImage);
                    //holder.imageImage.setBackground(ContextCompat.getDrawable(context, R.drawable.centos));
                    break;
                case "Ubuntu":
                    Picasso.with(context).load(R.drawable.ubuntu).into(holder.imageImage);
                    //holder.imageImage.setBackground(ContextCompat.getDrawable(context, R.drawable.ubuntu));
                    break;
                case "RancherOS":
                    Picasso.with(context).load(R.drawable.ic_droplet).into(holder.imageImage);
                    //holder.imageImage.setBackground(ContextCompat.getDrawable(context, R.drawable.ubuntu));
                    break;
            }
            holder.imageImage.setTag(false);
        }
    */
    class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }
    /*    ImageView imageImage;
        TextView imageName;
        TextView imageDistribution;
        CardView imageCard;

        ViewHolder(View view) {
            super(view);
            imageImage = (ImageView) view.findViewById(R.id.imageImage);
            imageName = (TextView) view.findViewById(R.id.imageName);
            imageDistribution = (TextView) view.findViewById(R.id.imageDistribution);
            imageCard = (CardView) view.findViewById(R.id.imagecard);
        }
    }*/
    }
}
