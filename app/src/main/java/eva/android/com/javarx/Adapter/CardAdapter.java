package eva.android.com.javarx.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import eva.android.com.javarx.Models.User;
import eva.android.com.javarx.R;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private final List<User> mItems;

    public CardAdapter(List<User> items) {
        mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.name.setText("username: " + mItems.get(i).getLogin());
        viewHolder.id.setText("id: " + mItems.get(i).getId());
        /**
         * Picasso with context, load from url, placeholder while uploading,
         * error when uploading failed, into ImageView
         */
        Picasso.with(viewHolder.itemView.getContext())
                .load(mItems.get(i).getAvatarUrl())
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_error)
                .into(viewHolder.avatar);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView name;
        private final ImageView avatar;

        ViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id);
            name = (TextView) itemView.findViewById(R.id.name);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
        }
    }
}