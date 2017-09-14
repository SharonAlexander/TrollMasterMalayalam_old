package com.sharon.trollmastermalayalam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sharon.trollmastermalayalam.model.Datum;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    int pagepic;
    private AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
    private Datum datum;
    private ListAdapterListener listener;
    private List<Datum> datumList;
    private Context context;

    public ListAdapter(List<Datum> datumList, int pagepic, Context context, ListAdapterListener listener) {
        this.datumList = datumList;
        this.context = context;
        this.listener = listener;
        this.pagepic = pagepic;
        anim.setDuration(1000);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_row_new, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        datum = this.datumList.get(position);

        if (datum.getMessage() != null) {
            holder.message.setText(datum.getMessage());
        } else {
            holder.message.setVisibility(View.GONE);
        }
        holder.pagename.setText(datum.getFrom().getName() != null ? datum.getFrom().getName() : context.getString(R.string.app_name));
        holder.page_pic.setImageResource(pagepic);
        Glide
                .with(context)
                .asBitmap()
                .load(datum.getFullPicture() != null ? datum.getFullPicture() : datum.getPicture())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        holder.pic.setImageBitmap(resource);
                        holder.pic.buildDrawingCache();
                    }
                });

        if (datum.getType().equals("link") || !datum.getLink().contains("https://www.facebook.com")) {
            holder.post_url.setText(datum.getName() != null ? datum.getName() : datum.getLink());
            holder.link_layout.setVisibility(View.VISIBLE);
        }
        if (datum.getType().equals("video") && datum.getSource() != null) {
            holder.play_icon.setVisibility(View.VISIBLE);
        }
        //setFadeAnimation(holder.itemView);
        applyClickEvents(holder, position, datum);
    }

    private void applyClickEvents(final MyViewHolder holder, final int position, final Datum dat) {
        holder.link_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onLinkClicked(position);
            }
        });

        holder.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dat.getType().equals("video")) {
                    listener.onVideoPlayRequest(position);
                } else if (dat.getType().equals("photo")) {
                    listener.onPhotoFullScreenRequest(position);
                } else if (dat.getType().equals("link")) {
                    listener.onLinkClicked(position);
                }
            }
        });

        holder.play_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onVideoPlayRequest(position);
            }
        });

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMainDownloadClicked(position);
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMainShareClicked(position);
            }
        });
        holder.visitfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMainVisitFB(position);
            }
        });
    }

    private void setFadeAnimation(View view) {
        view.startAnimation(anim);
    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void addAll(List<Datum> d) {
        datumList.addAll(d);
        notifyDataSetChanged();
    }

    public interface ListAdapterListener {
        void onLinkClicked(int position);

        void onVideoPlayRequest(int position);

        void onPhotoFullScreenRequest(int position);

        void onMainDownloadClicked(int position);

        void onMainShareClicked(int position);

        void onMainVisitFB(int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView pic, page_pic;
        AppCompatImageView play_icon;
        LinearLayout link_layout;
        TextView message, pagename, post_url;
        Button share, download, visitfb;

        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "HelveticaNeueStd.otf");

        private MyViewHolder(View view) {
            super(view);
            pic = view.findViewById(R.id.thumbnail);
            page_pic = view.findViewById(R.id.pagepic);
            message = view.findViewById(R.id.caption);
            pagename = view.findViewById(R.id.source_page_name);
            post_url = view.findViewById(R.id.post_url);
            play_icon = view.findViewById(R.id.play_icon);
            link_layout = view.findViewById(R.id.link_layout);

            share = view.findViewById(R.id.share_main_button);
            download = view.findViewById(R.id.download_main_button);
            visitfb = view.findViewById(R.id.visitfb_main_button);

            message.setTypeface(custom_font);
            share.setTypeface(custom_font);
            download.setTypeface(custom_font);
            visitfb.setTypeface(custom_font);
        }
    }
}
