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

public class MyFinishedTaskAdapter extends RecyclerView.Adapter<MyFinishedTaskAdapter.ViewHolder> {

    private List<MyFinishedTask> myFinishedTasks;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView price;
        TextView location;
        TextView time;

        public ViewHolder(View itemView) {

            super(itemView);
            title = (TextView)itemView.findViewById(R.id.wefinish_task_item_title);
            price= (TextView)itemView.findViewById(R.id.wefinish_task_item_price);
            location =(TextView)itemView.findViewById(R.id.wefinish_task_item_location);
            time = (TextView)itemView.findViewById(R.id.wefinish_task_item_time);
        }
    }

    public MyFinishedTaskAdapter(List<MyFinishedTask> myFinishedTasks) {
        this.myFinishedTasks = myFinishedTasks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_finished_task_item,parent,false);
        ViewHolder viewHolder= new ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyFinishedTask myFinishedTask = myFinishedTasks.get(position);
        holder.title.setText(myFinishedTask.getTitle());
        holder.location.setText(myFinishedTask.getLocation());
        holder.time.setText(myFinishedTask.getTime());
        holder.price.setText(myFinishedTask.getPrice());
    }

    @Override
    public int getItemCount() {
        return myFinishedTasks.size();
    }
}
