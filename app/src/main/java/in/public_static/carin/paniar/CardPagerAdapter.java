package in.public_static.carin.paniar;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.public_static.carin.paniar.activity.ActivityOrder;
import in.public_static.carin.paniar.activity.ActivityPopUpMain;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;

    public CardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(CardItem item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(CardItem item, View view) {
        CardView CardDsc = (CardView) view.findViewById(R.id.cardView);
        TextView imgCardDsc = (TextView) view.findViewById(R.id.imgCardDsc);
        ImageView imgMainCard = (ImageView) view.findViewById(R.id.imgMainCard);
        Button btnBimeName = (Button) view.findViewById(R.id.btnBimeName);
        TextView txtBimeContent = (TextView) view.findViewById(R.id.txtBimeContent);

        switch (item.getType()) {
            case 1:
                imgMainCard.setImageResource(R.drawable.card_haiq_image);
                txtBimeContent.setText(R.string.hariq_pooshesh_for_card);
                btnBimeName.setText(R.string.bime_manzel_maskooni);
                CardDsc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(App.context, ActivityOrder.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        App.context.startActivity(intent);
                    }
                });
                imgCardDsc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(App.context, ActivityPopUpMain.class);
                        intent.putExtra("type", "hariq_maskan");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        App.context.startActivity(intent);
                    }
                });
                break;
            case 2:
                imgMainCard.setImageResource(R.drawable.card_havades_image);
                txtBimeContent.setText(R.string.havades_pooshesh_for_card);
                btnBimeName.setText(R.string.bime_havades_enferadi);
                CardDsc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(App.context, R.string.will_add_in_nextr_version, Toast.LENGTH_SHORT).show();
                    }
                });
                imgCardDsc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
                break;
            case 3:
                imgMainCard.setImageResource(R.drawable.card_asnaf_image);
                txtBimeContent.setText(R.string.asnaf_pooshesh_for_card);
                btnBimeName.setText(R.string.bime_jame_asnaf);
                CardDsc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(App.context, R.string.will_add_in_nextr_version, Toast.LENGTH_SHORT).show();

                    }
                });
                imgCardDsc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
                break;
        }

//        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
//        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
//        titleTextView.setText(item.getTitle());
//        contentTextView.setText(item.getText());
    }

}
