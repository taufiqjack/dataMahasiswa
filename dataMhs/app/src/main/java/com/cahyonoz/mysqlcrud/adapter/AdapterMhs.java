package com.cahyonoz.mysqlcrud.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cahyonoz.mysqlcrud.R;
import com.cahyonoz.mysqlcrud.model.ModelMhs;

import java.util.List;

public class AdapterMhs extends RecyclerView.Adapter<AdapterMhs.MhsViewHolder> {


    List<ModelMhs> mhs;
    public  AdapterMhs(List<ModelMhs> mhs){
        this.mhs = mhs;
    }


    @Override
    public MhsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_mhs,viewGroup,false);

        MhsViewHolder mhsViewHolder = new MhsViewHolder(v);
        return mhsViewHolder;
    }
    @Override
    public void onBindViewHolder(MhsViewHolder mhsViewHolder , int i){
        mhsViewHolder.mhsName.setText(mhs.get(i).getNama());
        mhsViewHolder.mhsAlamat.setText(mhs.get(i).getAlamat());
        mhsViewHolder.mhsNim.setText(mhs.get(i).getNim());
    }
    @Override
    public int getItemCount(){
        return  mhs.size();
    }
    public ModelMhs getItem(int position){
        return mhs.get(position);
    }
    @Override
    public  void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class MhsViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView mhsName;
        TextView mhsAlamat;
        TextView mhsNim;
        MhsViewHolder(View itemView){
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            mhsName = (TextView) itemView.findViewById(R.id.textViewRowNama);
            mhsAlamat = (TextView) itemView.findViewById(R.id.textViewRowAlamat);
            mhsNim = (TextView) itemView.findViewById(R.id.textViewRowNim);

        }

    }

}