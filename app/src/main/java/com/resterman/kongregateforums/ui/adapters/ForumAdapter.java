package com.resterman.kongregateforums.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.resterman.kongregateforums.R;
import com.resterman.kongregateforums.model.Forum;

import java.util.List;

public class ForumAdapter extends ArrayAdapter<Forum> {

    private Context context;

    public ForumAdapter(Context context, int resource, List<Forum> objects) {
        super(context, resource, objects);

        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ForumViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_forum, parent, false);

            viewHolder = new ForumViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.title);
            viewHolder.description = (TextView) view.findViewById(R.id.description);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ForumViewHolder) view.getTag();
        }

        Forum forum = getItem(position);
        viewHolder.title.setText(forum.getName());
        viewHolder.description.setText(forum.getDescription());

        return view;
    }

    private static class ForumViewHolder {

        public TextView title;
        public TextView description;

    }

}
