package info;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eachother.MainActivity;
import com.example.eachother.R;
import com.example.eachother.taskFragment;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 袁刚 on 2017/5/15.
 */

public class TaskArrayAdapter extends RecyclerView.Adapter<TaskArrayAdapter.ViewHolder> {
//    private int resourceId;//布局文件

//    public TaskArrayAdapter(@NonNull Context context, int textViewResourceId,@NonNull List<TaskItem> objects) {
//        super(context, textViewResourceId, objects);
//        this.resourceId = textViewResourceId;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
////        return super.getView(position, convertView, parent);
//        TaskItem taskItem = getItem(position);
//        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
//        TextView taskTitle = (TextView)view.findViewById(R.id.task_item_title);
//        TextView taskPrice = (TextView)view.findViewById(R.id.task_item_price);
//        TextView taskLocation = (TextView)view.findViewById(R.id.task_item_location);
//        TextView taskTime = (TextView)view.findViewById(R.id.task_item_time);
//        taskTitle.setText(taskItem.getTitle());
//        taskPrice.setText(taskItem.getPrice());
//        taskLocation.setText(taskItem.getPushLoation());
//        taskTime.setText(taskItem.getTime());
//        return view;
//    }


    private List<TaskItem> taskItemList= new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView taskTitle;
        TextView taskPrice;
        TextView taskLocation;
        TextView taskTime;
        View taskItemView;
        CircleImageView circleImageView;


        public ViewHolder(View itemView) {
            super(itemView);
            taskItemView = itemView;
            circleImageView= (CircleImageView)itemView.findViewById(R.id.pushUser_img);
             taskTitle = (TextView)itemView.findViewById(R.id.task_item_title);
             taskPrice = (TextView)itemView.findViewById(R.id.task_item_price);
             taskLocation = (TextView)itemView.findViewById(R.id.task_item_location);
             taskTime = (TextView)itemView.findViewById(R.id.task_item_time);
        }
    }

    public TaskArrayAdapter(List<TaskItem> itemList) {
        this.taskItemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_view,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return  viewHolder;

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TaskItem taskItem= taskItemList.get(position);
        holder.taskTitle.setText(taskItem.getTitle());
        holder.taskPrice.setText(taskItem.getPrice());
        holder.taskLocation.setText(taskItem.getPushLoation());
        holder.taskTime.setText(taskItem.getTime());
        Glide.with(taskFragment.context).load(taskItem.getCircleImageView()).into(holder.circleImageView);
    }

    @Override
    public int getItemCount() {
//        return 0;
        return taskItemList.size();
    }
}
