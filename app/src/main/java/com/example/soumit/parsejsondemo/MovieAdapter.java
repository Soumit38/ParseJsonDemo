package com.example.soumit.parsejsondemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

public class MovieAdapter extends ArrayAdapter {

    private static final String TAG = "MovieAdapter";
    private Context mContext;
    private List<MovieModel> movieModelList;
    private int resource;
    private LayoutInflater inflater;

    public MovieAdapter(@NonNull Context context, int resource, @NonNull List<MovieModel> objects) {
        super(context, resource, objects);
        mContext = context;
        movieModelList = objects;
        this.resource = resource;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        if(convertView == null){
            convertView = inflater.inflate(resource, parent, false);
            holder.ivMovieIcon = convertView.findViewById(R.id.ivIcon);
            holder.tvMovie = convertView.findViewById(R.id.tvMovie);
            holder.tvTagline = convertView.findViewById(R.id.tvTagline);
            holder.tvYear = convertView.findViewById(R.id.tvYear);
            holder.tvDuration = convertView.findViewById(R.id.tvDuration);
            holder.tvDirector = convertView.findViewById(R.id.tvDirector);
            holder.rbMovieRating = convertView.findViewById(R.id.rbMovie);
            holder.tvCast = convertView.findViewById(R.id.tvCast);
            holder.tvStory = convertView.findViewById(R.id.tvStory);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ProgressBar progressBar = convertView.findViewById(R.id.progressBar);

        final ViewHolder finalHolder = holder;
        ImageLoader.getInstance().
                displayImage(movieModelList.get(position).getImage(), holder.ivMovieIcon, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        progressBar.setVisibility(View.VISIBLE);
                        finalHolder.ivMovieIcon.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        progressBar.setVisibility(View.GONE);
                        finalHolder.ivMovieIcon.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        progressBar.setVisibility(View.GONE);
                        finalHolder.ivMovieIcon.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        progressBar.setVisibility(View.GONE);
                        finalHolder.ivMovieIcon.setVisibility(View.INVISIBLE);
                    }
                });

        holder.tvMovie.setText(movieModelList.get(position).getMovie());
        holder.tvTagline.setText(movieModelList.get(position).getTagline());
        holder.tvYear.setText( "Year : " + movieModelList.get(position).getYear());
        holder.tvDuration.setText("Duration: " + movieModelList.get(position).getDuration());
        holder.tvDirector.setText("Director : " + movieModelList.get(position).getDirector());
        holder.tvStory.setText(movieModelList.get(position).getStory());

        StringBuffer stringBuffer = new StringBuffer();
        for(Cast cast : movieModelList.get(position).getCastList()){
            if(position == movieModelList.size()-1){
                stringBuffer.append(cast.getName() + ".");
            }else {
                stringBuffer.append(cast.getName() + ", ");
            }
        }

        holder.tvCast.setText("Cast : " + stringBuffer.toString());


        return convertView;
    }

    class ViewHolder{
        private ImageView ivMovieIcon;
        private TextView tvMovie;
        private TextView tvTagline;
        private TextView tvYear;
        private TextView tvDuration;
        private TextView tvDirector;
        private RatingBar rbMovieRating;
        private TextView tvCast;
        private TextView tvStory;
    }


}























