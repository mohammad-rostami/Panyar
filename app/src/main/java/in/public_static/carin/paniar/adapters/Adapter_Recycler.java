package in.public_static.carin.paniar.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.public_static.carin.paniar.OnItemListener;
import in.public_static.carin.paniar.R;
import in.public_static.carin.paniar.Struct;
import in.public_static.carin.paniar.activity.ActivityMain;


//*************************************************************** THIS CLASS IS THE ADAPTER OF RECYCLERVIEWS
public class Adapter_Recycler extends RecyclerView.Adapter<Adapter_Recycler.ViewHolder> {
    public static boolean checked;
    private OnItemListener onItemListener;
    private Context context;
    private ArrayList<Struct> structs;
    private boolean isGrid;
    private int Tab;
    private Struct selectedGroupPosition;
    private Boolean x;
    private Boolean m;

    public Adapter_Recycler(Context context, ArrayList<Struct> structs, OnItemListener onItemListener, int Tab, boolean isGrid) {
        this.onItemListener = onItemListener;
        this.context = context;
        this.structs = structs;
        this.isGrid = isGrid;
        this.Tab = Tab;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //CHOOSE WITCH XML (CARD LAYOUT) TO SHOW (INFLATE) FOR EATCH RECYCLERVIEW
        View view = inflater.inflate(R.layout.item_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //DEFINING VIEWS FOR EATCH RECYCLER VIEW
        Typeface typeFace_Regular = Typeface.createFromAsset(ActivityMain.context.getAssets(), "vazir.ttf");


        holder.llBacLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemListener.onItemClick(position);
            }
        });
        holder.txtPrice_header.setText(structs.get(position).strName);
        holder.txtPrice_header.setTypeface(typeFace_Regular);
        holder.txt1.setTypeface(typeFace_Regular);
        holder.txt2.setTypeface(typeFace_Regular);
        holder.txt3.setTypeface(typeFace_Regular);
        holder.txt4.setTypeface(typeFace_Regular);
    }


    @Override
    public int getItemCount() {
        return structs.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtPrice_header;
        TextView txt1;
        TextView txt2;
        TextView txt3;
        TextView txt4;
        LinearLayout llBacLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            txtPrice_header = (TextView) itemView.findViewById(R.id.txtPrice_header);
            txt1 = (TextView) itemView.findViewById(R.id.txt1);
            txt2 = (TextView) itemView.findViewById(R.id.txt2);
            txt3 = (TextView) itemView.findViewById(R.id.txt3);
            txt4 = (TextView) itemView.findViewById(R.id.txt4);
            llBacLayout = (LinearLayout) itemView.findViewById(R.id.llBacLayout);

        }
    }

}
