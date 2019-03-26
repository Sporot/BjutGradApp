package p.sby.gs_qca.Main.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import p.sby.gs_qca.R;
import p.sby.gs_qca.util.Inventory;

public class T1searchAdapter extends BaseRecyclerViewAdapter<Inventory>{


    public T1searchAdapter(Context context, List<Inventory> datas) {
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
