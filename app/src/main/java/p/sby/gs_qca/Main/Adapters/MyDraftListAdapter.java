package p.sby.gs_qca.Main.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import p.sby.gs_qca.R;

public class MyDraftListAdapter extends RecyclerView.Adapter<MyDraftListAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<String> datas;



    public MyDraftListAdapter(Context context, ArrayList<String> datas) {
        this.context=context;
        this.datas=datas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=View.inflate(context,R.layout.item_list,null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
    //根据位置得到对应数据
        String data=datas.get(i);
        myViewHolder.list_text.setText(data); //设置数据
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView list_text;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            list_text=(TextView)itemView.findViewById(R.id.list_text);
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
