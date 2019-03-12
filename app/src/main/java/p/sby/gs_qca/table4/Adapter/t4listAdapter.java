package p.sby.gs_qca.table4.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import p.sby.gs_qca.Main.Adapters.TableListAdapter;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table4.Activity.Activity_t4reportlist;

public class t4listAdapter extends RecyclerView.Adapter<t4listAdapter.Myviewholder> {

    private final Context context;
    private  ArrayList<String> datas;

    public t4listAdapter(Context context, ArrayList<String> datas) {
        this.context=context;
        this.datas=datas;
    }



    @NonNull
    @Override
    //创建view
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //调用每一条report title的布局
        View itemView=View.inflate(context, R.layout.item_report,null);
        return new Myviewholder(itemView);
    }


    //绑定数据和view
    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        //根据位置得到对应数据
        String data=datas.get(i);
        myviewholder.report_list.setText(data);

    }


    //list总条数
    @Override
    public int getItemCount() {

        return datas.size();
    }

    class Myviewholder extends RecyclerView.ViewHolder{
        private TextView report_list;
        private ImageView report_access;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
           report_list=(TextView)itemView.findViewById(R.id.report_list);
           report_access=(ImageView)itemView.findViewById(R.id.report_access);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null){
                        onItemClickListener.onItemClick(v,datas.get(getLayoutPosition()));
                    }
                }
            });
        }
    }

    //设置点击监听接口
    public interface OnItemClickListener{

        public  void onItemClick(View view, String content);
    }
    private TableListAdapter.OnItemClickListener onItemClickListener;

    /** * 设置某条的监听 * @param onItemClickListener */
    public void setOnItemClickListener(TableListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
