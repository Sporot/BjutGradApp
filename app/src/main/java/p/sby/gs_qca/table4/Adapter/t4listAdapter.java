package p.sby.gs_qca.table4.Adapter;

import android.content.Context;
import android.widget.TextView;

import java.util.List;


import p.sby.gs_qca.Main.Adapters.BaseRecyclerViewAdapter;
import p.sby.gs_qca.Main.Adapters.RecyclerViewHolder;
import p.sby.gs_qca.Main.Adapters.TableListAdapter;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table4.Activity.Activity_t4reportlist;
import p.sby.gs_qca.util.Inventory;

public class t4listAdapter extends BaseRecyclerViewAdapter<Inventory> {


    public t4listAdapter(Context context, List<Inventory> datas) {
        super(context, datas, R.layout.item_inventroy);
    }


    @Override
    protected void onBindData(RecyclerViewHolder holder, Inventory bean, int position) {
        ((TextView) holder.getView(R.id.t1_draftname)).setText(bean.getItemDesc());
        String quantity = bean.getDate();
        ((TextView) holder.getView(R.id.tv_quantity)).setText(quantity);
        String detail = bean.getItemCode() ;
        ((TextView) holder.getView(R.id.tv_detail)).setText(detail);
    }
}
