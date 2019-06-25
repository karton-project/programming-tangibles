package com.alpay.codenotes.adapter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.alpay.codenotes.BlocklyActivity;
import com.alpay.codenotes.CameraActivity;
import com.alpay.codenotes.R;
import com.alpay.codenotes.base.BaseActivity;
import com.alpay.codenotes.fragments.SketchFragment;
import com.alpay.codenotes.fragments.StoryboardFragment;
import com.alpay.codenotes.models.Frame;
import com.alpay.codenotes.utils.NavigationManager;
import com.alpay.codenotes.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class StoryViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private AppCompatActivity appCompatActivity;
    private Fragment fragment;
    private List<Frame> mFrameList;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int PICK_IMAGE = 1;

    private static final int REQUEST_CAMERA_PERMISSIONS = 931;

    public StoryViewAdapter(Fragment fragment, List<Frame> mFrameList) {
        this.fragment = fragment;
        this.mFrameList = mFrameList;
        appCompatActivity = (AppCompatActivity) fragment.getActivity();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.storyheader_layout, parent, false);
            return new HeaderViewHolder(layoutView);
        } else {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.storygrid_layout, parent, false);
            return new StoryViewHolder(layoutView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return mFrameList.get(position).getFrameGridType().contentEquals(Frame.HEADER_TYPE);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).mHeader.setText(mFrameList.get(position).getFrameName());
            ((HeaderViewHolder) holder).mHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HeaderViewHolder) holder).mHeader.setVisibility(View.GONE);
                    ((HeaderViewHolder) holder).mNewTitleLayout.setVisibility(View.VISIBLE);
                }
            });
            ((HeaderViewHolder) holder).mNewTitleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HeaderViewHolder) holder).mNewTitleLayout.setVisibility(View.GONE);
                    ((HeaderViewHolder) holder).mHeader.setVisibility(View.VISIBLE);
                    String text = ((HeaderViewHolder) holder).mNewTitle.getText().toString();
                    Frame.updateFrameTitle(mFrameList.get(position).getFrameID(), text);
                    ((StoryboardFragment)fragment).refreshStoryBoard(position);
                }
            });
        } else {
            ((StoryViewHolder) holder).mImage.setImageDrawable(Utils.getDrawableWithName(appCompatActivity, mFrameList.get(position).getFrameImageName()));
            ((StoryViewHolder) holder).mTitle.setText(mFrameList.get(position).getFrameName());
            ((StoryViewHolder) holder).mID.setText(String.valueOf(mFrameList.get(position).getFrameID()));
            ((StoryViewHolder) holder).mStoryText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Add text to story
                }
            });
            ((StoryViewHolder) holder).mTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((StoryViewHolder) holder).mNewTitleLayout.setVisibility(View.VISIBLE);
                    ((StoryViewHolder) holder).mTitle.setVisibility(View.GONE);
                }
            });
            ((StoryViewHolder) holder).mNewTitleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((StoryViewHolder) holder).mNewTitleLayout.setVisibility(View.GONE);
                    ((StoryViewHolder) holder).mTitle.setVisibility(View.VISIBLE);
                    String text = ((StoryViewHolder) holder).mNewTitle.getText().toString();
                    Frame.updateFrameTitle(mFrameList.get(position).getFrameID(), text);
                    ((StoryboardFragment)fragment).refreshStoryBoard(position);
                }
            });
            ((StoryViewHolder) holder).mImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((StoryViewHolder) holder).mViewSwitcher.getCurrentView().getId() == R.id.storygrid_card_thumbnail) {
                        ((StoryViewHolder) holder).mViewSwitcher.showNext();
                    }
                }
            });
            ((StoryViewHolder) holder).mActionLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((StoryViewHolder) holder).mViewSwitcher.getCurrentView().getId() == R.id.story_action_layout) {
                        ((StoryViewHolder) holder).mViewSwitcher.showPrevious();
                    }
                }
            });
            ((StoryViewHolder) holder).mAddFromCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT > 15) {
                        final String[] permissions = {
                                Manifest.permission.CAMERA,
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE};

                        final List<String> permissionsToRequest = new ArrayList<>();
                        for (String permission : permissions) {
                            if (ActivityCompat.checkSelfPermission(appCompatActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                                permissionsToRequest.add(permission);
                            }
                        }
                        if (!permissionsToRequest.isEmpty()) {
                            ActivityCompat.requestPermissions(appCompatActivity, permissionsToRequest.toArray(new String[permissionsToRequest.size()]), REQUEST_CAMERA_PERMISSIONS);
                        } else {
                            Intent intent = new Intent(appCompatActivity, CameraActivity.class);
                            intent.putExtra(NavigationManager.BUNDLE_KEY, String.valueOf(mFrameList.get(position).getFrameID()));
                            appCompatActivity.startActivity(intent);
                        }
                    } else {
                        Intent intent = new Intent(appCompatActivity, CameraActivity.class);
                        intent.putExtra(NavigationManager.BUNDLE_KEY, String.valueOf(mFrameList.get(position).getFrameID()));
                        appCompatActivity.startActivity(intent);
                    }
                    ((StoryViewHolder) holder).mImage.setVisibility(View.VISIBLE);
                    ((StoryViewHolder) holder).mActionLayout.setVisibility(View.GONE);
                }
            });
            ((StoryViewHolder) holder).mAddDrawing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(appCompatActivity, BaseActivity.class);
                    intent.putExtra(NavigationManager.BUNDLE_KEY, NavigationManager.SKETCH);
                    appCompatActivity.startActivity(intent);
                    ((StoryViewHolder) holder).mImage.setVisibility(View.VISIBLE);
                    ((StoryViewHolder) holder).mActionLayout.setVisibility(View.GONE);
                    SketchFragment.setFrameID(mFrameList.get(position).getFrameID());
                }
            });
            ((StoryViewHolder) holder).mChooseFromGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    StoryboardFragment.setCurrentFrameID(mFrameList.get(position).getFrameID());
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    fragment.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                }
            });
            ((StoryViewHolder) holder).mCodeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(appCompatActivity, BlocklyActivity.class);
                    BlocklyActivity.setFrameID(mFrameList.get(position).getFrameID());
                    appCompatActivity.startActivity(intent);
                    //NavigationManager.openFragment(appCompatActivity, NavigationManager.BLOCKLY);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mFrameList.size();
    }
}

class StoryViewHolder extends RecyclerView.ViewHolder {

    ImageView mImage;
    TextView mCodeButton;
    ImageView mNewTitleButton;
    TextView mTitle;
    TextView mID;
    TextView mStoryText;
    TextView mChooseFromGallery;
    TextView mAddFromCamera;
    TextView mAddDrawing;
    EditText mNewTitle;
    ViewSwitcher mViewSwitcher;
    CardView mCardView;
    RelativeLayout mNewTitleLayout;
    LinearLayout mActionLayout;

    StoryViewHolder(View itemView) {
        super(itemView);
        mImage = itemView.findViewById(R.id.storygrid_card_thumbnail);
        mCodeButton = itemView.findViewById(R.id.storygrid_code_button);
        mTitle = itemView.findViewById(R.id.storygrid_card_name);
        mNewTitle = itemView.findViewById(R.id.storygrid_card_title_edit);
        mStoryText = itemView.findViewById(R.id.storygrid_card_add_text);
        mID = itemView.findViewById(R.id.storygrid_card_id);
        mActionLayout = itemView.findViewById(R.id.story_action_layout);
        mViewSwitcher = itemView.findViewById(R.id.storygrid_switch);
        mNewTitleLayout = itemView.findViewById(R.id.storygrid_card_title_edit_layout);
        mNewTitleButton = itemView.findViewById(R.id.storygrid_card_title_edit_ok);
        mCardView = itemView.findViewById(R.id.storygrid_card);

        mAddFromCamera = itemView.findViewById(R.id.storygrid_add_from_camera);
        mAddDrawing = itemView.findViewById(R.id.storygrid_add_drawing);
        mChooseFromGallery = itemView.findViewById(R.id.storygrid_add_from_gallery);

    }
}

class HeaderViewHolder extends RecyclerView.ViewHolder {

    TextView mHeader;
    EditText mNewTitle;
    ImageView mNewTitleButton;
    LinearLayout mNewTitleLayout;

    HeaderViewHolder(View itemView) {
        super(itemView);
        mHeader = itemView.findViewById(R.id.storyheader_text);
        mNewTitleLayout = itemView.findViewById(R.id.storyheader_card_title_edit_layout);
        mNewTitle = itemView.findViewById(R.id.storyheader_card_title_edit);
        mNewTitleButton = itemView.findViewById(R.id.storyheader_card_title_edit_ok);
    }
}