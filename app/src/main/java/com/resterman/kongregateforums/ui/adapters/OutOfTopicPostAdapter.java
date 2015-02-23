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
import com.resterman.kongregateforums.model.OutOfTopicPost;
import com.resterman.kongregateforums.util.PicassoImageGetter;
import com.resterman.kongregateforums.util.PostViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OutOfTopicPostAdapter extends ArrayAdapter<OutOfTopicPost> {

    private static String FORUM_WITH_TOPIC = "%s - %s";

    private Context context;


    public OutOfTopicPostAdapter(Context context, int resource, List<OutOfTopicPost> objects) {
        super(context, resource, objects);

        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PostViewHolder viewHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.item_out_of_topic_post, parent, false);

            viewHolder = new PostViewHolder();
            viewHolder.avatar = (ImageView) view.findViewById(R.id.avatar);
            viewHolder.username = (TextView) view.findViewById(R.id.username);
            viewHolder.time = (TextView) view.findViewById(R.id.time);
            viewHolder.forumTopic = (TextView) view.findViewById(R.id.forum_topic);
            viewHolder.content = (TextView) view.findViewById(R.id.content);

            view.setTag(viewHolder);
        } else {
            viewHolder = (PostViewHolder) view.getTag();
        }

        OutOfTopicPost post = getItem(position);

        viewHolder.username.setText(post.getUsername());
        viewHolder.time.setText(post.getTime());
        viewHolder.forumTopic.setText(
                String.format(
                        FORUM_WITH_TOPIC,
                        post.getForum(),
                        post.getTopic()
                )
        );
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
