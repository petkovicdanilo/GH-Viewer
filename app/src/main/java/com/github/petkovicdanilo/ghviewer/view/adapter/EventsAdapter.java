package com.github.petkovicdanilo.ghviewer.view.adapter;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.petkovicdanilo.ghviewer.R;
import com.github.petkovicdanilo.ghviewer.api.dto.EventDto;
import com.github.petkovicdanilo.ghviewer.api.dto.RepositoryDto;
import com.github.petkovicdanilo.ghviewer.api.dto.UserDto;

import java.util.List;

import lombok.Getter;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    @Getter
    private List<EventDto> events;

    private Fragment fragment;

    private OnEventListener onEventListener;

    public EventsAdapter(List<EventDto> events, Fragment fragment,
                         OnEventListener onEventListener) {
        this.events = events;
        this.fragment = fragment;
        this.onEventListener = onEventListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);

        return new ViewHolder(view, onEventListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventDto event = events.get(position);

        String text = eventToString(event, holder);
        Spanned styledText = Html.fromHtml(text);
        holder.getTextView().setText(styledText);

        ImageView profileImage = holder.getProfileImage();
        Glide.with(fragment)
                .load(event.getActor().getAvatarUrl())
                .circleCrop()
                .into(profileImage);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Getter
        private final TextView textView;

        @Getter
        private final ImageView profileImage;

        private OnEventListener listener;

        public ViewHolder(View view, OnEventListener listener) {
            super(view);

            textView = view.findViewById(R.id.event_row_item);
            profileImage = view.findViewById(R.id.event_profile_image);

            this.listener = listener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onEventClick(getAdapterPosition());
        }
    }

    public interface OnEventListener {
        void onEventClick(int position);
    }

    private String eventToString(EventDto event, ViewHolder viewHolder) {
        Context context = viewHolder.itemView.getContext();
        String repositoryName = "<b>" + event.getRepo().getName() + "</b>";
        String actorLogin = "<b>" + event.getActor().getLogin() + "</b>";

        switch (event.getType()) {
            case COMMIT_COMMENT_EVENT:
                return context.getString(R.string.commit_comment_event, actorLogin, repositoryName);
            case CREATE_EVENT:
            case DELETE_EVENT:
                String ref = (String) event.getPayload().get("ref_type");
                return context.getString(R.string.create_event, actorLogin, ref,
                        repositoryName);
            case FORK_EVENT:
                return context.getString(R.string.fork_event, actorLogin, repositoryName);
            case GOLLUM_EVENT:
                return context.getString(R.string.gollum_event, actorLogin, repositoryName);
            case ISSUE_COMMENT_EVENT:
                String issueCommentAction = (String) event.getPayload().get("action");
                return context.getString(R.string.issue_comment_event, actorLogin,
                        issueCommentAction, repositoryName);
            case ISSUES_EVENT:
                String issueAction = (String) event.getPayload().get("action");
                return context.getString(R.string.issues_event, actorLogin, issueAction,
                        repositoryName);
            case MEMBER_EVENT:
                String memberAction = (String) event.getPayload().get("action");
                return context.getString(R.string.member_event, actorLogin, memberAction,
                        repositoryName);
            case PUBLIC_EVENT:
                return context.getString(R.string.public_event, actorLogin, repositoryName);
            case PULL_REQUEST_EVENT:
                String pullRequestAction = (String) event.getPayload().get("action");
                return context.getString(R.string.pull_request_event, actorLogin,
                        pullRequestAction, repositoryName);
            case PULL_REQUEST_REVIEW_EVENT:
                String pullRequestReviewAction = (String) event.getPayload().get("action");
                return context.getString(R.string.pull_request_review_event, actorLogin,
                        pullRequestReviewAction, repositoryName);
            case PULL_REQUEST_REVIEW_COMMENT_EVENT:
                String pullRequestReviewCommentAction = (String) event.getPayload().get("action");
                return context.getString(R.string.pull_request_review_comment_event, actorLogin,
                        pullRequestReviewCommentAction, repositoryName);
            case PUSH_EVENT:
                return context.getString(R.string.push_event, actorLogin, repositoryName);
            case RELEASE_EVENT:
                String releaseAction = (String) event.getPayload().get("action");
                return context.getString(R.string.release_event, actorLogin, releaseAction,
                        repositoryName);
            case SPONSORSHIP_EVENT:
                String sponsorshipAction = (String) event.getPayload().get("action");
                return context.getString(R.string.sponsorship_event, actorLogin,
                        sponsorshipAction, repositoryName);
            case WATCH_EVENT:
                return context.getString(R.string.watch_event, actorLogin, repositoryName);
            default:
                return "";
        }
    }
}
