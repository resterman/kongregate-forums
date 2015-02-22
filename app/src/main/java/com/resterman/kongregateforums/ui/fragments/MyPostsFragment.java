package com.resterman.kongregateforums.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.resterman.kongregateforums.R;
import com.resterman.kongregateforums.model.OutOfTopicPost;
import com.resterman.kongregateforums.ui.activities.HomeActivity;
import com.resterman.kongregateforums.ui.adapters.OutOfTopicPostAdapter;
import com.resterman.kongregateforums.util.OutOfTopicPostsLoader;

import org.jsoup.nodes.Document;

import java.util.ArrayList;

public class MyPostsFragment extends Fragment {

    OutOfTopicPostAdapter postAdapter;

    View progressBar;
    ListView postList;

    public static MyPostsFragment newInstance() {
        return new MyPostsFragment();
    }

    public MyPostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postAdapter = new OutOfTopicPostAdapter(
                getActivity(),
                R.layout.item_out_of_topic_post,
                new ArrayList<OutOfTopicPost>()
        );

        new PostsLoader().execute("http://www.kongregate.com/users/7868884/posts");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts, container, false);

        progressBar = view.findViewById(R.id.progress_bar);

        postList = (ListView) view.findViewById(R.id.posts);
        postList.setAdapter(postAdapter);
        postList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        postList.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            int selectedCount = 0;

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if (checked) {
                    selectedCount++;
                } else {
                    selectedCount--;
                }

                mode.setTitle(selectedCount + " selected");
                mode.invalidate();
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = mode.getMenuInflater();
                menuInflater.inflate(R.menu.menu_post_selected, menu);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                MenuItem item = menu.findItem(R.id.action_edit);
                item.setVisible(selectedCount == 1);

                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                selectedCount = 0;
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((HomeActivity) activity)
                .onSectionAttached(R.id.user_posts);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

   private class PostsLoader extends OutOfTopicPostsLoader {

        public PostsLoader() {
            super(postAdapter);
        }

        protected void onPostExecute(Document document) {
            super.onPostExecute(document);

            progressBar.setVisibility(View.GONE);
            postList.setVisibility(View.VISIBLE);
        }

    }

}
