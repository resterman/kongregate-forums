package com.resterman.kongregateforums.ui.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.resterman.kongregateforums.R;
import com.resterman.kongregateforums.model.Topic;

import java.util.List;

public class TopicAdapter extends ArrayAdapter<Topic> {

    private Context context;

    public TopicAdapter(Context context, int resource, List<Topic> objects) {
        super(context, resource, objects);

        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        TopicViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_topic, parent, false);

            viewHolder = new TopicViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.title);
            viewHolder.lastPost = (TextView) view.findViewById(R.id.last_post);

            view.setTag(viewHolder);
        } else {
            viewHolder = (TopicViewHolder) view.getTag();
        }

        Topic topic = getItem(position);
        viewHolder.title.setText(topic.getTitle());
        viewHolder.title.setTypeface(null, topic.isSticky()
                ? Typeface.BOLD : Typeface.NORMAL);

        viewHolder.lastPost.setText(
                String.format(
                        context.getString(R.string.last_post),
                        topic.getLastPostAuthor(),
                        topic.getLastPostTime()
                )
        );

        return view;
    }

    private class TopicViewHolder {

        public TextView title;
        public TextView lastPost;

    }
}
