package info;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eachother.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 袁刚 on 2017/5/28.
 */

public class MyZanTaskAdapter extends RecyclerView.Adapter<MyZanTaskAdapter.ViewHolder> {
    public List<MyZanTaskItem> myZanTaskItems;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView price;
        TextView location;
        TextView time;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            price = (TextView)itemView.findViewById(R.id.my_zan_task_item_price);
            location = (TextView)itemView.findViewById(R.id.my_zan_task_item_location);
            time = (TextView)itemView.findViewById(R.id.my_zan_task_item_time);
            title =(TextView)itemView.findViewById(R.id.my_zan_task_item_title);
        }
    }

    public MyZanTaskAdapter(List<MyZanTaskItem> myZanTaskItems) {
        this.myZanTaskItems = myZanTaskItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return null;
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.myzantaskitem,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyZanTaskItem myZanTaskItem = myZanTaskItems.get(position);
        holder.title.setText(myZanTaskItem.getTitle());
        holder.location.setText(myZanTaskItem.getLocation());
        holder.time.setText(myZanTaskItem.getTime());
        holder.price.setText(myZanTaskItem.getPrice());
    }

    @Override
    public int getItemCount() {
        return myZanTaskItems.size();
    }
}
