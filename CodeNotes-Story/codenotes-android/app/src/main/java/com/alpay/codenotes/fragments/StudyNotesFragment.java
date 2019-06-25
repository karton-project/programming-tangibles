package com.alpay.codenotes.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alpay.codenotes.AddToDoActivity;
import com.alpay.codenotes.R;
import com.alpay.codenotes.listener.CustomRecyclerScrollViewListener;
import com.alpay.codenotes.models.StudyNoteItem;
import com.alpay.codenotes.utils.ColorGenerator;
import com.alpay.codenotes.utils.StoreRetrieveData;
import com.alpay.codenotes.view.TextDrawable;
import com.alpay.codenotes.view.utils.ItemTouchHelperClass;
import com.alpay.codenotes.view.utils.RecyclerViewEmptySupport;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static android.app.Activity.RESULT_CANCELED;

public class StudyNotesFragment extends Fragment {

    public View view;
    private RecyclerViewEmptySupport mRecyclerView;
    private FloatingActionButton mAddToDoItemFAB;
    private ArrayList<StudyNoteItem> mStudyNoteItemsArrayList;
    public static final String TODOITEM = "StudyNotesFragment";
    private BasicListAdapter adapter;
    private static final int REQUEST_ID_TODO_ITEM = 100;
    private StudyNoteItem mJustDeletedStudyNoteItem;
    private int mIndexOfDeletedToDoItem;
    public static final String FILENAME = "todoitems.json";
    private StoreRetrieveData storeRetrieveData;
    public ItemTouchHelper itemTouchHelper;
    private CustomRecyclerScrollViewListener customRecyclerScrollViewListener;
    public static final String CHANGE_OCCURED = "changeoccured";


    public static ArrayList<StudyNoteItem> getLocallyStoredData(StoreRetrieveData storeRetrieveData) {
        ArrayList<StudyNoteItem> items = null;
        try {
            items = storeRetrieveData.loadFromFile();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_todonotes, container, false);

        storeRetrieveData = new StoreRetrieveData(getActivity(), FILENAME);
        mStudyNoteItemsArrayList = getLocallyStoredData(storeRetrieveData);
        adapter = new BasicListAdapter(mStudyNoteItemsArrayList);

        mRecyclerView = (RecyclerViewEmptySupport) view.findViewById(R.id.toDoRecyclerView);
        mRecyclerView.setEmptyView(view.findViewById(R.id.toDoEmptyView));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setFABAction();

        customRecyclerScrollViewListener = new CustomRecyclerScrollViewListener() {
            @Override
            public void show() {
                mAddToDoItemFAB.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }
            @Override
            public void hide() {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mAddToDoItemFAB.getLayoutParams();
                int fabMargin = lp.bottomMargin;
                mAddToDoItemFAB.animate().translationY(mAddToDoItemFAB.getHeight() + fabMargin).setInterpolator(new AccelerateInterpolator(2.0f)).start();
            }
        };
        mRecyclerView.addOnScrollListener(customRecyclerScrollViewListener);
        ItemTouchHelper.Callback callback = new ItemTouchHelperClass(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        return view;
    }

    private void setFABAction(){
        mAddToDoItemFAB = (FloatingActionButton) view.findViewById(R.id.button_fab);
        mAddToDoItemFAB.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Intent newTodo = new Intent(getActivity(), AddToDoActivity.class);
                StudyNoteItem item = new StudyNoteItem("", false, null);
                int color = ColorGenerator.MATERIAL.getRandomColor();
                item.setTodoColor(color);
                newTodo.putExtra(TODOITEM, item);
                startActivityForResult(newTodo, REQUEST_ID_TODO_ITEM);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED && requestCode == REQUEST_ID_TODO_ITEM) {
            StudyNoteItem item = (StudyNoteItem) data.getSerializableExtra(TODOITEM);
            if (item.getToDoText().length() <= 0) {
                return;
            }
            boolean existed = false;

            for (int i = 0; i < mStudyNoteItemsArrayList.size(); i++) {
                if (item.getIdentifier().equals(mStudyNoteItemsArrayList.get(i).getIdentifier())) {
                    mStudyNoteItemsArrayList.set(i, item);
                    existed = true;
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
            if (!existed) {
                addToDataStore(item);
            }


        }
    }

    private void addToDataStore(StudyNoteItem item) {
        mStudyNoteItemsArrayList.add(item);
        adapter.notifyItemInserted(mStudyNoteItemsArrayList.size() - 1);

    }

    public class BasicListAdapter extends RecyclerView.Adapter<BasicListAdapter.ViewHolder> implements ItemTouchHelperClass.ItemTouchHelperAdapter {
        private ArrayList<StudyNoteItem> items;

        @Override
        public void onItemMoved(int fromPosition, int toPosition) {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(items, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(items, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onItemRemoved(final int position) {
            mJustDeletedStudyNoteItem = items.remove(position);
            mIndexOfDeletedToDoItem = position;
        }

        @Override
        public BasicListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_circle_try, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final BasicListAdapter.ViewHolder holder, final int position) {
            StudyNoteItem item = items.get(position);
            int todoTextColor = getResources().getColor(R.color.textColorDark);
            holder.mToDoTextview.setText(item.getToDoText());
            holder.mToDoTextview.setTextColor(todoTextColor);

            TextDrawable myDrawable = TextDrawable.builder().beginConfig()
                    .textColor(getResources().getColor(R.color.textColorLight))
                    .useFont(Typeface.DEFAULT)
                    .toUpperCase()
                    .endConfig()
                    .buildRound(item.getToDoText().substring(0, 1), item.getTodoColor());

            holder.mColorImageView.setImageDrawable(myDrawable);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
        BasicListAdapter(ArrayList<StudyNoteItem> items) {
            this.items = items;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            View mView;
            LinearLayout linearLayout;
            TextView mToDoTextview;
            ImageView mColorImageView;
            TextView mTimeTextView;

            public ViewHolder(View v) {
                super(v);
                mView = v;
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StudyNoteItem item = items.get(ViewHolder.this.getAdapterPosition());
                        Intent i = new Intent(getActivity(), AddToDoActivity.class);
                        i.putExtra(TODOITEM, item);
                        startActivityForResult(i, REQUEST_ID_TODO_ITEM);
                    }
                });
                mToDoTextview = (TextView) v.findViewById(R.id.toDoListItemTextview);
                mTimeTextView = (TextView) v.findViewById(R.id.todoListItemTimeTextView);
                mColorImageView = (ImageView) v.findViewById(R.id.toDoListItemColorImageView);
                linearLayout = (LinearLayout) v.findViewById(R.id.listItemLinearLayout);
            }

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            storeRetrieveData.saveToFile(mStudyNoteItemsArrayList);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mRecyclerView.removeOnScrollListener(customRecyclerScrollViewListener);
    }

}


