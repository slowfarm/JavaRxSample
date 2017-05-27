package eva.android.com.javarx.Adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import eva.android.com.javarx.Models.User;
import eva.android.com.javarx.R;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private List<User> mItems = new ArrayList<>();

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
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        User item = mItems.get(position);
        viewHolder.login.setText(item.getLogin());
        viewHolder.id.setText("id: " + item.getId());
        viewHolder.name.setText("name: " + (item.getName()!=null ? item.getName() : "-"));
        viewHolder.company.setText("company: " + (item.getCompany()!=null ? item.getCompany() : "-"));
        viewHolder.blog.setText("blog: " + (item.getBlog()!=null ? item.getBlog() : "-"));
        viewHolder.location.setText("location: " + (item.getLocation()!=null ? item.getLocation() : "-"));
        Picasso.with(viewHolder.itemView.getContext())  // Context
                .load(item.getAvatarUrl())              // url
                .placeholder(R.drawable.ic_image)       // placeholder image
                .error(R.drawable.ic_error)             // error image
                .into(viewHolder.avatar);               // image container
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView login;
        private final ImageView avatar;
        private final TextView name;
        private final TextView company;
        private final TextView blog;
        private final TextView location;

        ViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id);
            login = (TextView) itemView.findViewById(R.id.login);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            name = (TextView) itemView.findViewById(R.id.name);
            company = (TextView) itemView.findViewById(R.id.company);
            blog = (TextView) itemView.findViewById(R.id.blog);
            location = (TextView) itemView.findViewById(R.id.location);

            itemView.setOnClickListener(v->
                v.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/"
                                +mItems.get(getAdapterPosition()).getLogin()))));
        }
    }
}