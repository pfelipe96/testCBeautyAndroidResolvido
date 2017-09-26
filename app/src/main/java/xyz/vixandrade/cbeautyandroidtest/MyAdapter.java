package xyz.vixandrade.cbeautyandroidtest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by paulofelipeoliveirasouza on 25/09/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<ConstructorRepoInfo> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tVRepo1Name, tVRepo1URL, tvRepo1Watchers, tVRepo1Issues;
        public ViewHolder(View v) {
            super(v);
            tVRepo1Name =  v.findViewById(R.id.repo1_name);
            tVRepo1URL =  v.findViewById(R.id.repo1_url);
            tvRepo1Watchers = v.findViewById(R.id.repo1_watchers);
            tVRepo1Issues = v.findViewById(R.id.repo1_issues);
        }
    }

    public MyAdapter(ArrayList<ConstructorRepoInfo> myDataset) {
        mDataset = myDataset;
    }
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tVRepo1Name.setText(mDataset.get(position).getRepoName());
        holder.tVRepo1URL.setText(mDataset.get(position).getRepoURL());
        holder.tvRepo1Watchers.setText(mDataset.get(position).getWatchers()+" Watchers");
        holder.tVRepo1Issues.setText(mDataset.get(position).getAbertoIssues()+" Issues Abertos");

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}


