package info;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eachother.R;
import java.util.List;

/**
 * Created by 袁刚 on 2017/5/26.
 */

public class MypushedTaskAdpter extends RecyclerView.Adapter<MypushedTaskAdpter.ViewHolder> {

    private List<MyPushedTaskItem> myPushedTaskItems;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView location;
        TextView price;
        TextView time;
        View view1;

        public ViewHolder(View view){
            super(view);
            view1=view;
            title = (TextView)view.findViewById(R.id.my_task_item_title);
            location = (TextView)view.findViewById(R.id.my_task_item_location);
            price= (TextView)view.findViewById(R.id.my_task_item_price);
            time = (TextView)view.findViewById(R.id.my_task_item_time);
        }

    }

    public MypushedTaskAdpter(List<MyPushedTaskItem> myPushedTaskItems) {
        this.myPushedTaskItems = myPushedTaskItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pushed_task_my,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyPushedTaskItem myPushedTaskItem =myPushedTaskItems.get(position);
        holder.title.setText(myPushedTaskItem.getTitle());
        holder.price.setText(myPushedTaskItem.getPrice());
        holder.location.setText(myPushedTaskItem.getLocation());
        holder.time.setText(myPushedTaskItem.getTime());
    }

    @Override
    public int getItemCount() {
        return myPushedTaskItems.size();
    }
}
