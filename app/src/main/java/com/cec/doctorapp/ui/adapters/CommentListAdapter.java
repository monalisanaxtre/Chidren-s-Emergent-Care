//package com.example.doctorapp.ui.adapters;
//
//import android.net.ParseException;
//import android.text.TextUtils;
//import android.text.format.DateUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.doctorapp.R;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//public class CommentListAdapter extends BaseRVAdapter<CommentItem, CommentsListAdapter.RecyclerViewHolder> {
//
//    private List<CommentItem> commentItems;
//
//    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
//
//        @BindView(R.id.comment_time)
//        TextView commentTime;
//        @BindView(R.id.commenter)
//        TextView commenter;
//        @BindView(R.id.comment)
//        TextView comment;
//
//        public RecyclerViewHolder(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//            view.bringToFront();
//        }
//    }
//
//    public CommentListAdapter(List<CommentItem> commentItems) {
//        super(commentItems);
//        this.commentItems = commentItems;
//    }
//
//    @Override
//    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_comment, parent, false);
//
//        return new RecyclerViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
//
//        final CommentItem commentItem = commentItems.get(position);
//
//        if (commentItem.getCreatedTime() != null) {
//            String createdTime = commentItem.getCreatedTime();
//            try {
//                holder.commentTime.setText(DateUtils.getRelativeTimeFromTimestamp(createdTime));
//            } catch (ParseException e) {
//                Timber.e(e);
//            }
//        }
//
//        if (!TextUtils.isEmpty(commentItem.getMessage())) {
//            holder.comment.setText(commentItem.getMessage());
//            holder.comment.setVisibility(View.VISIBLE);
//        } else {
//            // status is empty, remove from view
//            holder.comment.setVisibility(View.GONE);
//        }
//
//        if (!TextUtils.isEmpty(commentItem.getFrom().getName())) {
//            holder.commenter.setText(commentItem.getFrom().getName());
//            holder.commenter.setVisibility(View.VISIBLE);
//        } else {
//            // status is empty, remove from view
//            holder.commenter.setVisibility(View.GONE);
//        }
//
//    }
//}
//
