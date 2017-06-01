package info;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eachother.R;

import java.util.List;

/**
 * Created by 袁刚 on 2017/3/31.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<Msg> mMsgList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftLinearLayout;
        LinearLayout rightLinearLayout;
        TextView leftTextView;
        TextView rightTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            leftLinearLayout = (LinearLayout)itemView.findViewById(R.id.left_layout);
            rightLinearLayout=(LinearLayout)itemView.findViewById(R.id.right_layout);
            leftTextView = (TextView)itemView.findViewById(R.id.left_textview);
            rightTextView = (TextView)itemView.findViewById(R.id.right_textview);
        }
    }

    public MsgAdapter(List<Msg> mMsgList) {
        this.mMsgList = mMsgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Msg msg = mMsgList.get(position);
        if (msg.getType()==Msg.TYPE_RECEIVED){
            holder.leftLinearLayout.setVisibility(View.VISIBLE);
            holder.rightLinearLayout.setVisibility(View.GONE);
            holder.leftTextView.setText(msg.getContent());
        }
        else if(msg.getType()==Msg.TYPE_SEND) {
            holder.rightLinearLayout.setVisibility(View.VISIBLE);
            holder.leftLinearLayout.setVisibility(View.GONE);
            holder.rightTextView.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}
