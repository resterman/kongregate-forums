package com.resterman.kongregateforums.ui.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.resterman.kongregateforums.R;
import com.resterman.kongregateforums.util.PicassoImageGetter;
import com.resterman.kongregateforums.model.Post;
import com.resterman.kongregateforums.util.PostViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends ArrayAdapter<Post> {

    Context context;

    public PostAdapter(Context context, int resource, List<Post> objects) {
        super(context, resource, objects);

        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PostViewHolder viewHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.item_post, parent, false);

            viewHolder = new PostViewHolder();
            viewHolder.avatar = (ImageView) view.findViewById(R.id.avatar);
            viewHolder.username = (TextView) view.findViewById(R.id.username);
            viewHolder.time = (TextView) view.findViewById(R.id.time);
            viewHolder.content = (TextView) view.findViewById(R.id.content);

            view.setTag(viewHolder);
        } else {
            viewHolder = (PostViewHolder) view.getTag();
        }

        Post post = getItem(position);

        viewHolder.username.setText(post.getUsername());
        viewHolder.time.setText(post.getTime());
        viewHolder.content.setText(
                Html.fromHtml(
                        post.getContent(),
                        new PicassoImageGetter(viewHolder.content, context),
                        null
                )
        );
        Picasso.with(context)
                .load(post.getAvatarUrl()).into(viewHolder.avatar);

        return view;
    }

}
