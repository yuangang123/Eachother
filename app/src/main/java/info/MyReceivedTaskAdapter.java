package info;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eachother.R;

import java.util.List;

/**
 * Created by 袁刚 on 2017/5/27.
 */

public class MyReceivedTaskAdapter extends RecyclerView.Adapter<MyReceivedTaskAdapter.ViewHolder> {
    private List<MyReceivedTaskItem> myReceivedTaskItems;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView price;
        TextView location;
        TextView time;
        View view1;

        public ViewHolder(View view){
            super(view);
            view1 = view;
            title = (TextView)view.findViewById(R.id.wedo_task_item_title);
            price = (TextView)view.findViewById(R.id.wedo_task_item_price);
            location = (TextView)view.findViewById(R.id.wedo_task_item_location);
            time = (TextView)view.findViewById(R.id.wedo_task_item_time);
        }

    }

    public MyReceivedTaskAdapter(List<MyReceivedTaskItem> myReceivedTaskItems) {
        this.myReceivedTaskItems = myReceivedTaskItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wedo_task_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       MyReceivedTaskItem myReceivedTaskItem = myReceivedTaskItems.get(position);
        holder.title.setText(myReceivedTaskItem.getTitle());
        holder.location.setText(myReceivedTaskItem.getLocation());
        holder.price.setText(myReceivedTaskItem.getPrice());
        holder.time.setText(myReceivedTaskItem.getTime());

    }

    @Override
    public int getItemCount() {
        return myReceivedTaskItems.size();
    }
}
