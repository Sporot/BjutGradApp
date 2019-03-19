package p.sby.gs_qca.Main.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import p.sby.gs_qca.R;
import p.sby.gs_qca.table4.Adapter.t4listAdapter;

public class Searcht1Adapter extends RecyclerView.Adapter<Searcht1Adapter.Myviewholder> {

    private final Context context;
    private ArrayList<String> datas;

    public Searcht1Adapter(Context context, ArrayList<String> datas) {
        this.context=context;
        this.datas=datas;
    }



    @NonNull
    @Override
    //创建view
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //调用每一条report title的布局
        View itemView=View.inflate(context, R.layout.item_list,null);
        return new Myviewholder(itemView);
    }


    //绑定数据和view
    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        //根据位置得到对应数据
        String data=datas.get(i);
        myviewholder.list_text.setText(data);

    }


    //list总条数
    @Override
    public int getItemCount() {

        return datas.size();
    }

    class Myviewholder extends RecyclerView.ViewHolder{
        private TextView list_text;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            list_text=(TextView)itemView.findViewById(R.id.list_text);
    //       report_access=(ImageView)itemView.findViewById(R.id.report_access);
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
