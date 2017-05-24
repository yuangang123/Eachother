//package info;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.eachother.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by 袁刚 on 2017/5/15.
// */
//
//public class LiuyanArrayAdapter extends RecyclerView.Adapter<LiuyanArrayAdapter.ViewHoder> {
//
//    public List<Liuyan> liuyanList = new ArrayList<>();
//
//    static class ViewHoder extends RecyclerView.ViewHolder{
//        View liuyan;
//        TextView liuyanuser;
//        TextView liuyancontent;
//
//        public ViewHoder(View itemView) {
//            super(itemView);
//            this.liuyan= itemView;
//            this.liuyanuser = (TextView) itemView.findViewById(R.id.liuyan_user_item);
//            this.liuyancontent=(TextView)itemView.findViewById(R.id.liuyan_content_item);
//        }
//    }
//
//    public LiuyanArrayAdapter(List<Liuyan> liuyanList) {
//        this.liuyanList = liuyanList;
//    }
//
//    @Override
//    public ViewHoder onCreateViewHolder(final ViewGroup parent, int viewType) {
////        return null;return
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.liuyan_item,parent,false);
//        ViewHoder viewHoder = new ViewHoder(view);
//
//        /**
//         * 为留言中添加点击功能
//         */
//
//        viewHoder.liuyan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        viewHoder.liuyanuser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(parent.getContext(),"将会跳入用户详细界面，不过暂时没有开通。",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        return viewHoder;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHoder holder, int position) {
//        Liuyan liuyan= liuyanList.get(position);
//        holder.liuyanuser.setText(liuyan.getLiuyaner());
//        holder.liuyancontent.setText(liuyan.getLiuyancontent());
//
//    }
//
//    @Override
//    public int getItemCount() {
////        return 0;
//        return liuyanList.size();
//    }
//}
